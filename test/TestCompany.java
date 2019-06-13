package com.dor.coupons.test;

import com.dor.coupons.beans.Company;
import com.dor.coupons.dao.CompaniesDao;
import com.dor.coupons.logic.CompaniesController;

public class TestCompany {

	public static void main(String[] args) throws Exception {

		CompaniesController CompaniesController = new CompaniesController();
		CompaniesDao cmpdao = new CompaniesDao();
		Company comp1 = new Company(12, "nana", "office@nana123.com");
//		cmpdao.createCompany(comp1);
//		System.out.println(cmpdao.getAllCompanies());
//		cmpdao.updateCompany(comp1);
//		cmpdao.deleteCompany(2);
		
//		CompaniesController.createCompany(comp1);
//		System.out.println(CompaniesController.getAllCompanies());
//		CompaniesController.updateCompany(comp1);
//		CompaniesController.deleteCompany(7);
	}
}