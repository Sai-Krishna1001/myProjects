package com.pack.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;

import com.pack.service.PharmaCompanyServiceImpl;

@RestController
@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)
public class PharmaCompanyController {

	@Autowired
	PharmaCompanyServiceImpl service;
	
	static Logger logger=Logger.getLogger(PharmaCompanyController.class.getName());
	
	 @PostMapping("/logoutPharma")
	   public boolean logout(HttpSession session) {
	       session.invalidate();
	       return true;
	   }
	
	 @PostMapping("/loginPharmaCompany/{username}/{password}")
	   public ResponseEntity<PharmaCompany> validatePharmaCompanyLogin(@PathVariable String username,@PathVariable String password,HttpSession session) {
		   PharmaCompany pc = service.validatePharmaCompanyLogin(username, password);
	       if(pc != null) {
	           session.setAttribute("loggedUser", pc);
	           logger.info("PharmaCompany logged In");
	           return new ResponseEntity<>(pc,HttpStatus.OK);
	       }
	       logger.error("PharmaCompany details Not Valid");
	       return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	   }
	 

	   @PostMapping("/signUpPharma")
	   public PharmaCompany signUpPharma(@RequestBody PharmaCompany p) {
		 return service.signUpPharma(p);
	   }
	   
	   @PostMapping("/addInventory/{pid}")
	   public ResponseEntity<Medicine> addInventory(@RequestBody Medicine m,@PathVariable int pid,HttpSession session ) {
		   PharmaCompany p=(PharmaCompany) session.getAttribute("loggedUser");
		   if(p.getRole().equals("pharma"))
		   {
			   service.addInventory(m,pid); 
			   return new ResponseEntity<>(service.addInventory(m,pid),HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);  
	   }

}
