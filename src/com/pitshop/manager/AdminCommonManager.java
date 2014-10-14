package com.pitshop.manager;

import com.pitshop.dao.AdminCommonDAO;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.PitshopException;
import com.pitshop.bean.AdminCommonBean;

import java.util.List;
import java.util.logging.Logger;

public class AdminCommonManager {
    private AdminCommonDAO dao = new AdminCommonDAO();
    private static Logger logger = Logger.getLogger("com.pitshop.manager.AdminCommonManager");

    public List getDatas(String tableName) throws PitshopException {
        try {
            return dao.getDatas(tableName);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error retrieving data");
        }
    }

    public void deleteData(String tableName, int id) throws PitshopException {
        try {
            dao.deleteData(tableName, id);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error deleting data. Is data being used?");
        }
    }

    public int addData(String tableName, AdminCommonBean bean) throws PitshopException {
        try {
            return dao.addData(tableName, bean);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error adding data");
        }
    }

    public void updateData(String tableName, AdminCommonBean bean) throws PitshopException {
        try {
            dao.updateData(tableName, bean);
        } catch(PitshopDbConnectionException dbe) {
            logger.warning(dbe.getMessage());
            throw new PitshopException("Error updating data");
        }
    }
}
