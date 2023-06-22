package com.pack.service;

import java.util.HashMap;
import java.util.List;

import com.pack.exceptions.CustomerNotFoundException;
import com.pack.exceptions.InventoryEmptyException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;
import com.pack.model.Supplier;

public interface AdminService {
	// admin
		Customer viewCustomer(int cid) throws CustomerNotFoundException;
		HashMap<Integer, Customer> viewCustomers();
		Supplier addSupplier(Supplier supplier);
		PharmaCompany addPharmaCompany(PharmaCompany p);
		Medicine addMedicine(Medicine m,int pid);
		HashMap<Integer, PharmaCompany> viewPharmaCompanies();
		public HashMap<Integer,Supplier> viewSupplier();
		public HashMap<String,List<String>> viewInventory() throws InventoryEmptyException;

}
