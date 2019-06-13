package com.dor.coupons.test;

import com.dor.coupons.beans.Customer;
import com.dor.coupons.beans.User;
import com.dor.coupons.beans.UserLoginDetails;
import com.dor.coupons.dao.CustomersDao;
import com.dor.coupons.dao.UsersDao;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.logic.CustomersController;

public class TestCustomer {

	public static void main(String[] args) throws Exception {
		CustomersDao cusdao = new CustomersDao();
		User userTest1 = new User(51, null, new UserLoginDetails("Ff12993", "offdgdgicfe@test4.com", ClientType.CUSTOMER));
		CustomersController customersController = new CustomersController();
		Customer cus = new Customer(userTest1, "Nana", "Banana");
//		customersController.createCustomer(cus);
//		System.out.println(customersController.getCustomer(30));
//		System.out.println(customersController.getAllCustomers());
//		System.out.println(cus.getUser().getUserId());
//		customersController.updateCustomer(cus);
		customersController.deleteCustomer(71);
	}

}
