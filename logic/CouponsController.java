package com.dor.coupons.logic;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dor.coupons.beans.Coupon;
import com.dor.coupons.dao.CouponsDao;
import com.dor.coupons.enums.Category;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;

@Component
public class CouponsController {
	
	private CouponsDao couponsDao = new CouponsDao();
	
	// ----- constructors ------
	
	public CouponsController() {
	}

	// ----- function ------

	public long createCoupon(Coupon coupon) throws ApplicationException {
		if(couponsDao.isCouponExistsByTitle(coupon.getCompanyId(), coupon.getTitle())){
			throw new ApplicationException(ErrorTypes.DATA_EXIST, ErrorTypes.DATA_EXIST.getGeneralErrorMessage());
		}
		validateCouponTitle(coupon.getTitle());
		validateCouponDescription(coupon.getDescription());
		validateCouponDate(coupon.getStartDate(), coupon.getEndDate());
		validateCouponAmount(coupon.getAmount());
		validateCouponPrice(coupon.getPrice());
		
		return couponsDao.createCoupon(coupon);
	}
	
	public Coupon getCoupon(long couponId) throws ApplicationException {
		validateCouponExistsById(couponId);
		return couponsDao.getCoupon(couponId);
	}
	
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponsDao.getAllCoupons();
	}
	
	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {
		return couponsDao.getAllCouponsByCategory(category);
	}
	
	public List<Coupon> getAllCouponsByMaxPrice(double maxPrice) throws ApplicationException {
		return couponsDao.getAllCouponsByMaxPrice(maxPrice);
	}
	
	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {
		CustomersController customersController = new CustomersController();
		customersController.validateCustomerExistsById(customerId);
		return couponsDao.getCustomerCoupons(customerId);
	}
	
	public List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		companiesController.validateCompanyExistsById(companyId);
		return couponsDao.getCompanyCoupons(companyId);
	}
	
	public List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException {
		CustomersController customersController = new CustomersController();
		customersController.validateCustomerExistsById(customerId);
		return couponsDao.getCustomerCouponsByCategory(customerId, category);
	}
	
	public List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		companiesController.validateCompanyExistsById(companyId);
		return couponsDao.getCompanyCouponsByCategory(companyId, category);
	}
	
	public List<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws ApplicationException {
		CustomersController customersController = new CustomersController();
		customersController.validateCustomerExistsById(customerId);
		return couponsDao.getCustomerCouponsByMaxPrice(customerId, maxPrice);
	}
	
	public List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		companiesController.validateCompanyExistsById(companyId);
		return couponsDao.getCompanyCouponsByMaxPrice(companyId, maxPrice);
	}
	
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		validateCouponExistsById(coupon.getId());
		Coupon couponOriginal = couponsDao.getCoupon(coupon.getId());
		if (couponOriginal.getCompanyId() != coupon.getCompanyId()) {
			throw new ApplicationException(ErrorTypes.ILLEGAL_OPERATION, ErrorTypes.ILLEGAL_OPERATION.getGeneralErrorMessage());
		}
		validateCouponTitle(coupon.getTitle());
		validateCouponDescription(coupon.getDescription());
		validateCouponDate(coupon.getStartDate(), coupon.getEndDate());
		validateCouponAmount(coupon.getAmount());
		validateCouponPrice(coupon.getPrice());
		
		couponsDao.updateCoupon(coupon);
	}
	
	public void deleteCoupon(long couponId) throws ApplicationException {
		PurchasesController purchasesController = new PurchasesController();
		validateCouponExistsById(couponId);
		purchasesController.deletePurchasesByCouponId(couponId);
		couponsDao.deleteCoupon(couponId);
	}
	
	public void deleteCouponsByCompanyId(long companyId) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		PurchasesController purchasesController = new PurchasesController();
		companiesController.validateCompanyExistsById(companyId);
		purchasesController.deletePurchasesByCompanyId(companyId);
		couponsDao.deleteCouponsByCompanyId(companyId);
	}
	
	public void deleteAllExpiredCoupons() throws ApplicationException {
		PurchasesController purchasesController = new PurchasesController();
		purchasesController.deleteExpiredPurchases();
		couponsDao.deleteAllExpiredCoupons();
	}
	
	public void validateCouponExistsById(long couponId) throws ApplicationException {
		if (!couponsDao.isCouponExistsById(couponId)) {
			throw new ApplicationException(ErrorTypes.DATA_NOT_EXIST, ErrorTypes.DATA_NOT_EXIST.getGeneralErrorMessage());
		}
	}
	
	private void validateCouponTitle(String title) throws ApplicationException {
		if (title.length() < 3) {
			throw new ApplicationException(ErrorTypes.SHORT_VALUE, ErrorTypes.SHORT_VALUE.getGeneralErrorMessage());
		}
	}
	
	private void validateCouponDescription(String description) throws ApplicationException {
		if (description.length() < 5) {
			throw new ApplicationException(ErrorTypes.SHORT_VALUE, ErrorTypes.SHORT_VALUE.getGeneralErrorMessage());
		}
	}
	
	private void validateCouponDate(Date startDate, Date endDate) throws ApplicationException {
		Date currentDate = new Date();
		if (!endDate.after(startDate) && !endDate.after(currentDate)) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
	}
	
	private void validateCouponAmount(int amount) throws ApplicationException {
		if (amount < 1) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
	}
	
	private void validateCouponPrice(double price) throws ApplicationException {
		if (price < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
	}
	
}
