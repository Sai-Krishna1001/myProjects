package com.pack.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.pack.exceptions.CartNotFoundException;
import com.pack.exceptions.CustomerNotFoundException;
import com.pack.exceptions.InventoryEmptyException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.Order;
import com.pack.model.PharmaCompany;
import com.pack.model.Supplier;

@Repository
public class MedicureDao {
	
	HashMap<Integer,Customer> custMap = new HashMap<>();
	HashMap<Integer,Supplier> supplierMap = new HashMap<>();
	HashMap<Integer,PharmaCompany> pharmaMap=new HashMap<>();
	HashMap<Integer,Medicine> medicineMap=new HashMap<>();
	HashMap<Integer,Order> orderMap=new HashMap<>();

	HashMap<Customer,HashMap<Medicine,Integer>> cartMap=new HashMap<>();
	
	
	
	
	
	
	
	 public MedicureDao() {
	        super();
	        Customer admin = new Customer(0,"Admin",8889,"Admin@address","admin","abcd","admin",null);
	        custMap.put(admin.getCid(),admin);
	        cartMap.put(admin, new HashMap<>());
	    }
	
	
	@Override
	public String toString() {
		return "MedicureDao [cartMap=" + cartMap + "]";
	}

	public HashMap<Customer, HashMap<Medicine, Integer>> getCartMap() {
		return cartMap;
	}

	public void setCartMap(HashMap<Customer, HashMap<Medicine, Integer>> cartMap) {
		this.cartMap = cartMap;
	}

	public HashMap<Integer, Customer> getCustMap() {
		return custMap;
	}

	public void setCustMap(HashMap<Integer, Customer> custMap) {
		this.custMap = custMap;
	}

	public HashMap<Integer, Supplier> getSupplierMap() {
		return supplierMap;
	}

	public void setSupplierMap(HashMap<Integer, Supplier> supplierMap) {
		this.supplierMap = supplierMap;
	}

	public HashMap<Integer, PharmaCompany> getPharmaMap() {
		return pharmaMap;
	}

	public void setPharmaMap(HashMap<Integer, PharmaCompany> pharmaMap) {
		this.pharmaMap = pharmaMap;
	}

	public HashMap<Integer, Medicine> getMedicineMap() {
		return medicineMap;
	}

	public void setMedicineMap(HashMap<Integer, Medicine> medicineMap) {
		this.medicineMap = medicineMap;
	}

	public HashMap<Integer, Order> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(HashMap<Integer, Order> orderMap) {
		this.orderMap = orderMap;
	}

	public Customer addCustomer(Customer cust) {
		custMap.put(cust.getCid(),cust);
		return cust;
	}
	
	public Supplier addSupplier(Supplier s) {
		supplierMap.put(s.getSid(),s);
		return s;
	}
	
	public PharmaCompany addPharma(PharmaCompany p) {
		pharmaMap.put(p.getPcid(),p);
		return p;
	}
	
	
	
	
	//customer
	
	
	public Customer updateCustomer(Customer c)
	 {
		return custMap.put(c.getCid(), c);
	 }
	 
	 
	
	
	public Medicine addCart(Customer c,Medicine m, int q) {
		if(cartMap.get(c)!=null) {
			cartMap.get(c).put(m, q);
		}
			else {
			cartMap.put(c,new HashMap<>());
			cartMap.get(c).put(m, q);
			
		}
		return m;
	}
	
	public HashMap<Medicine,Integer> viewCart(Customer customer) throws CartNotFoundException{
		
		
		if(cartMap.get(customer)==null)
			throw new CartNotFoundException("Cart is not Yet Filled");
		return cartMap.get(customer);
	}
	
	
	

	public Order checkout(Customer c) throws CartNotFoundException {
		double sum = 0;
		HashMap<Medicine,Integer> myCart = viewCart(c);
		for(Medicine m: myCart.keySet()) {
			sum+=m.getMprice()*myCart.get(m);	
		}
		
		String membership=c.getMembership();
		
		String deliveryDateAndTime="Your order will be delivered in 2 days from the date of ordered";
		  
		 
		if(membership.equals("platinum"))
		{
			double discount=((double)20/(double)100)*sum;
			sum-=discount;
			Date dt = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.DATE, 1);
			dt = cal.getTime();
			deliveryDateAndTime=dt.toString();
			
		}
		if(membership.equals("gold"))
		{
			double discount=((double)20/(double)100)*sum;
			sum-=discount;
			if(sum>500)
			{
				Date dt = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DATE, 1);
				dt = cal.getTime();
				deliveryDateAndTime=dt.toString();
			}
			
		}
		if(membership.equals("silver"))
		{
			double discount=((double)20/(double)100)*sum;
			sum-=discount;
		}
		int oid = orderMap.size()+1;
		
		Order o = new Order(oid,c,myCart,sum,deliveryDateAndTime);
		orderMap.put(oid, o);
		return o;
	
	}


	 public Customer validateLogin(String username, String password) {
	        List<Customer> k = custMap.values().stream()
	        .filter(c -> (c.getUsername().equals(username) && c.getPassword().equals(password)))
	        .collect(Collectors.toList());

	        if(!k.isEmpty()) return k.get(0);
	        else return null;
	    }
	 
	 public PharmaCompany validatePharmaCompanyLogin(String username, String password) {
	
		 List<PharmaCompany> k = pharmaMap.values().stream()
			        .filter(c -> (c.getEmail().equals(username) && c.getPassword().equals(password)))
			        .collect(Collectors.toList());
		 if(!k.isEmpty()) return k.get(0);
	        else return null;
		}


	public Customer viewCustomer(int cid) throws CustomerNotFoundException {

		Customer cust=custMap.get(cid);
		if(cust==null)
			throw new CustomerNotFoundException("not found customer");
		return cust;
	}


	public Customer signUp(Customer c) {

		return custMap.put(c.getCid(),c);
	}


	public PharmaCompany signUpPharma(PharmaCompany p) {

		return pharmaMap.put(p.getPcid(), p);
	}


	public Medicine addInventory(Medicine m,int pid) {

		if(pharmaMap.get(pid).getInventoryMap()==null) {
		HashMap<Integer,Medicine> inventory=new HashMap<>();
		inventory.put(m.getMid(), m);
		pharmaMap.get(pid).setInventoryMap(inventory);
		}
		else
			pharmaMap.get(pid).getInventoryMap().put(m.getMid(), m);
		return m;
	}
	
	public Medicine addMedicine(Medicine m,int pid) {
		if(pharmaMap.get(pid).getInventoryMap()==null) {
			HashMap<Integer,Medicine> inventory=new HashMap<>();
			inventory.put(m.getMid(), m);
			pharmaMap.get(pid).setInventoryMap(inventory);
			}
			else
				pharmaMap.get(pid).getInventoryMap().put(m.getMid(), m);
			return m;
	}


	public HashMap<String, List<String>> viewInventory() throws InventoryEmptyException {

		HashMap<String, List<String>> ans=new HashMap<>();
		Set<Map.Entry<Integer,PharmaCompany>> set=pharmaMap.entrySet();
		int countOfPharmas=set.size();
		
			for(Map.Entry<Integer,PharmaCompany> pharmaMapEntry:set)
			{
				Map<Integer,Medicine> medicineMap1=pharmaMapEntry.getValue().getInventoryMap();
				if(medicineMap1==null)
					
					countOfPharmas--;
				else
				
					break;
			}
		if(countOfPharmas==0)
		{
			throw new InventoryEmptyException("Inventory is Completely Empty");
		}
		else {
		for(Map.Entry<Integer,PharmaCompany> pharmaMapEntry:set)
		{
			String pharmaCompany=pharmaMapEntry.getValue().getPcname();
			
			//Map<Integer,Medicine> medicineMap=pharmaMapEntry.getValue().getInventoryMap();
			
			List<String> medicineList=new ArrayList<>();
			Set<Map.Entry<Integer,Medicine>> set2=pharmaMapEntry.getValue().getInventoryMap().entrySet();
			for(Map.Entry<Integer,Medicine> medi:set2)
			{
				String k=medi.getValue().getMname();
				medicineList.add(k);
			}
			
			ans.put(pharmaCompany,medicineList);
		}
		}
		
		return ans;
	}

	public HashMap<String, List<String>> viewMedicine()
	{
		HashMap<String,List<String>> ans=new HashMap<>();
		Set<Map.Entry<Integer,PharmaCompany>> set=pharmaMap.entrySet();
		
		for(Map.Entry<Integer,PharmaCompany> pharmaMapEntry:set)
		{
			String pharmaCompany=pharmaMapEntry.getValue().getPcname();
			
			//Map<Integer,Medicine> medicineMap=pharmaMapEntry.getValue().getInventoryMap();
			
			List<String> medicineList=new ArrayList<>();
			Set<Map.Entry<Integer,Medicine>> set2=pharmaMapEntry.getValue().getInventoryMap().entrySet();
			for(Map.Entry<Integer,Medicine> medi:set2)
			{
				String k=medi.getValue().getMname();
				medicineList.add(k);
			}
			
			ans.put(pharmaCompany,medicineList);
		}
		
		return ans;
	}
	

	public HashMap<Integer, Customer> viewCustomers() {

		return custMap;
		
	}


	public HashMap<Integer, PharmaCompany> viewPharmaCompanies() {

		return pharmaMap;
	}


	public Customer deleteCustomer(int cid) {

		Customer c=custMap.get(cid);
		custMap.remove(cid);
		return c;
	}


	
	
	
}
