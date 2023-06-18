package com.virtusa.ars.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.virtusa.ars.dao.FlightDAO;
import com.virtusa.ars.dto.FlightDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DAOFactory;

public class FlightService implements Service<FlightDTO> {

    private static final FlightDAO flightDao = DAOFactory.getInstance().getFlightDAO();

    @Override
    public void add(FlightDTO flight) throws BookingException {
        flightDao.save(flight);
    }

    @Override
    public void update(FlightDTO flight) throws BookingException {
        flightDao.update(flight);
    }

    @Override
    public void delete(FlightDTO flight) throws BookingException {
        flightDao.delete(flight);
    }

    @Override
    public FlightDTO getById(String id) throws BookingException {
        return flightDao.findById(id);
    }

    @Override
    public List<FlightDTO> getAll() throws BookingException {
    	
        List<FlightDTO> flights = flightDao.findAll();
        
        // Sort flights by price in ascending order
        flights.sort(Comparator.comparing(FlightDTO::getPrice));
        
        return flights;
    }
    
    public List<FlightDTO> findFlightsBySourceAndDestination(String source, String destination) throws BookingException {
        List<FlightDTO> flights = flightDao.findAll();
        
        // Filter flights based on source and destination
        flights = flights.stream()
                .filter(f -> f.getDeparture().equalsIgnoreCase(source) && f.getArrival().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
        
        return flights;
    }
}
