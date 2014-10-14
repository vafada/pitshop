package com.pitshop.dao;

import com.pitshop.bean.SalesBean;
import com.pitshop.bean.PartsSalesBean;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.db.McKoiConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: bryu
 * Date: Sep 15, 2004
 * Time: 4:12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class SalesDAO {
    public List getSalesOfUser(int userId) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List result = new ArrayList();

        try {
            stat = conn.prepareStatement(GET_SALES);
            stat.setInt(1, userId);
            rs = stat.executeQuery();
            while(rs.next()) {
                SalesBean bean = generateSalesBean(rs);
                result.add(bean);
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
        return result;
    }

    public void insertSales(SalesBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        int id = 0;

        try {
            conn.setAutoCommit(false);

            stat = conn.prepareStatement("SELECT UNIQUEKEY('sales') as mykey");
            rs = stat.executeQuery();
            rs.next();
            id = rs.getInt("mykey");

            stat = conn.prepareStatement(INSERT_SALES);
            int i = 1;
            stat.setInt(i++, id);
            stat.setInt(i++, bean.getCustomerId());
            stat.setString(i++, bean.getCustomer());
            stat.setString(i++, bean.getAddress());
            stat.setDate(i++, new java.sql.Date(bean.getCreateDate().getTime()));
            stat.setString(i++, bean.getPhone());
            stat.setString(i++, bean.getRemarks());
            stat.setString(i++, bean.getSalesType());
            stat.setBoolean(i++, bean.isMember());
            stat.setString(i++, bean.getEncodedBy());
            stat.setDouble(i++, bean.getTotal());
            stat.executeUpdate();

            List parts = bean.getParts();
            Iterator iter = parts.iterator();
            while (iter.hasNext()) {
                PartsSalesBean part = (PartsSalesBean) iter.next();
                stat = conn.prepareStatement(INSERT_SALES_PART);
                i = 1;
                stat.setInt(i++, id);
                stat.setString(i++, part.getPartNumber());
                stat.setString(i++, part.getDescription());
                stat.setInt(i++, part.getQuantity());
                stat.setDouble(i++, part.getUnitPrice());
                stat.setDouble(i++, part.getDiscountedPrice());
                stat.setDouble(i++, part.getNetPrice());
                stat.setDouble(i++, part.getSubtotal());
                stat.executeUpdate();


                stat = conn.prepareStatement(DELETE_FROM_INVENTORY);

                i = 1;
                stat.setInt(i++, part.getQuantity());
                stat.setString(i++, part.getPartNumber());
                stat.executeUpdate();
            }

            conn.commit();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new PitshopDbConnectionException(sqle.getMessage(), sqle.getErrorCode());
        } finally {
            try {
                conn.setAutoCommit(true);
                rs.close();
                stat.close();
            } catch (SQLException e) {

            }
        }
    }

    private SalesBean generateSalesBean(ResultSet rs) throws SQLException {
        SalesBean bean = new SalesBean();
        bean.setId(rs.getInt("id"));
        bean.setCustomerId(rs.getInt("customer_id"));
        bean.setCustomer(rs.getString("customer"));
        bean.setAddress(rs.getString("address"));
        bean.setCreateDate(rs.getDate("create_date"));
        bean.setPhone(rs.getString("phone"));
        bean.setRemarks(rs.getString("remarks"));
        bean.setSalesType(rs.getString("sales_type"));
        bean.setMember(rs.getBoolean("member"));
        bean.setEncodedBy(rs.getString("encoded_by"));
        bean.setTotal(rs.getDouble("total"));

        return bean;
    }

    private static final String INSERT_SALES = "INSERT INTO sales (id, customer_id, customer, address, create_date, phone, remarks, sales_type, " +
            "member, encoded_by, total) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_SALES_PART = "INSERT INTO sales_part (sales_id, part_number, description, quantity, unit_price, discounted_price, " +
            "net_price, subtotal) VALUES (?,?,?,?,?,?,?,?)";
    private static final String DELETE_FROM_INVENTORY = "UPDATE parts SET quantity = quantity - ? WHERE number = ?";
    private static final String GET_SALES = "SELECT * FROM sales WHERE customer_id = ?";
}
