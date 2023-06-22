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

import com.pack.exceptions.CartNotFoundException;
import com.pack.model.Customer;
import com.pack.model.Medicine;
import com.pack.model.Order;
import com.pack.service.CustomerServiceImpl;

@RestController
@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
	
	@Autowired
	CustomerServiceImpl service;
	
	static Logger logger=Logger.getLogger(CustomerController.class.getName());
	
	 @PostMapping("/logoutCustomer")
	   public boolean logout(HttpSession session) {
	       session.invalidate();
	       logger.info("Customer logged out");
	       return true;
	   }
	
	
	@ResponseBody
	   @ResponseStatus( HttpStatus.OK )
	@PostMapping("/loginCustomer/{username}/{password}")
	   public ResponseEntity<Customer> validateLogin(@PathVariable String username,@PathVariable String password,HttpSession session) {
	       Customer c = service.validateLogin(username, password);
	       if(c != null) {
	           session.setAttribute("loggedUser", c);
	           logger.info("Customer logged In");
	           return new ResponseEntity<>(c,HttpStatus.OK);
	       }
	       logger.error("Customer details Not Valid");
	       return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }

	
	@PostMapping("/addCustomer")
	   public Customer addCustomer(@RequestBody Customer cust)
		{
		   logger.info("Customer Signed Up");
			return service.addCustomer(cust);  
		}
	
	@GetMapping("/viewMedicines")
	   public  ResponseEntity<HashMap<String, List<String>>> viewMedicines(HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("user"))
		   {
			   service.viewMedicines();
			   return new ResponseEntity<>(service.viewMedicines(),HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);  
		   
	   }
	
	@PostMapping("/addCart/{cid}/{pid}/{mid}/{q}")
	   public ResponseEntity<Medicine> addCart(@PathVariable int cid,@PathVariable int pid, @PathVariable int mid, @PathVariable int q,HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("user"))
		   {
			   service.addCart(cid,pid,mid, q);
			   logger.info("Medicine added To Customer Cart");
			   return new ResponseEntity<>(service.addCart(cid,pid,mid, q),HttpStatus.OK);
		   }
		   logger.error("Customer Details Are Not Valid");
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
	   }
	   
	   @GetMapping("/viewCart/{cid}")
	   public  ResponseEntity<HashMap<Medicine,Integer>> viewCart( @PathVariable int cid,HttpSession session ) {
		   
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("user"))
		   {
			   try {
				service.viewCart(cid);
				return new ResponseEntity<>(service.viewCart(cid),HttpStatus.OK);
			} catch (CartNotFoundException e) {

				System.out.println(e);
			}
			   
		   }
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
	   }
	   
	   @PostMapping("/checkout/{cid}")
	   public  ResponseEntity<Order> checkout(@PathVariable int cid,HttpSession session) {
		   Customer c=(Customer) session.getAttribute("loggedUser");
		   if(c.getRole().equals("user"))
		   {
			   try {
				service.checkout(cid);
				logger.info("Customer Checked Out the Cart");
				return new ResponseEntity<>(service.checkout(cid),HttpStatus.OK);
			} catch (CartNotFoundException e) {

			System.out.println(e);
			}
			   
		   }
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		   
		
	   }
	   @PostMapping("/updateCustomer")
	   public Customer updateCustomer(@RequestBody Customer c) {
		 return service.updateCustomer(c);
	   }
	   
	   @PostMapping("/deleteCustomer/{cid}")
	   public Customer deleteCustomer(@PathVariable int cid) {
		 return service.deleteCustomer(cid);
	   }
	   
	   @PostMapping("/signUp")
	   public Customer signUp(@RequestBody Customer c) {
	       
	       return service.signUp(c);
	   }
		
}
