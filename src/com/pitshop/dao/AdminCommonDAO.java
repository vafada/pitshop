package com.pitshop.dao;

import com.pitshop.db.McKoiConnection;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.AdminCommonBean;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class AdminCommonDAO {
    public List getDatas(String tableName) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List data = new ArrayList();

        try {
            StringBuffer buffer = new StringBuffer("SELECT * FROM ");
            buffer.append(tableName);
            stat = conn.prepareStatement(buffer.toString());
            rs = stat.executeQuery();

            while (rs.next()) {
                AdminCommonBean bean = getAdminCommonBeanFromResultSet(rs);
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

    public void deleteData(String tableName, int id) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            StringBuffer buffer = new StringBuffer("DELETE FROM ");
            buffer.append(tableName).append(" WHERE id = ?");
            stat = conn.prepareStatement(buffer.toString());
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

    public int addData(String tableName, AdminCommonBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        int id = 0;

        try {
            stat = conn.prepareStatement("SELECT UNIQUEKEY(?) as mykey");
            stat.setString(1, tableName);
            rs = stat.executeQuery();
            rs.next();
            id = rs.getInt("mykey");

            StringBuffer buffer = new StringBuffer("INSERT INTO ");
            buffer.append(tableName).append(" (id, name) VALUES (?,?)");
            stat = conn.prepareStatement(buffer.toString());
            stat.setInt(1, id);
            stat.setString(2, bean.getName());
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

    public void updateData(String tableName, AdminCommonBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            StringBuffer buffer = new StringBuffer("UPDATE ");
            buffer.append(tableName).append(" SET name = ? WHERE id = ?");
            stat = conn.prepareStatement(buffer.toString());
            stat.setString(1, bean.getName());
            stat.setInt(2, bean.getId());
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

    private AdminCommonBean getAdminCommonBeanFromResultSet(ResultSet rs) throws SQLException {
        AdminCommonBean bean = new AdminCommonBean();
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));

        return bean;
    }
}
