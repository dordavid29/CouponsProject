package com.dor.coupons.test;

import com.dor.coupons.beans.Purchase;
import com.dor.coupons.dao.PurchasesDao;
import com.dor.coupons.logic.PurchasesController;

public class TestPurchase {

	public static void main(String[] args) throws Exception {
		try {
//		PurchasesDao purdao = new PurchasesDao();
		Purchase pur = new Purchase(23, 30, 1);
//		purdao.createPurchase(pur);
//		System.out.println(purdao.getAllCompanies());
//		purdao.updatePurchase(pur);
//		purdao.deletePurchase(2);
		PurchasesController purchasesController = new PurchasesController();
//		purchasesController.createPurchase(pur);
//		System.out.println(purchasesController.getPurchase(1));
//		System.out.println(purchasesController.getAllPurchases());
//		purchasesController.deletePurchase(2);
//		purchasesController.deletePurchasesByCompanyId(100);
		purchasesController.deletePurchasesByCouponId(24);
//		purchasesController.deletePurchasesByCustomerId(22);
//		purchasesController.deleteExpiredPurchases();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
