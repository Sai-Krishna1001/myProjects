package com.virtusa.ars.util;

import com.virtusa.ars.dao.BankAccountDAO;
import com.virtusa.ars.dao.BookingDAO;
import com.virtusa.ars.dao.FlightDAO;
import com.virtusa.ars.dao.UserDAO;

public class DAOFactory {
    private static DAOFactory instance;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public FlightDAO getFlightDAO() {
        return new FlightDAO();
    }

    public BookingDAO getBookingDAO() {
        return new BookingDAO();
    }

    public BankAccountDAO getBankAccountDAO() {
        return new BankAccountDAO();
    }
}
