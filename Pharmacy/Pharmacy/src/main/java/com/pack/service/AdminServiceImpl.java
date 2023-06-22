package com.pack.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.dao.MedicureDao;
import com.pack.exceptions.CustomerNotFoundException;
import com.pack.exceptions.InventoryEmptyException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;
import com.pack.model.Supplier;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	MedicureDao medicureDao;
	
	
	public Customer validateAdminLogin(String username, String password) {
		
		return medicureDao.validateLogin(username, password);
	}
	
	@Override
	public Supplier addSupplier(Supplier supplier) {
		
		return medicureDao.addSupplier(supplier);
	}

	@Override
	public PharmaCompany addPharmaCompany(PharmaCompany p) {
		
		return medicureDao.addPharma(p);
	}
	

	public HashMap<Integer,Supplier> viewSupplier(){
		return medicureDao.getSupplierMap();
	}

	public Customer viewCustomer(int cid) throws CustomerNotFoundException  {
		
		
		
			return medicureDao.viewCustomer(cid);
		
		
		
	}
	@Override
	public Medicine addMedicine(Medicine m,int pid) {
		
		return medicureDao.addMedicine(m,pid);
	}
	
	@Override
	public HashMap<String, List<String>> viewInventory() throws InventoryEmptyException {
		
		 return medicureDao.viewInventory();
	}

	@Override
	public HashMap<Integer, Customer> viewCustomers() {
		
		return medicureDao.viewCustomers();
		
	}

	@Override
	public HashMap<Integer, PharmaCompany> viewPharmaCompanies() {
		
		return medicureDao.viewPharmaCompanies();
	}

	

	

}
