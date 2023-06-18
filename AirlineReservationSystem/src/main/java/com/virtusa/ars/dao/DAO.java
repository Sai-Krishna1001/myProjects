package com.virtusa.ars.dao;

import java.util.List;
import com.virtusa.ars.exception.BookingException;

public interface DAO<T> {
	void save(T t) throws BookingException;
	T findById(String id) throws BookingException;
	List<T> findAll() throws BookingException;
	void update(T t) throws BookingException;
	void delete(T t) throws BookingException;
}
