package com.pitshop.dao;

import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.CustomerBean;
import com.pitshop.db.McKoiConnection;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    public void deleteCustomer(int id) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(DELETE_CUSTOMER);
            int i = 1;
            stat.setInt(i++, id);
            stat.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new PitshopDbConnectionException(sqle.getMessage(), sqle.getErrorCode());
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {

            }
        }
    }

    public int addCustomer(CustomerBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs;
        int id;

        try {
            stat = conn.prepareStatement(GET_ID);
            rs = stat.executeQuery();
            rs.next();
            id = rs.getInt("mykey");

            stat = conn.prepareStatement(INSERT_CUSTOMER);
            int i = 1;
            stat.setInt(i++, id);
            stat.setString(i++, bean.getName());
            stat.setString(i++, bean.getAddress());
            stat.setString(i++, bean.getPhoneNumber());
            stat.setBoolean(i++, bean.isMember());
            stat.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new PitshopDbConnectionException(sqle.getMessage(), sqle.getErrorCode());
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {

            }
        }

        return id;
    }

    public void updateCustomer(CustomerBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(UPDATE_CUSTOMER);
            int i = 1;
            stat.setString(i++, bean.getName());
            stat.setString(i++, bean.getAddress());
            stat.setString(i++, bean.getPhoneNumber());
            stat.setBoolean(i++, bean.isMember());
            stat.setInt(i++, bean.getId());
            stat.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new PitshopDbConnectionException(sqle.getMessage(), sqle.getErrorCode());
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {

            }
        }
    }

    public List getCustomers() throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List data = new ArrayList();

        try {
            stat = conn.prepareStatement(GET_CUSTOMERS);
            rs = stat.executeQuery();

            while (rs.next()) {
                CustomerBean bean = getCustomerFromResultSet(rs);
                data.add(bean);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new PitshopDbConnectionException(sqle.getMessage(), sqle.getErrorCode());
        } finally {
            try {
                rs.close();
                stat.close();
            } catch (SQLException e) {

            }
        }
        return data;
    }

    private CustomerBean getCustomerFromResultSet(ResultSet rs) throws SQLException {
        CustomerBean customer = new CustomerBean();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        customer.setMember(rs.getBoolean("member"));

        return customer;
    }

    private static final String GET_ID = "SELECT UNIQUEKEY('customer') as mykey";
    private static final String GET_CUSTOMERS = "SELECT * FROM customer ORDER BY name";
    private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
    private static final String INSERT_CUSTOMER = "INSERT INTO customer (id, name, address, phone_number, member) VALUES (?,?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, address = ?, phone_number = ?, member = ? WHERE id = ?";
}
