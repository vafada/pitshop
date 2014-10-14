package com.pitshop.manager;

import com.pitshop.PitshopException;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.PartsBean;
import com.pitshop.dao.PartsDAO;

import java.util.List;

public class PartsManager {
    private PartsDAO dao = new PartsDAO();

    public List getInventory() throws PitshopException {
        try {
            return dao.getInventory();
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException("Error getting inventory");
        }
    }

    public PartsBean getParts(String partNumber) throws PitshopException {
        try {
            return dao.getParts(partNumber);
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException(pe.getMessage());
        }
    }

    public void addParts(PartsBean bean) throws PitshopException {
        try {
            dao.addParts(bean);
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException("Error adding parts. Check part number.");
        }
    }

    public void deleteParts(String partNumber) throws PitshopException {
        try {
            dao.deleteParts(partNumber);
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException("Error deleting parts.");
        }
    }

    public void updateParts(PartsBean bean) throws PitshopException {
        try {
            dao.updateParts(bean);
        } catch(PitshopDbConnectionException pe) {
            throw new PitshopException("Error updating parts.");
        }
    }
}
