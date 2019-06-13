package com.dor.coupons.beans;

public class User {

	private long userId;
	private Long companyId;
	private UserLoginDetails userLoginDetails;
	
	//----- get+set ------
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public UserLoginDetails getUserLoginDetails() {
		return userLoginDetails;
	}
	public void setUserLoginDetails(UserLoginDetails userLoginDetails) {
		this.userLoginDetails = userLoginDetails;
	}
	
	//----- constructors ------
	
	public User(long userId, Long companyId, UserLoginDetails userLoginDetails) {
		super();
		this.userId = userId;
		this.companyId = companyId;
		this.userLoginDetails = userLoginDetails;
	}
	
	public User(Long companyId, UserLoginDetails userLoginDetails) {
		super();
		this.companyId = companyId;
		this.userLoginDetails = userLoginDetails;
	}
	
	public User() {
		super();	
	}
	
	//----- function ------
	
	@Override
	public String toString() {
		return "User [userId=" + getUserId() + ", companyId=" + getCompanyId() + ", userLoginDetails=" + getUserLoginDetails() + "]";
	}
		
}
