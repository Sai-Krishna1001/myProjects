package com.pack.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pack.exceptions.CustomerNotFoundException;
import com.pack.exceptions.InventoryEmptyException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;
import com.pack.model.Supplier;
import com.pack.service.AdminServiceImpl;


@RestController
@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
	
	@Autowired
	AdminServiceImpl service;
	
	static Logger logger=Logger.getLogger(AdminController.class.getName());
	
	 @PostMapping("/logoutAdmin")
	   public boolean logout(HttpSession session) {
	       session.invalidate();
	       logger.info("admin logged out");
	       return true;
	   }
	
	@ResponseBody
	   @ResponseStatus( HttpStatus.OK )
	@PostMapping("/loginAdmin/{username}/{password}")
	   public ResponseEntity<Customer> validateAdminLogin(@PathVariable String username,@PathVariable String password,HttpSession session) {
	       Customer c = service.validateAdminLogin(username, password);
	       if(c != null) {
	           session.setAttribute("loggedUser", c);
	           logger.info("admin logged In");
	           return new ResponseEntity<>(c,HttpStatus.OK);
	       }
	       logger.error("Admin Details Are Not Valid");
	       return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }

	
	@GetMapping("/viewCustomer/{cid}")
	   public ResponseEntity<Customer> viewCustomer(@PathVariable int cid,HttpSession session) 
		{
		Customer p=(Customer) session.getAttribute("loggedUser");
		if(p.getRole().equals("admin"))
		{
			
			 try {
				service.viewCustomer(cid);
				return new ResponseEntity<>(service.viewCustomer(cid),HttpStatus.OK);
			} catch (CustomerNotFoundException e) {
	
				System.out.println(e);
			} 
			 
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	
	 @GetMapping("/viewCustomers")
	   public  ResponseEntity<HashMap<Integer, Customer>> viewCustomers(HttpSession session) {
		 Customer p=(Customer) session.getAttribute("loggedUser");
			if(p.getRole().equals("admin"))
			{
				service.viewCustomers();
				return new ResponseEntity<>(service.viewCustomers(),HttpStatus.OK);
			}
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }
	 
	 @PostMapping("/addSupplier")
	   public Supplier addSupplier(@RequestBody Supplier s) {
		   return service.addSupplier(s);
	   }
	   
	  
	   @PostMapping("/addPharmaCompany")
	   public ResponseEntity<PharmaCompany> addPharmaCompany(@RequestBody PharmaCompany p,HttpSession session) {
		   
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("admin"))
		   {
			   service.addPharmaCompany(p);
			   logger.info("New Pharma Company Added By Admin");
			   return new ResponseEntity<>(service.addPharmaCompany(p),HttpStatus.OK);}
			   
		   
		    
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }
	   
	   @PostMapping("/addMedicine/{pid}")
	   public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine m,@PathVariable int pid,HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("admin")) {
			    service.addMedicine(m,pid);
			    logger.info("New Medicine added into Inventory By Admin");
			    return new ResponseEntity<>(service.addMedicine(m,pid),HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }
	   

	   @GetMapping("/viewPharmaCompanies")
	   public  ResponseEntity<HashMap<Integer, PharmaCompany>> viewPharmaCompanies(HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("admin"))
		   {
			   service.viewPharmaCompanies();
			   return new ResponseEntity<>(service.viewPharmaCompanies(),HttpStatus.OK);
		   }
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
		   
	   }
	   
	   @GetMapping("/viewInventory")
	   public ResponseEntity<HashMap<String, List<String>>> viewInventory(HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("admin")) {
		 try {
			 service.viewInventory();
			return new ResponseEntity<>(service.viewInventory(),HttpStatus.OK);
		} catch (InventoryEmptyException e) {

			System.out.println(e);
		}}
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }
	   
	   @GetMapping("/viewSuppliers")
	   public  HashMap<Integer, Supplier> viewSuppliers() {
		return service.viewSupplier();
		   
	   }

}
