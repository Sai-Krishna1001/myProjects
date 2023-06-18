package com.virtusa.ars.service;
import java.util.List;
import com.virtusa.ars.exception.BookingException;

public interface Service<T> {
    void add(T item) throws BookingException;
    void update(T item) throws BookingException;
    void delete(T item) throws BookingException;
    T getById(String id) throws BookingException;
    List<T> getAll() throws BookingException;
}
