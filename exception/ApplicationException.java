package com.dor.coupons.exception;

import com.dor.coupons.enums.ErrorTypes;

public class ApplicationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	ErrorTypes errorType;
	String errorMessage;
	
	//----- constructors ------
	
	public ApplicationException(ErrorTypes errorType, String errorMessage) {
		super(errorType.getGeneralErrorMessage() + errorMessage);
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}

	public ApplicationException(Exception e, ErrorTypes errorType, String errorMessage) {
		super(errorType.getGeneralErrorMessage() + errorMessage);
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}
	
	
}
