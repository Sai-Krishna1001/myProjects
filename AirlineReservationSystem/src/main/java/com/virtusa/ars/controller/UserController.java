package com.virtusa.ars.controller;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.dto.UserRole;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.service.UserService;
import com.virtusa.ars.util.IdGenerator;
import com.virtusa.ars.util.ServiceFactory;
import com.virtusa.ars.validation.EmailValidator;
import com.virtusa.ars.validation.PasswordValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserController {
	
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private PasswordValidator passwordValidator = new PasswordValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private static final String ENTER_USER_ID = "Eneter user Id: ";
    private static final String USER_WITH_ID_NOT_FOUND = "User with id {} not found.";
   

    
    public void registerUser(UserRole userRole) throws BookingException {
        try {
            LOGGER.info("Enter user name: ");
            String userName = reader.readLine();
            LOGGER.info("Enter email: ");
            String email = reader.readLine();
            // validate email address
        	if (emailValidator.validate(email)) {
    	    	LOGGER.info("Please enter a password:");
    	    	String password = reader.readLine();  	    	
    	    	// validate password
    	    	String errorMsg = passwordValidator.validate(password);
    	    	if (errorMsg==null) {
    		    	LOGGER.info("Please confirm your password:");
    		    	String confirmPassword = reader.readLine();   		
    		    	if (!password.equals(confirmPassword)) {
    		    	    LOGGER.info("Password do not match. Registration failed. Please try again.");
    		    	}else {
	    		    	// for generating userId
	    		    	boolean userFlag = UserRole.CUSTOMER.equals(userRole);	    		    
	    		    	UserDTO user = new UserDTO(IdGenerator.generateUserId(userFlag),userName, email, password, userRole);	    		    	
	    		    	userService.add(user);
	    		    	LOGGER.info("Registration successful!");
    		    	}
    	    	} else {
    	    		errorMsg = "Invalid password: "+ errorMsg;
    	    	    LOGGER.info(errorMsg);
    	    	}
        	}else {
        	    LOGGER.info("Invalid email address.");
        	    
        	}
        } catch (IOException | BookingException e) {
            LOGGER.error("Error adding user:{} ", e.getMessage());
        }
    }

    public UserDTO loginUser() {
		try {
			LOGGER.info("Please enter your email:");
	    	String email = reader.readLine();
	    	
	    	LOGGER.info("Please enter your password:");
	    	String password = reader.readLine();
			UserDTO user = userService.getUserByEmailAndPassword(email, password);
			if (user != null) {
				LOGGER.info("Login Successfull!");
	            return user;
	        }else {
	        	LOGGER.info("User Not Found");
	        }
		} catch (IOException | BookingException e) {
			LOGGER.error("Error login user: {}", e.getMessage());
		}
		return null;
        
    }
    public void updateUser() {
        try {
            LOGGER.info(ENTER_USER_ID);
            String userId = reader.readLine();
            UserDTO user = userService.getById(userId);
            if (user != null) {
                LOGGER.info("Enter user name ({}):", user.getUserName());
                String userName = reader.readLine();
                LOGGER.info("Enter email ({}):", user.getEmail());
                String email = reader.readLine();
                LOGGER.info("Enter password ({}):", user.getPassword());
                String password = reader.readLine();
                if (!userName.isEmpty()) {
                    user.setUserName(userName);
                }
                if (!email.isEmpty()) {
                    user.setEmail(email);
                }
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }
                userService.update(user);
                LOGGER.info("User updated successfully.");
            } else {
            	LOGGER.warn(USER_WITH_ID_NOT_FOUND, userId);
            }
        } catch (IOException | BookingException e) {
            LOGGER.error("Error updating user: {}", e.getMessage());
        }
    }

    public void deleteUser() {
        try {
            LOGGER.info("Enter user id: ");
            String userId = reader.readLine();
            UserDTO user = userService.getById(userId);
            if (user != null) {
                userService.delete(user);
                LOGGER.info("User deleted successfully.");
            } else {
            	LOGGER.warn(USER_WITH_ID_NOT_FOUND, userId);
            }
        } catch (IOException | BookingException e) {
            LOGGER.error("Error deleting user: {}", e.getMessage());
        }
    }

    public void viewUser() {
        try {
            LOGGER.info("Enter user id: ");
            String userId = reader.readLine();
            UserDTO user = userService.getById(userId);
            if (user != null) {
                LOGGER.info(user);
            } else {
            	LOGGER.warn(USER_WITH_ID_NOT_FOUND, userId);

            }
        } catch (IOException | BookingException e) {
            LOGGER.error("Error viewing user:{} ", e.getMessage());
        }
    }
    public void viewAllUsers() {
        try {
            List<UserDTO> users = userService.getAll();
            for (UserDTO user : users) {
                LOGGER.info(user);
            }
        } catch (BookingException e) {
            LOGGER.error("Error viewing all users: {} ", e.getMessage());
        }
    }
}
