package com.dor.coupons.logic;

import java.util.List;

import com.dor.coupons.beans.Customer;
import com.dor.coupons.dao.CustomersDao;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.ValidateUtils;

public class CustomersController {

	private CustomersDao customersDao = new CustomersDao();

	// ----- constructors ------

	public CustomersController() {
	}

	// ----- function ------

	public long createCustomer(Customer customer) throws ApplicationException {
		UsersController usersController = new UsersController();
		if(customer.getUser().getUserLoginDetails().getType() != ClientType.CUSTOMER) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
		if ((!ValidateUtils.isNameValid(customer.getFirstName()))
				|| (!ValidateUtils.isNameValid(customer.getLastName()))) {
			throw new ApplicationException(ErrorTypes.INVALID_NAME, ErrorTypes.INVALID_NAME.getGeneralErrorMessage());
		}
		long userId = usersController.createUser(customer.getUser());
		customer.setUserId(userId);
		return customersDao.createCustomer(customer);
	}

	public Customer getCustomer(long customerId) throws ApplicationException {
		validateCustomerExistsById(customerId);
		return customersDao.getCustomer(customerId);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		return customersDao.getAllCustomers();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		validateCustomerExistsById(customer.getUser().getUserId());
		if ((!ValidateUtils.isNameValid(customer.getFirstName()))
				|| (!ValidateUtils.isNameValid(customer.getLastName()))) {
			throw new ApplicationException(ErrorTypes.INVALID_NAME, ErrorTypes.INVALID_NAME.getGeneralErrorMessage());
		}
		customersDao.updateCustomer(customer);
	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		UsersController usersController = new UsersController();
		PurchasesController purchasesController = new PurchasesController();
		validateCustomerExistsById(customerId);
		purchasesController.deletePurchasesByCustomerId(customerId);
		customersDao.deleteCustomer(customerId);
		usersController.deleteUser(customerId);
	}
		
	public void validateCustomerExistsById(long customerId) throws ApplicationException {
		if (!customersDao.isCustomerExistsById(customerId)) {
			throw new ApplicationException(ErrorTypes.DATA_NOT_EXIST, ErrorTypes.DATA_NOT_EXIST.getGeneralErrorMessage());
		}
	}
	
}
