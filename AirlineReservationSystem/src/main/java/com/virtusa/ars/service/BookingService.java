package com.virtusa.ars.service;

import java.util.List;
import java.util.stream.Collectors;

import com.virtusa.ars.dao.BookingDAO;
import com.virtusa.ars.dto.BookingDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DAOFactory;

public class BookingService implements Service<BookingDTO> {

    private static final BookingDAO bookingDao = DAOFactory.getInstance().getBookingDAO();

    @Override
    public void add(BookingDTO booking) throws BookingException {
        bookingDao.save(booking);
    }

    @Override
    public void update(BookingDTO booking) throws BookingException {
        bookingDao.update(booking);
    }

    @Override
    public void delete(BookingDTO booking) throws BookingException {
        bookingDao.delete(booking);
    }

    @Override
    public BookingDTO getById(String id) throws BookingException {
        return bookingDao.findById(id);
    }

    @Override
    public List<BookingDTO> getAll() throws BookingException {
    	List<BookingDTO> bookings = bookingDao.findAll();
    	bookings.sort((b1, b2) -> b1.getCreatedAt().compareTo(b2.getCreatedAt()));
        return bookings;
    }
    
    public List<BookingDTO> getByFlightId(String flightId) throws BookingException{
    	return getAll().stream().filter(f -> f.getFlightId().equals(flightId)).collect(Collectors.toList());
    }
}
