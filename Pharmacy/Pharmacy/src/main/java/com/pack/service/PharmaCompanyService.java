package com.pack.service;

import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;

public interface PharmaCompanyService {

	
	//PharmaCompany
		 public PharmaCompany validatePharmaCompanyLogin(String username, String password);
		 public PharmaCompany signUpPharma(PharmaCompany p);
		 public Medicine addInventory(Medicine m,int pid);
		
}
