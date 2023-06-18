package com.virtusa.ars.controller;
import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.virtusa.ars.dto.FlightDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.dto.Airline;
import com.virtusa.ars.service.FlightService;
import com.virtusa.ars.util.ServiceFactory;

public class FlightController {
    
    private static final Logger LOGGER = LogManager.getLogger(FlightController.class);
    private static final String INVALID_ID_MESSAGE = "Invalid flight ID. Please enter a valid flight ID.";
    private final FlightService flightService = ServiceFactory.getInstance().getFlightService();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String ENTER_FLIGHT_ID = "Enter flight ID:";
    
    
    public void addFlight() {
        try {
            LOGGER.info(ENTER_FLIGHT_ID);
            String flightId = reader.readLine();
            LOGGER.info("Enter airline name:");
            Airline airlineName = Airline.valueOf(reader.readLine().toUpperCase());
            LOGGER.info("Enter departure location:");
            String departure = reader.readLine();
            LOGGER.info("Enter arrival location:");
            String arrival = reader.readLine();
            LOGGER.info("Enter duration:");
            String duration = reader.readLine();
            LOGGER.info("Enter price:");
            BigDecimal price = new BigDecimal(reader.readLine());
            
            FlightDTO flight = new FlightDTO(flightId, airlineName, departure, arrival, duration, price);
            flightService.add(flight);
            LOGGER.info("Flight added successfully: ");
        } catch (IOException | BookingException e) {
            LOGGER.info("Error adding flight. Please try again.");
        }
    }
    
    public void updateFlight() {
        try {
            LOGGER.info(ENTER_FLIGHT_ID);
            String flightId = reader.readLine().trim().toUpperCase();
            FlightDTO existingFlight = flightService.getById(flightId);
            
            if (existingFlight == null) {
                LOGGER.info(INVALID_ID_MESSAGE);
                return;
            }
            
            LOGGER.info("Enter airline name ({}):", existingFlight.getAirlineName());
            String airlineNameInput = reader.readLine();
            Airline airlineName = airlineNameInput.isBlank() ? existingFlight.getAirlineName() : Airline.valueOf(airlineNameInput.toUpperCase());
            
            LOGGER.info("Enter departure location ({}):", existingFlight.getDeparture());
            String departure = reader.readLine().isBlank() ? existingFlight.getDeparture() : reader.readLine();
            
            LOGGER.info("Enter arrival location ({}):", existingFlight.getArrival());
            String arrival = reader.readLine().isBlank() ? existingFlight.getArrival() : reader.readLine();
            
            LOGGER.info("Enter duration ({}):", existingFlight.getDuration());
            String duration = reader.readLine().isBlank() ? existingFlight.getDuration() : reader.readLine();
            
            LOGGER.info("Enter price ({}):", existingFlight.getPrice());
            BigDecimal price = reader.readLine().isBlank() ? existingFlight.getPrice() : new BigDecimal(reader.readLine());
            
            FlightDTO updatedFlight = new FlightDTO(flightId, airlineName, departure, arrival, duration, price);
            flightService.update(updatedFlight);
            LOGGER.info("Flight updated successfully: ");
        } catch (IOException | BookingException e) {
            LOGGER.info("Error updating flight. Please try again.");
        }
    }
    
    public void deleteFlight() {
        try {
            LOGGER.info(ENTER_FLIGHT_ID);
            String flightId = reader.readLine().trim().toUpperCase();
            
            FlightDTO flight = flightService.getById(flightId);
            if (flight == null) {
            	String flightNotFoundErr = "Flight with ID " + flightId + " not found";
                LOGGER.info(flightNotFoundErr);
                return;
            }
            
            flightService.delete(flight);
            String deleteFlightErr = "Flight with ID " + flightId + " has been deleted";
            LOGGER.info(deleteFlightErr);
        } catch (IOException e) {
            LOGGER.error("Error reading input", e);
        } catch (BookingException e) {
            LOGGER.error("Error deleting flight", e);
        }
    }

    public void viewFlightsByRoute() {
        try {
            LOGGER.info("Enter source:");
            String source = reader.readLine().trim().toUpperCase();
            LOGGER.info("Enter destination:");
            String destination = reader.readLine().trim().toUpperCase();
            
            List<FlightDTO> flights = flightService.findFlightsBySourceAndDestination(source, destination);
            
            if (flights.isEmpty()) {
            	String flightsEmptyErr = "No flights found between " + source + " and " + destination;
                LOGGER.info(flightsEmptyErr);
            } else {
            	String flightsFromToMsg = "Flights between " + source + " and " + destination + ":";
                LOGGER.info(flightsFromToMsg);
                for (FlightDTO flight : flights) {
                    LOGGER.info(flight);
                }
            }
        } catch (IOException | BookingException e) {
            LOGGER.error("An error occurred while viewing flights by route: {}", e.getMessage());
            
        }
    }
}
            
