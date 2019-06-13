package com.dor.coupons.utils;

public class DateUtils {

	public static java.sql.Date converDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}
