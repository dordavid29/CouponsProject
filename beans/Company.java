package com.dor.coupons.beans;

public class Company {

	private long companyId;
	private String companyName;
	private String companyEmail;

	// ----- get+set ------

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	// ----- constructors ------

	public Company(long companyId, String companyName, String companyEmail) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyEmail = companyEmail;
	}

	public Company(String companyName, String companyEmail) {
		super();
		this.companyName = companyName;
		this.companyEmail = companyEmail;
	}

	public Company() {
		super();
	}

	// ----- function ------

	@Override
	public String toString() {
		return "Company [companyName=" + getCompanyName() + ", companyId=" + getCompanyId() + ", companyEmail=" + getCompanyEmail()
				+ "]";
	}

}
