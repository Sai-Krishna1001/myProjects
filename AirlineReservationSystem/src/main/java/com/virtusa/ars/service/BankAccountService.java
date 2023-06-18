package com.virtusa.ars.service;
import java.util.List;

import com.virtusa.ars.dao.BankAccountDAO;
import com.virtusa.ars.dto.BankAccountDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DAOFactory;

public class BankAccountService implements Service<BankAccountDTO> {
	
    private BankAccountDAO bankAccountDAO = DAOFactory.getInstance().getBankAccountDAO();
    
    @Override
    public void add(BankAccountDTO bankAccountDTO) throws BookingException {
        try {
            bankAccountDAO.save(bankAccountDTO);
        } catch (Exception e) {
            throw new BookingException("Failed to add bank account", e);
        }
    }

    @Override
    public void update(BankAccountDTO bankAccountDTO) throws BookingException {
        try {
            bankAccountDAO.update(bankAccountDTO);
        } catch (Exception e) {
            throw new BookingException("Failed to update bank account", e);
        }
    }

    @Override
    public void delete(BankAccountDTO bankAccountDTO) throws BookingException {
        try {
            bankAccountDAO.delete(bankAccountDTO);
        } catch (Exception e) {
            throw new BookingException("Failed to delete bank account", e);
        }
    }

    @Override
    public BankAccountDTO getById(String id) throws BookingException {
        try {
            return bankAccountDAO.findById(id);
        } catch (Exception e) {
            throw new BookingException("Failed to get bank account by id", e);
        }
    }

    @Override
    public List<BankAccountDTO> getAll() throws BookingException {
        try {
            return bankAccountDAO.findAll();
        } catch (Exception e) {
            throw new BookingException("Failed to get all bank accounts", e);
        }
    }
}
