package com.dor.coupons.test;

import com.dor.coupons.beans.User;
import com.dor.coupons.beans.UserLoginDetails;
import com.dor.coupons.dao.UsersDao;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.logic.UsersController;;

public class TestUser {

	public static void main(String[] args) throws ApplicationException {
		try {
		UsersController usersController = new UsersController();
		User userTest1 = new User(10l, new UserLoginDetails("SCC506464", "jiji@gmail.com", ClientType.COMPANY));
//		usersController.createUser(userTest1);
//		System.out.println(usersController.getUser(70));
//		System.out.println(usersController.getAllUsers());
//		usersController.updateUser(userTest1);
//		usersController.deleteUser(56);
//		usersController.deleteUsersByCompanyId(5);
//		System.out.println(usersController.login("858@gmail.com", "Dd4580"));
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
