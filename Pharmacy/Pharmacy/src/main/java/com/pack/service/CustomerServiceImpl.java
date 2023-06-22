package com.pack.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.dao.MedicureDao;
import com.pack.exceptions.CartNotFoundException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.Order;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	MedicureDao medicureDao;
	
	
	
	@Override
	 public Customer updateCustomer(Customer c)
	 {
		 return medicureDao.addCustomer(c);
	 }

	@Override
	public Customer addCustomer(Customer c) {

		
		return medicureDao.addCustomer(c);
	}
	
	@Override
	public HashMap<String, List<String>> viewMedicines() {

		return medicureDao.viewMedicine();
	}
	
	@Override
	public Medicine addCart(int cid,int pid,int mid, int q) {

		if(medicureDao.getCustMap().get(cid)==null) return null;

		Customer c=medicureDao.getCustMap().get(cid);
//		Medicine medicine=medicureDao.getPharmaMap().get(pid).getInventoryMap().get(mid);
		HashMap<Integer, Medicine> medicineMenu=medicureDao.getPharmaMap().get(pid).getInventoryMap();
		Medicine medicine=medicineMenu.get(mid);
		return medicureDao.addCart(c,medicine,q);
	}

	@Override
	public HashMap<Medicine,Integer> viewCart(int cid) throws CartNotFoundException {

		 
			return medicureDao.viewCart(medicureDao.getCustMap().get(cid));
		
	}

	
	
	@Override
	public Order checkout(int cid) throws CartNotFoundException {

		if(medicureDao.getCustMap().get(cid) == null) return null;
		Customer c=medicureDao.getCustMap().get(cid);
			return medicureDao.checkout(c);
		
	}

	 public Customer validateLogin(String username, String password) {
	        return medicureDao.validateLogin(username, password);

}
	 @Override
		public Customer signUp(Customer c) {
	
			return medicureDao.signUp(c);
		}
	 public Customer deleteCustomer(int cid) {
	
			return medicureDao.deleteCustomer(cid);
		}


}
