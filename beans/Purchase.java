package com.dor.coupons.beans;

public class Purchase {
	
    private long purchaseId;
    private long customerId;
    private long couponId;
    private int purchaseAmount;
	
	//----- get+set ------
    
    public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public int getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	
	//----- constructors ------
	
	public Purchase(long purchaseId, long customerId, long couponId, int purchaseAmount) {
		super();
		this.purchaseId = purchaseId;
		this.customerId = customerId;
		this.couponId = couponId;
		this.purchaseAmount = purchaseAmount;
	}

	public Purchase(long customerId, long couponId, int purchaseAmount) {
		super();
		this.customerId = customerId;
		this.couponId = couponId;
		this.purchaseAmount = purchaseAmount;
	}
	
	public Purchase() {
		super();
	}
	
	//----- function ------	
	
	@Override
	public String toString() {
		return "Purchase [purchaseId=" + getPurchaseId() + ", customerId=" + getCustomerId() + ", couponId=" + getCouponId()
				+ ", purchaseAmount=" + getPurchaseAmount() + "]";
	}
	  
}
