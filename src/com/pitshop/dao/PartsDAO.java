package com.pitshop.dao;

import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.PartsBean;
import com.pitshop.db.McKoiConnection;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartsDAO {
    public PartsBean getParts(String partNumber) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(GET_PARTS);
            stat.setString(1, partNumber);
            rs = stat.executeQuery();

            if(rs.next()) {
                PartsBean bean = getInventoryFromResultSet(rs);
                return bean;
            } else {
                throw new PitshopDbConnectionException("Parts doesn't exists");
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
    }

    public List getInventory() throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List data = new ArrayList();

        try {
            stat = conn.prepareStatement(GET_INVENTORY);
            rs = stat.executeQuery();

            while (rs.next()) {
                PartsBean bean = getInventoryFromResultSet(rs);
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

    public void updateParts(PartsBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(UPDATE_PARTS);
            int i = 1;
            stat.setString(i++, bean.getDescription());
            stat.setInt(i++, bean.getQuantity());
            stat.setDouble(i++, bean.getSrp());
            stat.setString(i++, bean.getMaximumLevel());
            stat.setString(i++, bean.getReorderLevel());
            stat.setInt(i++, bean.getManufacturer().getId());
            stat.setInt(i++, bean.getCategory().getId());
            stat.setInt(i++, bean.getSource().getId());
            /*stat.setInt(i++, bean.getDiscount().getId());*/
            stat.setString(i++, bean.getNumber());
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

    public void addParts(PartsBean bean) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(INSERT_PARTS);
            int i = 1;
            stat.setString(i++, bean.getNumber());
            stat.setString(i++, bean.getDescription());
            stat.setInt(i++, bean.getQuantity());
            stat.setDouble(i++, bean.getSrp());
            stat.setString(i++, bean.getMaximumLevel());
            stat.setString(i++, bean.getReorderLevel());
            stat.setInt(i++, bean.getManufacturer().getId());
            stat.setInt(i++, bean.getCategory().getId());
            stat.setInt(i++, bean.getSource().getId());
            /*stat.setInt(i++, bean.getDiscount().getId());*/
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

    public void deleteParts(String partNumber) throws PitshopDbConnectionException {
        Connection conn = McKoiConnection.getInstance().getConn();
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(DELETE_PARTS);
            int i = 1;
            stat.setString(i++, partNumber);
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

    private PartsBean getInventoryFromResultSet(ResultSet rs) throws SQLException {
        PartsBean bean = new PartsBean();
        bean.setNumber(rs.getString("number"));
        bean.setDescription(rs.getString("description"));
        bean.setQuantity(rs.getInt("quantity"));
        bean.setSrp(rs.getDouble("srp"));
        bean.setMaximumLevel(rs.getString("max_level"));
        bean.setReorderLevel(rs.getString("reorder_level"));
        bean.getManufacturer().setId(rs.getInt("manufacturer_id"));
        bean.getManufacturer().setName(rs.getString("manufacturer"));
        bean.getCategory().setId(rs.getInt("category_id"));
        bean.getCategory().setName(rs.getString("category"));
        bean.getSource().setId(rs.getInt("source_id"));
        bean.getSource().setName(rs.getString("source"));
        //bean.getDiscount().setId(rs.getInt("discount_id"));
        //bean.getDiscount().setName(rs.getString("discount"));

        return bean;

    }

    private static final String GET_PARTS = "SELECT p.*, m.name as manufacturer, c.name as category, s.name as source " +
            "FROM parts p LEFT JOIN manufacturer m ON (p.manufacturer_id = m.id) LEFT JOIN category c ON (p.category_id = c.id) LEFT JOIN " +
            "source s ON (p.source_id = s.id) WHERE p.number = ?";
    /*private static final String GET_INVENTORY = "select p.*, m.name as manufacturer, c.name as category, s.name as source, d.name as discount " +
            "FROM parts p LEFT JOIN manufacturer m ON (p.manufacturer_id = m.id) LEFT JOIN category c ON (p.category_id = c.id) LEFT JOIN " +
            "source s ON (p.source_id = s.id) LEFT JOIN discount d ON (d.id = p.discount_id)";*/
    private static final String GET_INVENTORY = "select p.*, m.name as manufacturer, c.name as category, s.name as source " +
            "FROM parts p LEFT JOIN manufacturer m ON (p.manufacturer_id = m.id) LEFT JOIN category c ON (p.category_id = c.id) LEFT JOIN " +
            "source s ON (p.source_id = s.id)";
    private static final String INSERT_PARTS = "INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
            "category_id, source_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PARTS = "DELETE FROM parts WHERE number = ?";
    private static final String UPDATE_PARTS = "UPDATE parts SET description = ?, quantity = ?, srp = ?, max_level = ?, " +
            "reorder_level = ?, manufacturer_id = ?, category_id = ?, source_id = ? WHERE number = ?";
}
