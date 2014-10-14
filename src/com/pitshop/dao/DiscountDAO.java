package com.pitshop.dao;

import com.pitshop.db.McKoiConnection;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.DiscountBean;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class DiscountDAO { 
    public List getDatas() throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List data = new ArrayList();

        try {
            stat = conn.prepareStatement("SELECT * FROM discount");
            rs = stat.executeQuery();

            while (rs.next()) {
                DiscountBean bean = getDiscountBeanFromResultSet(rs);
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

    public void deleteData(int id) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement("DELETE FROM discount WHERE id = ?");
            stat.setInt(1, id);
            stat.executeQuery();

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

    public int addData(DiscountBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        int id = 0;

        try {
            stat = conn.prepareStatement("SELECT UNIQUEKEY('discount') as mykey");
            rs = stat.executeQuery();
            rs.next();
            id = rs.getInt("mykey");

            StringBuffer buffer = new StringBuffer("INSERT INTO discount (id, name, value) VALUES (?,?,?)");
            stat = conn.prepareStatement(buffer.toString());
            stat.setInt(1, id);
            stat.setString(2, bean.getName());
			stat.setInt(3, bean.getValue());
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

    public void updateData(DiscountBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            StringBuffer buffer = new StringBuffer("UPDATE discount SET name = ?, value = ? WHERE id = ?");
            stat = conn.prepareStatement(buffer.toString());
            stat.setString(1, bean.getName());
            stat.setInt(2, bean.getValue());
            stat.setInt(3, bean.getId());
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

    private DiscountBean getDiscountBeanFromResultSet(ResultSet rs) throws SQLException {
        DiscountBean bean = new DiscountBean();
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));
		bean.setValue(rs.getInt("value"));

        return bean;
    }
}
