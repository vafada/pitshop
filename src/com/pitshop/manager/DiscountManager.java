package com.pitshop.manager;

import com.pitshop.dao.DiscountDAO;
import com.pitshop.PitshopException;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.DiscountBean;

import java.util.logging.Logger;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bryu
 * Date: Sep 14, 2004
 * Time: 8:43:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class DiscountManager {
    private DiscountDAO dao = new DiscountDAO();
    private static Logger logger = Logger.getLogger("com.pitshop.manager.DiscountManager");

    public List getDatas() throws PitshopException {
        try {
            return dao.getDatas();
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error retrieving data");
        }
    }

    public void deleteData(int id) throws PitshopException {
        try {
            dao.deleteData(id);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error deleting data. Is data being used?");
        }
    }

    public int addData(DiscountBean bean) throws PitshopException {
        try {
            return dao.addData(bean);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error adding data");
        }
    }

    public void updateData(DiscountBean bean) throws PitshopException {
        try {
            dao.updateData(bean);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error updating data");
        }
    }
}
