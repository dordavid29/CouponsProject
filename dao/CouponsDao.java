package com.dor.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dor.coupons.beans.Coupon;
import com.dor.coupons.dao.interfaces.ICouponsDao;
import com.dor.coupons.enums.Category;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.DateUtils;
import com.dor.coupons.utils.JdbcUtils;

public class CouponsDao implements ICouponsDao {

	public long createCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();

			String sqlStatement = "INSERT INTO coupons (company_id, category, coupon_title, coupon_description, coupon_start_date, coupon_end_date, coupon_amount, coupon_price, coupon_image) VALUES (?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setString(2, coupon.getCategory().name());
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setDate(5, DateUtils.converDateToSqlDate(coupon.getStartDate()));
			preparedStatement.setDate(6, DateUtils.converDateToSqlDate(coupon.getEndDate()));
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImg());

			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				System.out.println("Coupon id " + id + " created successfully");
				coupon.setId(id);
				System.out.println("created your " + coupon.toString());
				return id;
			} else {
				throw new ApplicationException(ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Coupon getCoupon(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * from coupons WHERE coupon_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponId);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractCouponFromResultSet(result);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsList.add(validCoupon);
			}
			return couponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsByCategoryList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE category=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, category.name());
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsByCategoryList.add(validCoupon);
			}
			return couponsByCategoryList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public List<Coupon> getAllCouponsByMaxPrice(double maxPrice) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsByMaxPriceList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE coupon_price<?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setDouble(1, maxPrice);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsByMaxPriceList.add(validCoupon);
			}
			return couponsByMaxPriceList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> companyCouponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT c.* FROM coupons c "
					+ "INNER JOIN purchases p ON c.coupon_id = p.coupon_id "
					+ "WHERE p.customer_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				companyCouponsList.add(validCoupon);
			}
			return companyCouponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> companyCouponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE company_Id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				companyCouponsList.add(validCoupon);
			}
			return companyCouponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsByCategoryList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons c "
					+ "INNER JOIN purchases p ON c.coupon_id = p.coupon_id "
					+ "WHERE p.customer_id = ? AND c.category = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setString(2, category.name());
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsByCategoryList.add(validCoupon);
			}
			return couponsByCategoryList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsByCategoryList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE company_id=? AND category=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, category.name());
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsByCategoryList.add(validCoupon);
			}
			return couponsByCategoryList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public List<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws ApplicationException {

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet result = null;
			List<Coupon> couponsByMaxPriceList = new ArrayList<Coupon>();

			try {
				connection = JdbcUtils.getConnection();
				String sqlStatement = "SELECT * FROM coupons c "
						+ "INNER JOIN purchases p ON c.coupon_id = p.coupon_id "
						+ " WHERE p.customer_id = ? AND c.coupon_price < ? ";
				preparedStatement = connection.prepareStatement(sqlStatement);
				preparedStatement.setLong(1, customerId);
				preparedStatement.setDouble(2, maxPrice);
				result = preparedStatement.executeQuery();

				while (result.next()) {
					Coupon validCoupon = extractCouponFromResultSet(result);
					couponsByMaxPriceList.add(validCoupon);
				}
				return couponsByMaxPriceList;

			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
			} finally {
				JdbcUtils.closeResources(connection, preparedStatement, result);
			}
		}

	public List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Coupon> couponsByMaxPriceList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND coupon_price < ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.setDouble(2, maxPrice);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Coupon validCoupon = extractCouponFromResultSet(result);
				couponsByMaxPriceList.add(validCoupon);
			}
			return couponsByMaxPriceList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE coupons SET company_id=?, category=?, coupon_title=?, coupon_description=?, coupon_start_date=?, coupon_end_date=?, coupon_amount=?, coupon_price=?, coupon_image=? WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setString(2, coupon.getCategory().name());
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setDate(5, DateUtils.converDateToSqlDate(coupon.getStartDate()));
			preparedStatement.setDate(6, DateUtils.converDateToSqlDate(coupon.getEndDate()));
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImg());
			preparedStatement.setLong(10, coupon.getId());
			preparedStatement.executeUpdate();
			System.out.println("Coupon id " + coupon.getId() + " updated successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.UPDATE_ERROR, ErrorTypes.UPDATE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCoupon(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM coupons WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
			System.out.println("Coupon id " + couponId + " deleted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deleteCouponsByCompanyId(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM coupons WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
			System.out.println("Coupon id " + companyId + " deleted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteAllExpiredCoupons() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM coupons WHERE coupon_end_date < curdate()";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public boolean isCouponExistsById(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM Coupons WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponId);

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

	public boolean isCouponExistsByTitle(long companyId, String title) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM coupons WHERE company_id=? AND coupon_title=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, title);

			result = preparedStatement.executeQuery();

			if (result.next()) {
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

	private Coupon extractCouponFromResultSet(ResultSet result) throws SQLException {

		Coupon coupon = new Coupon();

		coupon.setId(result.getLong("coupon_id"));
		coupon.setCompanyId(result.getLong("company_id"));
		coupon.setCategory(Category.valueOf(result.getString("category")));
		coupon.setTitle(result.getString("coupon_title"));
		coupon.setDescription(result.getString("coupon_description"));
		coupon.setStartDate(result.getDate("coupon_start_date"));
		coupon.setEndDate(result.getDate("coupon_end_date"));
		coupon.setAmount(result.getInt("coupon_amount"));
		coupon.setPrice(result.getDouble("coupon_price"));
		coupon.setImg(result.getString("coupon_image"));

		return coupon;

	}

}
