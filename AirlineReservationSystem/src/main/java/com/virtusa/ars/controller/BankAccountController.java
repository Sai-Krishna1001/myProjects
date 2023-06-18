package com.virtusa.ars.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtusa.ars.dto.BankAccountDTO;
import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.service.BankAccountService;

import com.virtusa.ars.util.ServiceFactory;

public class BankAccountController {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);
	private static BankAccountService bankAccountService = ServiceFactory.getInstance().getBankAccountService();
	
	public void addBankAccount(UserDTO user) {
	    try {
	        LOGGER.info("Enter bank account details:");
	        LOGGER.info("Enter account number:");
	        int accountNumber = Integer.parseInt(reader.readLine());
	        LOGGER.info("Enter bank name:");
	        String bankName = reader.readLine();
	        BigDecimal balance = new BigDecimal("50000.00");
	        BankAccountDTO bankAccountDTO = new BankAccountDTO(user.getUserId(), accountNumber, bankName, balance);
	        bankAccountService.add(bankAccountDTO);
	        LOGGER.info("Bank account added successfully!");
	    } catch (IOException | BookingException e) {
	        LOGGER.info("Error adding bank account: {}", e.getMessage());
	    }
	}

}
