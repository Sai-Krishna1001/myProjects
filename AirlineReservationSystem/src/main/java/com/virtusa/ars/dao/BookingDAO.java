package com.virtusa.ars.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.virtusa.ars.dto.BookingDTO;
import com.virtusa.ars.dto.Airline;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DBUtil;
public class BookingDAO implements DAO<BookingDTO> {
   
    private static final String SAVE_SQL = "INSERT INTO booking (booking_id, user_id, flight_id, airline_name, passenger_name, price) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM booking WHERE booking_id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM booking";
    private static final String UPDATE_SQL = "UPDATE booking SET user_id = ?, flight_id = ?, airline_name = ?, passenger_name = ?, price = ? WHERE booking_id = ?";
    private static final String DELETE_SQL = "DELETE FROM booking WHERE booking_id = ?";
    @Override
    public void save(BookingDTO booking) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(SAVE_SQL)) {
            statement.setString(1, booking.getBookingId());
            statement.setString(2, booking.getUserId());
            statement.setString(3, booking.getFlightId());
            statement.setString(4, booking.getAirlineName().name());
            statement.setString(5, booking.getPassengerName());
            statement.setBigDecimal(6, booking.getPrice());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted <= 0) {
                throw new BookingException("Failed to save booking.");
            }
        } catch (SQLException e) {
            throw new BookingException("Failed to save booking.", e);
        }
    }
    @Override
    public BookingDTO findById(String id) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
            throw new BookingException("Booking not found.");
        } catch (SQLException e) {
            throw new BookingException("Failed to find booking by id.", e);
        }
    }
    @Override
    public List<BookingDTO> findAll() throws BookingException {
        List<BookingDTO> bookings = new ArrayList<>();
        try (Statement statement = DBUtil.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(FIND_ALL_SQL);
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;
        } catch (SQLException e) {
            throw new BookingException("Failed to find all bookings.", e);
        }
    }
    @Override
    public void update(BookingDTO booking) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(UPDATE_SQL)) {
            statement.setString(1, booking.getUserId());
            statement.setString(2, booking.getFlightId());
            statement.setString(3, booking.getAirlineName().name());
            statement.setString(4, booking.getPassengerName());
            statement.setBigDecimal(5, booking.getPrice());
            statement.setString(6, booking.getBookingId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new BookingException("Failed to update booking.");
            }
        } catch (SQLException e) {
            throw new BookingException("Failed to update booking.", e);
        }
    }
    @Override
    public void delete(BookingDTO booking) throws BookingException {
    	try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(DELETE_SQL)) {
	        statement.setString(1, booking.getBookingId());
	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted <= 0) {
	            throw new BookingException("Failed to delete booking.");
	        }
    	} catch (SQLException e) {
    		throw new BookingException("Failed to delete booking.", e);
    	}
    }
    
    
    private BookingDTO mapResultSetToBooking(ResultSet rs) throws SQLException {
        BookingDTO booking = new BookingDTO();
        booking.setBookingId(rs.getString("booking_id"));
        booking.setUserId(rs.getString("user_id"));
        booking.setFlightId(rs.getString("flight_id"));
        booking.setAirlineName(Airline.valueOf(rs.getString("airline_name")));
        booking.setPassengerName(rs.getString("passenger_name"));
        booking.setPrice(rs.getBigDecimal("price"));
        booking.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return booking;
    }
}
