package com.dor.coupons.logic;

import java.util.List;

import com.dor.coupons.beans.User;
import com.dor.coupons.dao.CustomersDao;
import com.dor.coupons.dao.UsersDao;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.ValidateUtils;

public class UsersController {

	private UsersDao usersDao = new UsersDao();

	// ----- constructors ------

	public UsersController() {
	}

	// ----- function ------

	public long createUser(User user) throws ApplicationException {

		if (usersDao.isUserExistsByEmail(user.getUserLoginDetails().getEmail())) {
			throw new ApplicationException(ErrorTypes.DATA_EXIST, ErrorTypes.DATA_EXIST.getGeneralErrorMessage());
		}
		validateUser(user);
		return usersDao.createUser(user);
	}

	public User getUser(long userId) throws ApplicationException {
		validateUserExistsById(userId);
		return usersDao.getUser(userId);
	}

	public List<User> getAllUsers() throws ApplicationException {
		return usersDao.getAllUsers();
	}

	public void updateUser(User user) throws ApplicationException {

		validateUserExistsById(user.getUserId());
		validateUser(user);
		if (usersDao.getUser(user.getUserId()).getUserLoginDetails().getType() != user.getUserLoginDetails()
				.getType()) {
			throw new ApplicationException(ErrorTypes.UPDATE_ERROR, ErrorTypes.UPDATE_ERROR.getGeneralErrorMessage());
		}

		usersDao.updateUser(user);
	}

	public void deleteUser(long userId) throws ApplicationException {

		CustomersController customerController = new CustomersController();
		CustomersDao customersDao = new CustomersDao();
		validateUserExistsById(userId);
		if ((usersDao.getUser(userId).getUserLoginDetails().getType() == ClientType.CUSTOMER)
				&& (customersDao.getCustomer(userId) != null)) {
			customerController.deleteCustomer(userId);
			return;
		}
		if (usersDao.getUser(userId).getUserLoginDetails().getType() == ClientType.ADMINISTRATOR) {
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, ErrorTypes.GENERAL_ERROR.getGeneralErrorMessage());
		}
		usersDao.deleteUser(userId);
	}

	public void deleteUsersByCompanyId(long companyId) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		companiesController.validateCompanyExistsById(companyId);
		usersDao.deleteUsersByCompanyId(companyId);
	}

	public ClientType login(String userName, String password) throws ApplicationException {
		return usersDao.login(userName, password);
	}

	private void validateUserExistsById(long userId) throws ApplicationException {
		if (!usersDao.isUserExistsById(userId)) {
			throw new ApplicationException(ErrorTypes.DATA_NOT_EXIST,
					ErrorTypes.DATA_NOT_EXIST.getGeneralErrorMessage());
		}
	}

	private void validateUser(User user) throws ApplicationException {

		if (!ValidateUtils.isEmailValid(user.getUserLoginDetails().getEmail())) {
			throw new ApplicationException(ErrorTypes.INVALID_EMAIL, ErrorTypes.INVALID_EMAIL.getGeneralErrorMessage());
		}

		if (!ValidateUtils.isPasswordValid(user.getUserLoginDetails().getPassword())) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD,
					ErrorTypes.INVALID_PASSWORD.getGeneralErrorMessage());
		}

		if (user.getCompanyId() != null) {
			if (!user.getUserLoginDetails().getType().equals(ClientType.COMPANY)) {
				throw new ApplicationException(ErrorTypes.INVALID_ENTRY,
						ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
			}
			CompaniesController companiesController = new CompaniesController();
			companiesController.validateCompanyExistsById(user.getCompanyId());
		} else if (user.getUserLoginDetails().getType().equals(ClientType.COMPANY)) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
	}

}