package com.pitshop.ui.customer;

import com.pitshop.ui.PagingTableModel;
import com.pitshop.bean.CustomerBean;
import com.pitshop.PitshopException;
import com.pitshop.manager.CustomerManager;

import javax.swing.*;

public class CustomerTableModel extends PagingTableModel {
    private static final String[] HEADERS = {"Name", "Address", "Telephone No.", "Member"};
    private CustomerManager manager = new CustomerManager();

    public CustomerTableModel(JLabel page) {
        super(page);
    }

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    public Object getRealValueAt(int rowIndex, int columnIndex) {
        CustomerBean bean = (CustomerBean) data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return bean.getName();
            case 1:
                return bean.getAddress();
            case 2:
                return bean.getPhoneNumber();
            case 3:
                return bean.isMember() ? "Yes" : "No";
        }
        return "";
    }

    public void initData() throws PitshopException {
        data = manager.getCustomers();
        updatePage();
    }

    public void addCustomer(CustomerBean bean) throws PitshopException {
        try {
            int id = manager.addCustomer(bean);
            bean.setId(id);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        boolean repage = false;
        if(getRowCount() == this.pageSize && pageOffset == getPageCount() -1) {
            repage = true;
        }
        this.data.add(bean);
        if(repage) {
            pageOffset = getPageCount() - 1;
        }
        fireTableDataChanged();
        updatePage();
    }

    public void deleteCustomer(int row) throws PitshopException {
        CustomerBean bean = (CustomerBean) getObject(row);
        try {
            manager.deleteCustomer(bean.getId());
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        boolean repage = false;
        if(getRowCount() == 1 && pageOffset == getPageCount() - 1) {
            repage = true;
        }
        int realRow = row + (pageOffset * pageSize);
        data.remove(realRow);

        if(repage) {
            pageOffset = getPageCount() - 1;
        }
        fireTableDataChanged();
        updatePage();
    }

    public void updateCustomer(int rowIndex, CustomerBean bean) throws PitshopException {
        try {
            CustomerBean _bean = (CustomerBean) getObject(rowIndex);
            bean.setId(_bean.getId());
            manager.updateCustomer(bean);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        int realRow = rowIndex + (pageOffset * pageSize);
        this.data.set(realRow, bean);
        fireTableRowsUpdated(rowIndex, rowIndex);
        updatePage();
    }
}
