package com.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.dao.MedicureDao;
import com.pack.model.Medicine;
import com.pack.model.PharmaCompany;

@Service
public class PharmaCompanyServiceImpl implements PharmaCompanyService{
	
	@Autowired
	MedicureDao medicureDao;
	
	
	 public PharmaCompany validatePharmaCompanyLogin(String username, String password) {
			
			return medicureDao.validatePharmaCompanyLogin(username, password);
		}
	 
	 public PharmaCompany signUpPharma(PharmaCompany p) {
			
			return medicureDao.signUpPharma(p);
			
		}

		@Override
		public Medicine addInventory(Medicine m,int pid) {
			
			return medicureDao.addInventory(m,pid);
		}

}
