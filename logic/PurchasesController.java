package com.dor.coupons.logic;

import java.util.List;

import com.dor.coupons.beans.Coupon;
import com.dor.coupons.beans.Purchase;
import com.dor.coupons.dao.PurchasesDao;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;

public class PurchasesController {

	private PurchasesDao purchasesDao = new PurchasesDao();
	
	// ----- constructors ------

	public PurchasesController() {
	}

	// ----- function ------

	public long createPurchase(Purchase purchase) throws ApplicationException {
		CouponsController couponsController = new CouponsController();
		couponsController.validateCouponExistsById(purchase.getCouponId());
		ReducingAmountOfCoupons(purchase);
		return purchasesDao.createPurchase(purchase);
	}

	public Purchase getPurchase(long purchaseId) throws ApplicationException {
		validatePurchaseExistsById(purchaseId);
		return purchasesDao.getPurchase(purchaseId);
	}

	public List<Purchase> getAllPurchases() throws ApplicationException {
		return purchasesDao.getAllPurchases();
	}

	public void deletePurchase(long purchaseId) throws ApplicationException {
		validatePurchaseExistsById(purchaseId);
		purchasesDao.deletePurchase(purchaseId);
	}

	public void deletePurchasesByCustomerId(long customerId) throws ApplicationException {
		CustomersController customersController = new CustomersController();
		customersController.validateCustomerExistsById(customerId);
		purchasesDao.deletePurchasesByCustomerId(customerId);
	}

	public void deletePurchasesByCouponId(long couponId) throws ApplicationException {
		CouponsController couponsController = new CouponsController();
		couponsController.validateCouponExistsById(couponId);
		purchasesDao.deletePurchasesByCouponId(couponId);
	}

	public void deletePurchasesByCompanyId(long companyId) throws ApplicationException {
		CompaniesController companiesController = new CompaniesController();
		companiesController.validateCompanyExistsById(companyId);
		purchasesDao.deletePurchasesByCompanyId(companyId);
	}

	public void deleteExpiredPurchases() throws ApplicationException {
		purchasesDao.deleteExpiredPurchases();
	}

	private void validatePurchaseExistsById(long purchaseId) throws ApplicationException {
		if (!purchasesDao.isPurchaseExistsById(purchaseId)) {
			throw new ApplicationException(ErrorTypes.DATA_NOT_EXIST, ErrorTypes.DATA_NOT_EXIST.getGeneralErrorMessage());
		}
	}

	private void ReducingAmountOfCoupons(Purchase purchase) throws ApplicationException {
		
		CouponsController couponsController = new CouponsController();
		if (purchase.getPurchaseAmount() < 1) {
			throw new ApplicationException(ErrorTypes.INVALID_ENTRY, ErrorTypes.INVALID_ENTRY.getGeneralErrorMessage());
		}
		if (purchasesDao.isPurchaseExistsById(purchase.getPurchaseId())) {
			throw new ApplicationException(ErrorTypes.DATA_EXIST, ErrorTypes.DATA_EXIST.getGeneralErrorMessage());
		}
		if (couponsController.getCoupon(purchase.getCouponId()).getAmount() - purchase.getPurchaseAmount() < 0) {
			throw new ApplicationException(ErrorTypes.OUT_OF_STOCK, ErrorTypes.OUT_OF_STOCK.getGeneralErrorMessage());
		}
		Coupon currentCoupon = couponsController.getCoupon(purchase.getCouponId());
		currentCoupon.setAmount(couponsController.getCoupon(purchase.getCouponId()).getAmount() - purchase.getPurchaseAmount());
		couponsController.updateCoupon(currentCoupon);
	}
}