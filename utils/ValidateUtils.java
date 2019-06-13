package com.dor.coupons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^[A-Z]+[a-z]+[a-z0-9]{4,18}$",
			Pattern.CASE_INSENSITIVE);
	
	private static final Pattern VALID_NAME_REGEX = Pattern
			.compile("^[A-Z]+[a-z]{1,19}$", Pattern.CASE_INSENSITIVE);
	
	public static boolean isEmailValid(String email) {

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return (matcher.find() ? true : false);
	}

	public static boolean isPasswordValid(String password) {

		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return (matcher.find() ? true : false);
	}
	
	public static boolean isNameValid(String name) {

		Matcher matcher = VALID_NAME_REGEX.matcher(name);
		return (matcher.find() ? true : false);
	}
}
