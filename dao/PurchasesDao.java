package com.dor.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dor.coupons.beans.Purchase;
import com.dor.coupons.dao.interfaces.IPurchasesDao;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.JdbcUtils;


public class PurchasesDao implements IPurchasesDao {

	public long createPurchase(Purchase purchase) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO purchases (customer_id, coupon_id, purchase_amount) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, purchase.getCustomerId());
			preparedStatement.setLong(2, purchase.getCouponId());
			preparedStatement.setInt(3, purchase.getPurchaseAmount());

			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				System.out.println("Purchase id "+ id + " created successfully");
				return id;
			} else {
				throw new ApplicationException(ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
		} finally {
			System.out.println("created your " + purchase.toString());
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Purchase getPurchase(long purchaseId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * from purchases WHERE purchase_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchaseId);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractPurchaseFromResultSet(result);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Purchase> getAllPurchases() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Purchase> purchasesList = new ArrayList<Purchase>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM purchases";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Purchase validPurchase = extractPurchaseFromResultSet(result);
				purchasesList.add(validPurchase);
			}
			return purchasesList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void deletePurchase(long purchaseId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchaseId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deletePurchasesByCustomerId(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deletePurchasesByCouponId(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deletePurchasesByCompanyId(long companyId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id IN (SELECT coupon_id FROM coupons WHERE company_id = ?);";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deleteExpiredPurchases() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id IN (SELECT coupon_id FROM coupons WHERE coupon_end_date < curdate())";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public boolean isPurchaseExistsById(long purchaseId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM purchases WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchaseId);

			result = preparedStatement.executeQuery();

			if ((result.next())) {
				return true;
			} 
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, ErrorTypes.GENERAL_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	private Purchase extractPurchaseFromResultSet(ResultSet result) throws SQLException {

		Purchase purchase = new Purchase();

		purchase.setPurchaseId(result.getLong("purchase_id"));
		purchase.setCustomerId(result.getLong("customer_id"));
		purchase.setCouponId(result.getLong("coupon_id"));
		purchase.setPurchaseAmount(result.getInt("purchase_amount"));

		return purchase;

	}

}
