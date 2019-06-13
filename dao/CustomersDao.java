package com.dor.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dor.coupons.beans.Customer;
import com.dor.coupons.beans.User;
import com.dor.coupons.dao.interfaces.ICustomersDao;
import com.dor.coupons.enums.ErrorTypes;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.utils.JdbcUtils;


public class CustomersDao implements ICustomersDao {

	public long createCustomer(Customer customer) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO customers (customer_id , customer_first_name, customer_last_name) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customer.getUser().getUserId());
			preparedStatement.setString(2, customer.getFirstName());
			preparedStatement.setString(3, customer.getLastName());

			preparedStatement.executeUpdate();
			long id = customer.getUser().getUserId();
			System.out.println("Custumer id "+ id + " created successfully");
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.CREATE_ERROR, ErrorTypes.CREATE_ERROR.getGeneralErrorMessage());
		} finally {
			System.out.println("created your " + customer.toString());
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Customer getCustomer(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * from customers WHERE customer_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractCustomerFromResultSet(result);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Customer> customersList = new ArrayList<Customer>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM customers";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Customer validCustomer = extractCustomerFromResultSet(result);
				customersList.add(validCustomer);
			}
			return customersList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.READ_ERROR, ErrorTypes.READ_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE customers SET customer_first_name=? , customer_last_name=? WHERE customer_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setLong(3, customer.getUser().getUserId());
			preparedStatement.executeUpdate();
			System.out.println("Customer id "+ customer.getUser().getUserId() + " updated successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.UPDATE_ERROR, ErrorTypes.UPDATE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCustomer(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM customers WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
			System.out.println("Customer id "+ customerId + " deleted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.DELETE_ERROR, ErrorTypes.DELETE_ERROR.getGeneralErrorMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public boolean isCustomerExistsById(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM customers WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);

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

	private Customer extractCustomerFromResultSet(ResultSet result) throws SQLException {

		Customer customer = new Customer();
		User user = new User();

		user.setUserId(result.getLong("customer_id"));
		
		customer.setUser(user);
		customer.setFirstName(result.getString("customer_first_name"));
		customer.setLastName(result.getString("customer_last_name"));

		return customer;

	}
}
