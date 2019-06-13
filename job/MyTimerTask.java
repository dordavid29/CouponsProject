package com.dor.coupons.job;

import java.util.TimerTask;

import com.dor.coupons.logic.CouponsController;
import com.dor.coupons.logic.PurchasesController;

public class MyTimerTask extends TimerTask {

	private PurchasesController purchasesController = new PurchasesController();
	private CouponsController couponController = new CouponsController();

	public void run() {

		try {
			purchasesController.deleteExpiredPurchases();
			couponController.deleteAllExpiredCoupons();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
