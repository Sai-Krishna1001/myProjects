package com.virtusa.ars.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.dto.UserRole;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DBUtil;
public class UserDAO implements DAO<UserDTO> {
	
	private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);
	private static final String INSERT_SQL = "INSERT INTO users (user_id, user_name, email, password, user_role) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM users";
    private static final String UPDATE_SQL = "UPDATE users SET user_name = ?, email = ?, password = ?, user_role = ? WHERE user_id = ?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE user_id = ?";
    
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "user_name";
    private static final String USER_ROLE = "user_role";
   
    
    @Override
    public void save(UserDTO user) throws BookingException {
        
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(INSERT_SQL)) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUserRole().toString());
            statement.executeUpdate();
            
         // to catch the exception for duplication entries of email
        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                LOGGER.info("The email address you entered is already registered.");
                
            } else {
                LOGGER.info("An error occurred while inserting the record: {}", e.getMessage());
                
            }

        } catch (SQLException e) {
            throw new BookingException("Error saving user: " + e.getMessage(), e);
        }
    }
    @Override
    public UserDTO findById(String id) throws BookingException {
        
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserDTO(
                resultSet.getString(USER_ID),
                resultSet.getString(USER_NAME),
                resultSet.getString(EMAIL),
                resultSet.getString(PASSWORD),
                UserRole.valueOf(resultSet.getString(USER_ROLE)));
               
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new BookingException("Error finding user by id: " + e.getMessage(), e);
        }
    }
    @Override
    public List<UserDTO> findAll() throws BookingException {
        
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<UserDTO> users = new ArrayList<>();
            while (resultSet.next()) {
            	UserDTO user = new  UserDTO(
                        resultSet.getString(USER_ID),
                        resultSet.getString(USER_NAME),
                        resultSet.getString(EMAIL),
                        resultSet.getString(PASSWORD),
                        UserRole.valueOf(resultSet.getString(USER_ROLE)));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new BookingException("Error finding all users: " + e.getMessage(), e);
        }
    }
    @Override
    public void update(UserDTO user) throws BookingException {
       
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(UPDATE_SQL)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUserRole().toString());
            statement.setString(5, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BookingException("Error updating user: " + e.getMessage(), e);
        }
    }
    @Override
    public void delete(UserDTO user) throws BookingException {
       
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(DELETE_SQL)) {
            statement.setString(1, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BookingException("Error deleting user: " + e.getMessage(), e);
        }
    }
    
    public UserDTO getUserByEmailAndPassword(String email, String password) throws BookingException{
        UserDTO user = null;
        
        try (PreparedStatement stmt = DBUtil.getConnection().prepareStatement("SELECT * FROM users WHERE email=? AND password=?")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String id = rs.getString(USER_ID);
                String username = rs.getString(USER_NAME);
                UserRole role = UserRole.valueOf(rs.getString(USER_ROLE));
                user = new UserDTO(id,username, email, password, role);
            }
        } catch (SQLException e) {
           throw new BookingException("Error finding user by email and password: " + e.getMessage());
        }
        
        return user;
    }
}