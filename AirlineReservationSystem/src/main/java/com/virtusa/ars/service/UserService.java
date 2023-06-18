package com.virtusa.ars.service;
import java.util.List;
import java.util.stream.Collectors;
import com.virtusa.ars.dao.UserDAO;
import com.virtusa.ars.dto.UserDTO;
import com.virtusa.ars.exception.BookingException;
import com.virtusa.ars.util.DAOFactory;

public class UserService implements Service<UserDTO> {
    
    private static final UserDAO userDao = DAOFactory.getInstance().getUserDAO();
    
    @Override
    public void add(UserDTO user) throws BookingException {
        userDao.save(user);
    }

    @Override
    public void update(UserDTO user) throws BookingException {
        userDao.update(user);
    }

    @Override
    public void delete(UserDTO user) throws BookingException {
        userDao.delete(user);
    }

    @Override
    public UserDTO getById(String id) throws BookingException {
        return userDao.findById(id);
    }

    @Override
    public List<UserDTO> getAll() throws BookingException {
        return userDao.findAll().stream()
                .sorted((u1, u2) -> u1.getUserName().compareTo(u2.getUserName()))
                .collect(Collectors.toList());
    }

	public UserDTO getUserByEmailAndPassword(String email, String password) throws BookingException {
		
		return userDao.getUserByEmailAndPassword(email, password);
	}

}
