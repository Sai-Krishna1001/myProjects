package com.virtusa.ars;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtusa.ars.controller.BankAccountController;
import com.virtusa.ars.controller.BookingController;
import com.virtusa.ars.controller.FlightController;
import com.virtusa.ars.controller.UserController;
import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.dto.UserRole;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.FV;


public class App 
{
	private static final Logger LOGGER = LogManager.getLogger(App.class);
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static UserController userController = new UserController();
	private static FlightController flightController = new FlightController();
	private static BookingController bookingController = new BookingController();
	private static BankAccountController bankAccountController = new BankAccountController();
   
	// MAIN METHOD
	public static void main( String[] args ) throws NumberFormatException, IOException, BookingException
    {
		LOGGER.info(FV.WELCOME);
    	showMainMenu();
    }
    
    private static void showMainMenu() throws NumberFormatException, IOException, BookingException {
        
        LOGGER.info("1. Register as a Manager");
        LOGGER.info("2. Register as a Customer");
        LOGGER.info("3. Login as Manager/Customer");
        LOGGER.info("4. Exit");
        
        choice();
    }
    
    private static void choice() throws NumberFormatException, IOException, BookingException {
        int choice = getChoiceInput();
        
        if (choice == 1 || choice == 2) {
            handleRegistrationChoice(choice);
        } else if (choice == 3) {
            handleLoginChoice();
        } else if (choice == 4) {
            exitProgram();
        } else {
            showInvalidChoiceMessage();
            showMainMenu();
        }
    }

    private static int getChoiceInput() throws NumberFormatException, IOException, BookingException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (InputMismatchException e) {
            LOGGER.info(FV.INPUT_TYPE_ERR);
            showMainMenu();
        }
        return 0;
    }

    private static void handleRegistrationChoice(int choice) throws BookingException, IOException {
        LOGGER.info(FV.WELCOME_REGISTER);
        if (choice == 1) {
            registerManager();
            showMainMenu();
        } else {
            userController.registerUser(UserRole.CUSTOMER);
            showMainMenu();
        }
    }

    private static void handleLoginChoice() throws BookingException, NumberFormatException, IOException {
        LOGGER.info(FV.WELCOME_LOGIN);
        UserDTO user = userController.loginUser();
        if (user != null) {
            if (user.getUserRole().equals(UserRole.CUSTOMER)) {
                showCustomerMenu(user);
            } else if (user.getUserRole().equals(UserRole.MANAGER)) {
                showManagerMenu();
            }
        } else {
            LOGGER.info(FV.CREDENTIALS_INCORRECT);
            showMainMenu();
        }
    }

    private static void exitProgram() {
        LOGGER.info(FV.THANK_YOU);
        System.exit(0);
    }

    private static void showInvalidChoiceMessage() {
        LOGGER.info(FV.CHOOSE_OPTION);
    }

	private static void registerManager() throws IOException, BookingException {
		LOGGER.info("Enter the secret key of this system: ");
		String secretKey = br.readLine();
		if(secretKey.equals(FV.SECRET_KEY)) {
			userController.registerUser(UserRole.MANAGER);
		}else {
			LOGGER.info("You have no Access, to register as a Manager Without enter the SecretKey of this System.");
		}
	}

	private static void showCustomerMenu(UserDTO customer) throws NumberFormatException, IOException, BookingException {
		LOGGER.info(FV.WELCOME_CUSTOMER);
		LOGGER.info("1. Book Ticket");
		LOGGER.info("2. Cancel Ticket");
		LOGGER.info("3. Search Flight");
		LOGGER.info("4. View My Flight Tickets");
		LOGGER.info("5. Add Bank Account");
		LOGGER.info("6. Back");
		LOGGER.info("7. Exit");
		
		int choice = 0;
		try {
			choice = Integer.parseInt(br.readLine());
 			if(choice !=1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7) {
				LOGGER.info(FV.CHOOSE_OPTION);
				showCustomerMenu(customer);
			}else customerChoice(choice, customer);
 		} catch (InputMismatchException e) {
			LOGGER.info(FV.INPUT_TYPE_ERR);
			showCustomerMenu(customer);
		}
		
	}

	private static void customerChoice(int choice, UserDTO customer) throws NumberFormatException, IOException, BookingException {
 
		switch(choice) {
			case 1:{
 
				bookingController.addBooking(customer);
				showCustomerMenu(customer);
			}
			break;
			case 2:{
				bookingController.cancelBooking(customer);
				showCustomerMenu(customer);
			}
			break;
			case 3:{
				flightController.viewFlightsByRoute();
				showCustomerMenu(customer);
			}
			break;
			case 4:{
				bookingController.viewCustomerBookings(customer);
				showCustomerMenu(customer);
			}
			break;
			case 5:{
				bankAccountController.addBankAccount(customer);
				showCustomerMenu(customer);
			}
			break;
			case 6:{
				showMainMenu();
			}
			break;
			case 7:{
				LOGGER.info(FV.THANK_YOU);
				System.exit(0);
			}
			break;
			
			default:
				LOGGER.info(FV.SOMETHING_WRONG);
		}
	}

	private static void showManagerMenu() throws NumberFormatException, IOException, BookingException {
		LOGGER.info(FV.WELCOME_MANAGER);
		LOGGER.info("Please choose an option:");
		LOGGER.info("1. Add Flight");
		LOGGER.info("2. Delete Flight ");		
		LOGGER.info("3. Update Flight Details");
		LOGGER.info("4. View All Bookings");
		LOGGER.info("5. View All Bookings by FlightID");
		LOGGER.info("6. Back");
		LOGGER.info("7. Exit");
		
		int choice = 0;
		try {
			choice = Integer.parseInt(br.readLine());
			if(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7) {
				LOGGER.info(FV.CHOOSE_OPTION);
				showManagerMenu();
			}else {
				managerChoice(choice);
			}
		} catch (InputMismatchException e) {
			LOGGER.info(FV.INPUT_TYPE_ERR);
			showManagerMenu();
		} 
		
		
	}

	private static void managerChoice(int choice) throws NumberFormatException, IOException, BookingException {
		switch(choice) {
			case 1:{
					flightController.addFlight();
					showManagerMenu();
				}
				break;
			case 2:{
					flightController.deleteFlight();
					showManagerMenu();
				}
				break;
			case 3:{
					flightController.updateFlight();
					showManagerMenu();
				}
				break;
			case 4:{
					bookingController.viewAllBookings();
					showManagerMenu();
				}
				break;
			case 5:{
					bookingController.viewAllBookingsByFlightId();
					showManagerMenu();
				}
				break;
			case 6:{ 
					showMainMenu();
				}
				break;
			case 7:{
					LOGGER.info(FV.THANK_YOU);
					System.exit(0);
				}
				break;
			default:
				LOGGER.info(FV.SOMETHING_WRONG);
		}
	}
}
