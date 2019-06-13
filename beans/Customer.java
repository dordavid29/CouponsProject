package com.dor.coupons.beans;

public class Customer {

	private User user;
	private String firstName;
	private String lastName;

	// ----- get+set ------

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param userId Receive user id
	 */
	public void setUserId(long userId) {
		this.getUser().setUserId(userId);
	}

	// ----- constructors ------

	public Customer(User user, String firstName, String lastName) {
		super();
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer() {
		super();
	}

	// ----- function ------

	@Override
	public String toString() {
		return "Customer [customerId=" + user.getUserId() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + "]";
	}

}
