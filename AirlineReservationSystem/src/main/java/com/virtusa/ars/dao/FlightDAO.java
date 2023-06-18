package com.virtusa.ars.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.virtusa.ars.dto.FlightDTO;
import com.virtusa.ars.dto.Airline;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DBUtil;
public class FlightDAO implements DAO<FlightDTO> {
	
    private static final String INSERT_SQL = "INSERT INTO flight(flight_id, airline_name, departure, arrival, duration, price) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT flight_id, airline_name, departure, arrival, duration, price FROM flight WHERE flight_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT flight_id, airline_name, departure, arrival, duration, price FROM flight";
    private static final String UPDATE_SQL = "UPDATE flight SET airline_name = ?, departure = ?, arrival = ?, duration = ?, price = ? WHERE flight_id = ?";
    private static final String DELETE_SQL = "DELETE FROM flight WHERE flight_id = ?";
    @Override
    public void save(FlightDTO flight) throws BookingException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_SQL)) {
            statement.setString(1, flight.getFlightId());
            statement.setString(2, flight.getAirlineName().name());
            statement.setString(3, flight.getDeparture());
            statement.setString(4, flight.getArrival());
            statement.setString(5, flight.getDuration());
            statement.setBigDecimal(6, flight.getPrice());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted <= 0) {
                throw new BookingException("Failed to save flight.");
            }
        } catch (SQLException e) {
            throw new BookingException("Error saving flight: " + e.getMessage(), e);
        }
    }
    @Override
    public FlightDTO findById(String id) throws BookingException {
        FlightDTO flight = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    flight = mapResultSetToFlight(rs);
                }
            }
        } catch (SQLException e) {
            throw new BookingException("Error finding flight by id: " + e.getMessage(), e);
        }
        if (flight == null) {
            throw new BookingException("Flight with id " + id + " not found.");
        }
        return flight;
    }
    @Override
    public List<FlightDTO> findAll() throws BookingException {
        List<FlightDTO> flights = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ALL_SQL);
        		ResultSet rs = statement.executeQuery();
             ) {
         	
            while (rs.next()) {
                FlightDTO flight = mapResultSetToFlight(rs);
                flights.add(flight);
            	 
            }
        } catch (SQLException e) {
            throw new BookingException("Error finding all flights: " + e.getMessage(), e);
        }
        return flights;
    }
    @Override
    public void update(FlightDTO flight) throws BookingException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, flight.getAirlineName().name());
            statement.setString(2, flight.getDeparture());
            statement.setString(3, flight.getArrival());
            statement.setString(4, flight.getDuration());
            statement.setBigDecimal(5, flight.getPrice());
            statement.setString(6, flight.getFlightId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new BookingException("Failed to update flight.");
            }
        } catch (SQLException e) {
            throw new BookingException("Failed to update flight." + e.getMessage(), e);
        }
    }
    
    @Override
    public void delete(FlightDTO flight) throws BookingException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_SQL)) {
            statement.setString(1, flight.getFlightId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new BookingException("Failed to delete flight.");
            }
        } catch (SQLException e) {
            throw new BookingException("Error while deleting flight: " + e.getMessage(), e);
        }
    }
    
    private FlightDTO mapResultSetToFlight(ResultSet rs) throws SQLException {
    	FlightDTO flight = new FlightDTO();
        flight.setFlightId(rs.getString("flight_id"));
        flight.setAirlineName(Airline.valueOf(rs.getString("airline_name")));
        flight.setDeparture(rs.getString("departure"));
        flight.setArrival(rs.getString("arrival"));
        flight.setDuration(rs.getString("duration"));
        flight.setPrice(rs.getBigDecimal("price"));
        return flight;
    }
}