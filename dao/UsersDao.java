package com.dor.coupons.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dor.coupons.beans.User;
import com.dor.coupons.beans.UserLoginDetails;
import com.dor.coupons.dao.interfaces.IUsersDao;
import com.dor.coupons.enums.ClientType;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.JdbcUtils;


public class UsersDao implements IUsersDao {

	public long createUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO users (user_email, user_password, type, company_id) VALUES(?,?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUserLoginDetails().getEmail());
			preparedStatement.setString(2, user.getUserLoginDetails().getPassword());
			preparedStatement.setString(3, user.getUserLoginDetails().getType().name());
			if (user.getCompanyId() == null) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				System.out.println("User id "+ id + " created successfully");
				return id;
			} else {
				throw new ApplicationException(ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
		} finally {
			System.out.println("created your " + user.toString());
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public User getUser(long userId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM users WHERE user_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractUserFromResultSet(result);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}
	
	public User getUserByEmail(String email) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM users WHERE user_email = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractUserFromResultSet(result);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<User> getAllUsers() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<User> usersList = new ArrayList<User>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM users";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				User validUser = extractUserFromResultSet(result);
				usersList.add(validUser);
			}
			return usersList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE users SET user_email=?, user_password=?, type=?, company_id=? WHERE user_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, user.getUserLoginDetails().getEmail());
			preparedStatement.setString(2, user.getUserLoginDetails().getPassword());
			preparedStatement.setString(3, user.getUserLoginDetails().getType().name());
			if (user.getCompanyId() == null) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}
			preparedStatement.setLong(5, user.getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.UPDATE_ERROR, ErrorTypes.UPDATE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteUser(long userId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM users WHERE user_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteUsersByCompanyId(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM users WHERE company_id=?";
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
	
	public boolean isUserExistsById(long userId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM users WHERE user_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);

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

	public boolean isUserExistsByEmail(String email) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM users WHERE user_email=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);

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
	
	public ClientType login(String userName, String password) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();

			String sqlStatement = "SELECT * FROM users WHERE user_email = ? AND user_password=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);

			result = preparedStatement.executeQuery();

			if (!result.next()) {
				throw new ApplicationException(ErrorTypes.LOGIN_FAILED, ErrorTypes.LOGIN_FAILED.getGeneralErrorMessage());
			}

			ClientType clientType = ClientType.valueOf(result.getString("type"));
			return clientType;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.LOGIN_FAILED, ErrorTypes.LOGIN_FAILED.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	private User extractUserFromResultSet(ResultSet result) throws SQLException {
		User user = new User();
		UserLoginDetails userLoginDetails = new UserLoginDetails();

			userLoginDetails.setEmail(result.getString("user_email"));
			userLoginDetails.setPassword(result.getString("user_password"));
			userLoginDetails.setType(ClientType.valueOf(result.getString("type")));

			user.setUserLoginDetails(userLoginDetails);
			user.setUserId(result.getLong("user_id"));
			user.setCompanyId(result.getLong("company_id"));

			return user;
			
	}


}
