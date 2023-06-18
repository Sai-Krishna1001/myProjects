package com.virtusa.ars.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.virtusa.ars.dto.BankAccountDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DBUtil;

public class BankAccountDAO implements DAO<BankAccountDTO> {
	
    private static final String SELECT_SQL = "SELECT user_id, account_number, bank_name, balance FROM bank_account WHERE user_id = ?";
    private static final String INSERT_SQL = "INSERT INTO bank_account (user_id, account_number, bank_name, balance) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE bank_account SET balance = ? WHERE user_id = ? AND account_number = ?";
    private static final String DELETE_SQL = "DELETE FROM bank_account WHERE user_id = ? AND account_number = ?";
    private static final String FIND_ALL_SQL = "SELECT user_id, account_number, bank_name, balance FROM bank_account";
    @Override
    public void save(BankAccountDTO bankAccount) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(INSERT_SQL)) {
            statement.setString(1, bankAccount.getUserId());
            statement.setInt(2, bankAccount.getAccountNumber());
            statement.setString(3, bankAccount.getBankName());
            statement.setBigDecimal(4, bankAccount.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BookingException("Failed to save bank account.", e);
        }
    }
    @Override
    public BankAccountDTO findById(String id) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(SELECT_SQL)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapResultSetToBankAccount(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new BookingException("Failed to find bank account by ID.", e);
        }
    }
    @Override
    public List<BankAccountDTO> findAll() throws BookingException {
        List<BankAccountDTO> bankAccounts = new ArrayList<>();
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(FIND_ALL_SQL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                BankAccountDTO bankAccount = mapResultSetToBankAccount(rs);
                bankAccounts.add(bankAccount);
            }
            return bankAccounts;
        } catch (SQLException e) {
            throw new BookingException("Failed to find all bank accounts.", e);
        }
    }
    @Override
    public void update(BankAccountDTO bankAccount) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(UPDATE_SQL)) {
            statement.setBigDecimal(1, bankAccount.getBalance());
            statement.setString(2, bankAccount.getUserId());
            statement.setInt(3, bankAccount.getAccountNumber());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new BookingException("Failed to update bank account.");
            }
        } catch (SQLException e) {
            throw new BookingException("Failed to update bank account.", e);
        }
    }
    @Override
    public void delete(BankAccountDTO bankAccount) throws BookingException {
        try (PreparedStatement statement = DBUtil.getConnection().prepareStatement(DELETE_SQL)) {
            statement.setString(1, bankAccount.getUserId());
            statement.setInt(2, bankAccount.getAccountNumber());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new BookingException("Failed to delete bank account.");
            }
        } catch (SQLException e) {
        	throw new BookingException("Failed to delete bank account.", e);
        }
    }
    
    private BankAccountDTO mapResultSetToBankAccount(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        int accountNumber = rs.getInt("account_number");
        String bankName = rs.getString("bank_name");
        BigDecimal balance = rs.getBigDecimal("balance");
        return new BankAccountDTO(userId, accountNumber, bankName, balance);
    }
}