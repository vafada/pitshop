package com.pitshop.ui.admin;

import com.pitshop.ui.PagingTableModel;
import com.pitshop.manager.AdminCommonManager;
import com.pitshop.bean.AdminCommonBean;
import com.pitshop.PitshopException;

import javax.swing.*;

public class AdminCommonTableModel extends PagingTableModel {
    protected AdminCommonManager manager;
    protected String TABLE_NAME;
    protected String COLUMN_NAME;

    protected AdminCommonTableModel(JLabel page, String tableName, String columnName) {
        super(page);
        this.TABLE_NAME = tableName;
        this.COLUMN_NAME = columnName;
        manager = new AdminCommonManager();
    }

    public void initData() throws PitshopException {
        data = manager.getDatas(TABLE_NAME);
        updatePage();
    }

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int column) {
        return COLUMN_NAME;
    }

    public Object getRealValueAt(int rowIndex, int columnIndex) {
        AdminCommonBean bean = (AdminCommonBean) data.get(rowIndex);
        return bean.getName();
    }

    public void addData(AdminCommonBean bean) throws PitshopException {
        try {
            int id = manager.addData(TABLE_NAME, bean);
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

    public void updateData(int rowIndex, AdminCommonBean bean) throws PitshopException {
        try {
            AdminCommonBean _bean = (AdminCommonBean) getObject(rowIndex);
            bean.setId(_bean.getId());
            manager.updateData(this.TABLE_NAME, bean);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        int realRow = rowIndex + (pageOffset * pageSize);
        this.data.set(realRow, bean);
        fireTableRowsUpdated(rowIndex, rowIndex);
        updatePage();
    }

    public void deleteData(int row) throws PitshopException {
        AdminCommonBean bean = (AdminCommonBean) getObject(row);
        try {
            manager.deleteData(TABLE_NAME, bean.getId());
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
