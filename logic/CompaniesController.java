package com.dor.coupons.logic;

import java.util.List;

import com.dor.coupons.beans.Company;
import com.dor.coupons.dao.CompaniesDao;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.ValidateUtils;

public class CompaniesController {

	private CompaniesDao companiesDao = new CompaniesDao();
	
	// ----- constructors ------

	public CompaniesController() {
	}

	// ----- function ------
	
	public long createCompany(Company company) throws ApplicationException {
		if ((companiesDao.isCompanyExistsByName(company.getCompanyName()))
				|| (companiesDao.isCompanyExistsByEmail(company.getCompanyEmail()))) {
			throw new ApplicationException(ErrorTypes.DATA_EXIST, ErrorTypes.DATA_EXIST.getGeneralErrorMessage());
		}
		if (!ValidateUtils.isEmailValid(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorTypes.INVALID_EMAIL, ErrorTypes.INVALID_EMAIL.getGeneralErrorMessage());
		}
		validateCompanyName(company.getCompanyName());
		return companiesDao.createCompany(company);
	}

	public Company getCompany(long companyId) throws ApplicationException {
		validateCompanyExistsById(companyId);
		return companiesDao.getCompany(companyId);
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		return companiesDao.getAllCompanies();
	}

	public void updateCompany(Company company) throws ApplicationException {
		validateCompanyExistsById(company.getCompanyId());
		if (!ValidateUtils.isEmailValid(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorTypes.INVALID_EMAIL, ErrorTypes.INVALID_EMAIL.getGeneralErrorMessage());
		}
		if (companiesDao.isCompanyExistsByEmail(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorTypes.DATA_EXIST, ErrorTypes.DATA_EXIST.getGeneralErrorMessage());
		}
		if (!company.getCompanyName().equals(companiesDao.getCompany(company.getCompanyId()).getCompanyName())) {
			throw new ApplicationException(ErrorTypes.FIXED_VALUE, ErrorTypes.FIXED_VALUE.getGeneralErrorMessage());
		}
		companiesDao.updateCompany(company);
	}

	public void deleteCompany(long companyId) throws ApplicationException {
		PurchasesController purchasesController = new PurchasesController();
		CouponsController couponsController = new CouponsController();
		UsersController usersController = new UsersController();
		validateCompanyExistsById(companyId);
		purchasesController.deletePurchasesByCompanyId(companyId);
		couponsController.deleteCouponsByCompanyId(companyId);
		usersController.deleteUsersByCompanyId(companyId);
		companiesDao.deleteCompany(companyId);
	}

	public void validateCompanyExistsById(long companyId) throws ApplicationException {
		if (!companiesDao.isCompanyExistsById(companyId)) {
			throw new ApplicationException(ErrorTypes.DATA_NOT_EXIST,
					ErrorTypes.DATA_NOT_EXIST.getGeneralErrorMessage());
		}
	}

	private void validateCompanyName(String companyName) throws ApplicationException {
		if (companyName.length() < 2) {
			throw new ApplicationException(ErrorTypes.SHORT_VALUE, ErrorTypes.SHORT_VALUE.getGeneralErrorMessage());
		}
	}
}
