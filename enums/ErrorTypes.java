package com.dor.coupons.enums;

public enum ErrorTypes {
	
	GENERAL_ERROR(" General error"),
	CREATE_ERROR(" Data creation failed"),
	READ_ERROR(" Data reading failed"),
	UPDATE_ERROR(" Failed to update data"),
	DELETE_ERROR(" Data deletion failed"),
	LOGIN_FAILED(" The login has failed"),
	FIXED_VALUE(" This value can not be changed"),
	DATA_EXIST(" The data entered already exist"),
	DATA_NOT_EXIST(" The entered data is not exist"),
	INVALID_EMAIL(" The email entered is incorrect"),
	INVALID_PASSWORD(" The password entered is incorrect, a password must begin with a capital letter, a small letter followed by between 4-18 characters (letters or numbers)"),
	INVALID_NAME(" The name must begin with a capital letter and can contain between 2 and 20 letters"),
	ILLEGAL_OPERATION(" The operation failed"),
	INVALID_ENTRY(" Invalid value"),
	SHORT_VALUE(" Value is too short"),
	OUT_OF_STOCK(" This coupon is out of stock");
	
	private String generalErrorMessage;

	//----- get ------
	
	public String getGeneralErrorMessage() {
		return generalErrorMessage;
	}

	//----- constructor ------
	
	private ErrorTypes(String errorMessage) {
		this.generalErrorMessage = errorMessage;
	}

}
