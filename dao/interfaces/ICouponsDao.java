
package com.dor.coupons.dao.interfaces;

import java.util.List;

import com.dor.coupons.beans.Coupon;
import com.dor.coupons.enums.Category;
import com.dor.coupons.exception.ApplicationException;


public interface ICouponsDao {

	long createCoupon(Coupon coupon) throws ApplicationException;
	Coupon getCoupon(long couponId) throws ApplicationException;
	List<Coupon> getAllCoupons() throws ApplicationException;
	List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException;
	List<Coupon> getAllCouponsByMaxPrice(double maxPrice) throws ApplicationException;
	List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException;
	List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException;
	List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException;
	List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException;
	List<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws ApplicationException;
	List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException;
	void updateCoupon(Coupon coupon) throws ApplicationException;
	void deleteCoupon(long couponId) throws ApplicationException;
	void deleteCouponsByCompanyId(long companyId) throws ApplicationException;
	void deleteAllExpiredCoupons() throws ApplicationException;
	boolean isCouponExistsById(long couponId) throws ApplicationException;
	boolean isCouponExistsByTitle(long companyId, String title) throws ApplicationException;
	
}
