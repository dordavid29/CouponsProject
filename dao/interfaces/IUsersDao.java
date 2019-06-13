package com.dor.coupons.dao.interfaces;

import java.util.List;

import com.dor.coupons.beans.User;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.exception.ApplicationException;


public interface IUsersDao {

	long createUser(User user) throws ApplicationException;
	User getUser(long userId) throws ApplicationException;
	User getUserByEmail(String email) throws ApplicationException;
	List<User> getAllUsers() throws ApplicationException;
	void updateUser(User user) throws ApplicationException;
	void deleteUser(long userId) throws ApplicationException;
	void deleteUsersByCompanyId(long companyId) throws ApplicationException;
	public boolean isUserExistsById(long userId) throws ApplicationException;
	public boolean isUserExistsByEmail(String email) throws ApplicationException;
	public ClientType login(String user, String password) throws ApplicationException;
	
}
