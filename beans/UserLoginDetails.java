package com.dor.coupons.beans;

import com.dor.coupons.enums.ClientType;

public class UserLoginDetails {
	
	private String password;
	private String email;
	private ClientType type;

	
	//----- get+set ------
	
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public ClientType getType() {
		return type;
	}
	
	
	public void setType(ClientType type) {
		this.type = type;
	}
	

	
	//----- constructors ------

	public UserLoginDetails(String password, String email, ClientType type) {
		super();
		this.password = password;
		this.email = email;
		this.type = type;
	}

	public UserLoginDetails() {
		super();
	}
	
	//----- function ------
	
	@Override
	
	public String toString() {
		return "UserLoginDetails [password:" + getPassword() + ", email:" + getEmail() + ", type:" + getType() + "]";
	}



}
