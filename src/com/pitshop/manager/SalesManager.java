package com.pitshop.manager;

import com.pitshop.dao.SalesDAO;
import com.pitshop.PitshopException;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.SalesBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bryu
 * Date: Sep 16, 2004
 * Time: 11:00:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SalesManager {
    private SalesDAO dao = new SalesDAO();

    public List getSalesOfUser(int userId) throws PitshopException {
        try {
            return dao.getSalesOfUser(userId);
        } catch(PitshopDbConnectionException dbe) {
            throw new PitshopException("Error getting sales");
        }
    }

    public void insertSales(SalesBean bean) throws PitshopException {
        try {
            dao.insertSales(bean);
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException("Error adding sales");
        }
    }
}
