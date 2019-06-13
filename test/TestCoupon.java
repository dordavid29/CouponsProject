package com.dor.coupons.test;

import java.util.Date;

import com.dor.coupons.beans.Company;
import com.dor.coupons.beans.Coupon;
import com.dor.coupons.dao.CompaniesDao;
import com.dor.coupons.dao.CouponsDao;
import com.dor.coupons.enums.Category;
import com.dor.coupons.logic.CouponsController;



public class TestCoupon {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Date currentDate = new Date();
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setYear(100);
		endDate.setYear(130);
//		System.out.println(currentDate);
//		System.out.println(startDate + ", " + endDate);
				
		Coupon couponTest = new Coupon(5, Category.FOOTBALL_GAMES, "MACCABI HAIFA","good team", startDate, endDate, 60, 20,"www.MACCABI-HAIFA.com");
		CouponsController couponsController = new CouponsController();
//		System.out.println(couponTest);
		couponsController.createCoupon(couponTest);
//		System.out.println(couponsController.getCompanyCoupons(10));
//		System.out.println(couponsController.getCoupon(19));
//		System.out.println(couponsController.getCustomerCoupons(23));
//		System.out.println(couponsController.getAllCoupons());
//		System.out.println(couponsController.getAllCouponsByCategory(Category.FOOTBALL_GAMES));
//		System.out.println(couponsController.getAllCouponsByMaxPrice(50));
//		System.out.println(couponsController.getCustomerCouponsByCategory(22, Category.COFFEE_SHOPS));
//		System.out.println(couponsController.getCompanyCouponsByCategory(8, Category.HOTELS));
//		System.out.println(couponsController.getCompanyCouponsByMaxPrice(8, 301));
//		System.out.println(couponsController.getCustomerCouponsByMaxPrice(22, 70));
//		couponsController.updateCoupon(couponTest);

//		couponsController.deleteCoupon(1);
//		couponsController.deleteCouponsByCompanyId(8l);
//		couponsController.deleteAllExpiredCoupons();
	
	}

}
