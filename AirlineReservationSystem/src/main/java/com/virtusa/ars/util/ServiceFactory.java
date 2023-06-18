package com.virtusa.ars.util;

import com.virtusa.ars.service.BankAccountService;
import com.virtusa.ars.service.BookingService;
import com.virtusa.ars.service.FlightService;
import com.virtusa.ars.service.UserService;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
   
    }

    public UserService getUserService() {
        return new UserService();
    }

    public FlightService getFlightService() {
        return new FlightService();
    }

    public BookingService getBookingService() {
        return new BookingService();
    }

    public BankAccountService getBankAccountService() {
        return new BankAccountService();
    }
}
