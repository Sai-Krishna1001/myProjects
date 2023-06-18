package com.virtusa.ars.controller;
import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtusa.ars.dto.BankAccountDTO;
import com.virtusa.ars.dto.BookingDTO;
import com.virtusa.ars.dto.FlightDTO;
import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.service.BankAccountService;
import com.virtusa.ars.service.BookingService;
import com.virtusa.ars.service.FlightService;
import com.virtusa.ars.util.IdGenerator;
import com.virtusa.ars.util.SendMail;
import com.virtusa.ars.util.ServiceFactory;

public class BookingController {
    private static final Logger LOGGER = LogManager.getLogger(BookingController.class);
    private final FlightService flightService = ServiceFactory.getInstance().getFlightService();
    private final BookingService bookingService = ServiceFactory.getInstance().getBookingService();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final BankAccountService bankAccountService = ServiceFactory.getInstance().getBankAccountService();
    private static final String FLIGHT_TABLE_FORMAT = "%-12s %-20s %-20s %-20s %-10s %10s";
    private static final String BOOKING_TABLE_FORMAT = "%-15s %-20s %-15s %-15s %-15s %-15s %-20s";

    private static final String FLIGHT_ID = "Flight ID";
    private static final String AIRLINE_NAME = "Airline Name";
    private static final String DEPARTURE = "Departure";
    private static final String ARRIVAL = "Arrival";
    private static final String DURATION = "Duration";
    private static final String PRICE = "Price";
    
    public void addBooking(UserDTO customer) throws BookingException, IOException {
        try {
            // Get list of all available flights
            List<FlightDTO> flights = flightService.getAll();
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(FLIGHT_TABLE_FORMAT, FLIGHT_ID, AIRLINE_NAME, DEPARTURE, ARRIVAL, DURATION, PRICE))
                  .append(System.lineSeparator());
           
            LOGGER.info(sb);
            flights.forEach(flight -> LOGGER.info(String.format(FLIGHT_TABLE_FORMAT, flight.getFlightId(), flight.getAirlineName(), 
                    flight.getDeparture(), flight.getArrival(), flight.getDuration(), flight.getPrice())));

            
            // Filter flights by source and destination
            LOGGER.info("Enter source airport:");
            String source = reader.readLine().trim();
            LOGGER.info("Enter destination airport:");
            String destination = reader.readLine().trim();
            
            
            List<FlightDTO> filteredFlights = flights.stream()
                    .filter(f -> f.getDeparture().equalsIgnoreCase(source) && f.getArrival().equalsIgnoreCase(destination))
                    .collect(Collectors.toList());

            // Print available flights
            String availableFlights = "Available flights From " + source + " to " + destination + " : ";
            LOGGER.info(availableFlights);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.format(FLIGHT_TABLE_FORMAT, FLIGHT_ID, AIRLINE_NAME, DEPARTURE, ARRIVAL, DURATION, PRICE))
                  .append(System.lineSeparator());
           
            LOGGER.info(sb2);
            filteredFlights.forEach(flight -> LOGGER.info(String.format(FLIGHT_TABLE_FORMAT, flight.getFlightId(), flight.getAirlineName(), 
                    flight.getDeparture(), flight.getArrival(), flight.getDuration(), flight.getPrice())));

            // Ask user to select a flight and enter passenger details
            LOGGER.info("Enter flight ID to book ticket:");
            String flightId = reader.readLine().trim();
            FlightDTO selectedFlight = flightService.getById(flightId);
            if (selectedFlight == null) {
                throw new BookingException("Invalid flight ID entered.");
            }

            // Check if the customer has enough balance to book the flight
            BigDecimal flightPrice = selectedFlight.getPrice();
            BankAccountDTO bankAccount = bankAccountService.getById(customer.getUserId());
            if (bankAccount == null) {
                throw new BookingException("No bank account found for the customer.");
            }
            BigDecimal accountBalance = bankAccount.getBalance();
            if (accountBalance.compareTo(flightPrice) < 0) {
                throw new BookingException("Insufficient balance in the customer's bank account.");
            }

            // Deduct flight price from customer's bank account
            BigDecimal newBalance = accountBalance.subtract(flightPrice);
            bankAccount.setBalance(newBalance);
            bankAccountService.update(bankAccount);

            // Create booking object and save to database
            BookingDTO booking = new BookingDTO(
                    IdGenerator.generateBookingId(),
                    customer.getUserId(),
                    flightId,
                    selectedFlight.getAirlineName(),
                    customer.getUserName(),
                    selectedFlight.getPrice(),
                    LocalDateTime.now());
            bookingService.add(booking);

            LOGGER.info("Ticket booked successfully!");
            String to = customer.getEmail();
            String subject = "Flight Ticket Status";
            
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Booking ID: ").append(booking.getBookingId()).append("\n");
            sb3.append("User ID: ").append(booking.getUserId()).append("\n");
            sb3.append("Flight ID: ").append(booking.getFlightId()).append("\n");
            sb3.append("Airline Name: ").append(booking.getAirlineName()).append("\n");
            sb3.append("Passenger Name: ").append(booking.getPassengerName()).append("\n");
            sb3.append("Price: ").append(booking.getPrice()).append("\n");
            sb3.append("Created At: ").append(booking.getCreatedAt()).append("\n");
            
            String text = "Dear " + customer.getUserName() + ",\n\n"  
            		+ "Your Flight Ticket is Booked Successfully from " + selectedFlight.getDeparture()
            		+ " to " + selectedFlight.getArrival() + "\n" 
            		+ "Ticket Details : \n"
            		+ sb3;
            SendMail.sendEmail(to, subject, text);
        } catch (Exception e) {
            LOGGER.info("Error booking Ticket {}", e.getMessage());
        }
    }

    public void cancelBooking(UserDTO customer) {
        try {
            LOGGER.info("Enter booking ID:");
            String bookingId = reader.readLine();
            BookingDTO booking = bookingService.getById(bookingId);
            if (booking == null) {
                LOGGER.info("Invalid booking ID.");
                return;
            }
            BigDecimal price = booking.getPrice();
            BigDecimal refundAmount = price.multiply(BigDecimal.valueOf(0.7)); // calculate refund amount
            bookingService.delete(booking);
            BankAccountDTO bankAccount = bankAccountService.getById(customer.getUserId());
            bankAccount.setBalance(bankAccount.getBalance().add(refundAmount)); // add refund amount to user's account balance
            bankAccountService.update(bankAccount);
            LOGGER.info("Booking with ID {} cancelled. Refund amount of {} has been added to {}'s account balance.", bookingId, refundAmount, customer.getUserName());
            String to = customer.getEmail();
            String subject = "Flight Ticket Status";
           
            String text = "Dear " + customer.getUserName() + ",\n\n" 
            		+"Booking with ID "+bookingId + " cancelled."
            				+ " Refund amount of "+refundAmount+" has been added to "+customer.getUserName()+"'s account balance.";
            		
            SendMail.sendEmail(to, subject, text);
        } catch (IOException | BookingException e) {
            LOGGER.info("Error cancelling booking {}", e.getMessage());
        }
    }


    public void viewCustomerBookings(UserDTO customer) throws BookingException {
    	List<BookingDTO> customerTickets = bookingService.getAll().stream()
                .filter(booking -> booking.getUserId().equals(customer.getUserId()))
                .collect(Collectors.toList());
        if (customerTickets.isEmpty()) {
            LOGGER.info("No bookings found for customer {} " , customer.getUserName());
        } else {
            LOGGER.info("Bookings for customer {} : ", customer.getUserName());
            for (BookingDTO booking : customerTickets) {
                LOGGER.info(booking);
            }
        }
    }
    
    public void viewAllBookings() throws BookingException {
        List<BookingDTO> bookings = bookingService.getAll();
        if (bookings.isEmpty()) {
            LOGGER.info("No bookings found.");
            return;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(String.format(BOOKING_TABLE_FORMAT, 
                "Booking ID", "User ID", "Flight ID", AIRLINE_NAME, "Passenger Name", PRICE, "Created At"))
        		.append(System.lineSeparator());
        LOGGER.info(sb4);
        bookings.forEach(booking -> LOGGER.info(String.format(BOOKING_TABLE_FORMAT, 
                    booking.getBookingId(), booking.getUserId(), booking.getFlightId(), 
                    booking.getAirlineName(), booking.getPassengerName(), booking.getPrice(), booking.getCreatedAt())));
    }

    public void viewAllBookingsByFlightId() throws BookingException, IOException {
    	LOGGER.info("Enter flight Id: ");
    	String flightId = reader.readLine();
        List<BookingDTO> bookings = bookingService.getByFlightId(flightId);
        if (bookings.isEmpty()) {
            LOGGER.info("No bookings found for flight {}", flightId);
            return;
        }
        LOGGER.info("Bookings for flight {}:", flightId);
        StringBuilder sb5 = new StringBuilder();
        sb5.append(String.format(BOOKING_TABLE_FORMAT, 
                "Booking ID", "User ID", "Flight ID", AIRLINE_NAME, "Passenger Name", PRICE, "Created At"))
        		.append(System.lineSeparator());
        LOGGER.info(sb5);
        bookings.forEach(booking -> LOGGER.info(String.format(BOOKING_TABLE_FORMAT, 
                    booking.getBookingId(), booking.getUserId(), booking.getFlightId(), 
                    booking.getAirlineName(), booking.getPassengerName(), booking.getPrice(), booking.getCreatedAt())));
    }



}
