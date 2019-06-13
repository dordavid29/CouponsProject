package com.dor.coupons.beans;

import java.util.Date;

import com.dor.coupons.enums.Category;

public class Coupon {
	
	private long id;
	private long companyId;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String img;
	
	//----- get+set ------
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	
	//----- constructors ------
	
	public Coupon(long id, long companyId, Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String img) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}
	
	public Coupon(long companyId, Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String img) {
		super();
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}
	
	public Coupon() {
		super();	
	}
	
	//----- function ------
	
	@Override
	public String toString() {
		return "Coupon [id=" + getId() + ", companyId=" + getCompanyId() + ", category=" + getCategory() + ", title=" + getTitle()
				+ ", description=" + getDescription()+ ", start date=" + getStartDate() + ", end date=" + getEndDate() + ", amount="
				+ getAmount() + ", price=" + getPrice() + ", img=" + getImg() + "]";
	}

}
