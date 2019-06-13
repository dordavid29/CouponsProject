package com.dor.coupons.dao.interfaces;

import java.util.List;

import com.dor.coupons.beans.Company;
import com.dor.coupons.exception.ApplicationException;

public interface ICompaniesDao {

	long createCompany(Company company) throws ApplicationException;
	Company getCompany(long companyId) throws ApplicationException;
	List<Company> getAllCompanies() throws ApplicationException;
	void updateCompany(Company company) throws ApplicationException;
	void deleteCompany(long companyId) throws ApplicationException;
	boolean isCompanyExistsById(long companyId) throws ApplicationException;
	boolean isCompanyExistsByName(String companyName) throws ApplicationException;
	boolean isCompanyExistsByEmail(String companyEmail) throws ApplicationException;

}
