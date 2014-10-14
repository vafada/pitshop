package com.pitshop.manager;

import com.pitshop.PitshopException;
import com.pitshop.PitshopDbConnectionException;
import com.pitshop.bean.CustomerBean;
import com.pitshop.dao.CustomerDAO;

import java.util.List;

public class CustomerManager {
    private CustomerDAO dao = new CustomerDAO();

    public void deleteCustomer(int id)  throws PitshopException {
        try {
            dao.deleteCustomer(id);
        } catch(PitshopDbConnectionException dbe) {
            throw new PitshopException("Error deleting customer");
        }
    }

    public void updateCustomer(CustomerBean bean)  throws PitshopException {
        try {
            dao.updateCustomer(bean);
        } catch(PitshopDbConnectionException dbe) {
            throw new PitshopException("Error updating customer");
        }
    }

    public int addCustomer(CustomerBean bean)  throws PitshopException {
        try {
            return dao.addCustomer(bean);
        } catch(PitshopDbConnectionException dbe) {
            throw new PitshopException("Error adding customer");
        }
    }

    public List getCustomers() throws PitshopException {
        try {
            return dao.getCustomers();
        } catch(PitshopDbConnectionException dbe) {
            throw new PitshopException("Error retrieving data");
        }
    }
}
