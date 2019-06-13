package com.dor.coupons.dao.interfaces;

import java.util.List;

import com.dor.coupons.beans.Purchase;
import com.dor.coupons.exception.ApplicationException;

public interface IPurchasesDao {

	long createPurchase(Purchase purchase) throws ApplicationException;
	Purchase getPurchase(long purchaseId) throws ApplicationException;
	List<Purchase> getAllPurchases() throws ApplicationException;
	void deletePurchase(long purchaseId) throws ApplicationException;
	void deletePurchasesByCustomerId(long customerId) throws ApplicationException;
	void deletePurchasesByCouponId(long couponId) throws ApplicationException;
	void deletePurchasesByCompanyId(long companyId) throws ApplicationException;
	void deleteExpiredPurchases() throws ApplicationException;
	boolean isPurchaseExistsById(long purchaseId) throws ApplicationException;

}
