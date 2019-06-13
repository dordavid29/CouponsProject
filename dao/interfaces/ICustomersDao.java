package com.dor.coupons.dao.interfaces;

import java.util.List;

import com.dor.coupons.beans.Customer;
import com.dor.coupons.exception.ApplicationException;

public interface ICustomersDao {

	long createCustomer(Customer customer) throws ApplicationException;
	Customer getCustomer(long customerId) throws ApplicationException;
	List<Customer> getAllCustomers() throws ApplicationException;
	void updateCustomer(Customer customer) throws ApplicationException;
	void deleteCustomer(long customerId) throws ApplicationException;
	boolean isCustomerExistsById(long customerId) throws ApplicationException;

	
}
