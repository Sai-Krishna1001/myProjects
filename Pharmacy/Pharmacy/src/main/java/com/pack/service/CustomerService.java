package com.pack.service;

import java.util.HashMap;
import java.util.List;

import com.pack.exceptions.CartNotFoundException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.Order;

public interface CustomerService {

	
	// customer
		 public Customer validateLogin(String username, String password);
		 Customer addCustomer(Customer c);//Additional
		 HashMap<String, List<String>> viewMedicines();
		 Medicine addCart(int cid, int pid, int mid, int q);
		 HashMap<Medicine, Integer> viewCart(int cid) throws CartNotFoundException;
		 Order checkout(int cid) throws CartNotFoundException;
		 Customer signUp(Customer c);
		 Customer updateCustomer(Customer c);
		 public Customer deleteCustomer(int cid);
}
