package com.pitshop.ui.admin;

import com.pitshop.ui.PagingTableModel;
import com.pitshop.manager.DiscountManager;
import com.pitshop.bean.AdminCommonBean;
import com.pitshop.bean.DiscountBean;
import com.pitshop.PitshopException;

import javax.swing.*;

public class DiscountTableModel extends PagingTableModel {
    protected DiscountManager manager;
    protected String HEADERS[] = {"Name", "Value"};

    protected DiscountTableModel(JLabel page) {
        super(page);
        manager = new DiscountManager();
    }

    public void initData() throws PitshopException {
        data = manager.getDatas();
        updatePage();
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public Object getRealValueAt(int rowIndex, int columnIndex) {
        DiscountBean bean = (DiscountBean) data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return bean.getName();
            case 1:
                return String.valueOf(bean.getValue()) + "%";
        }
		return "";
    }

    public void addData(DiscountBean bean) throws PitshopException {
        try {
            int id = manager.addData(bean);
            bean.setId(id);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        boolean repage = false;
        if(getRowCount() == this.pageSize && pageOffset == getPageCount() - 1) {
            repage = true;
        }
        this.data.add(bean);
        if(repage) {
            pageOffset = getPageCount() - 1;
        }

        fireTableDataChanged();
        updatePage();
    }

    public void updateData(int rowIndex, DiscountBean bean) throws PitshopException {
    
    try {
            DiscountBean _bean = (DiscountBean) getObject(rowIndex);
            bean.setId(_bean.getId());
            manager.updateData(bean);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        int realRow = rowIndex + (pageOffset * pageSize);
        this.data.set(realRow, bean);

        fireTableRowsUpdated(rowIndex, rowIndex);
        updatePage();
    }

    public void deleteData(int row) throws PitshopException {
        DiscountBean bean = (DiscountBean) getObject(row);
        try {
            manager.deleteData(bean.getId());
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
}
