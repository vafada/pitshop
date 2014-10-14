package com.pitshop.ui.parts;

import com.pitshop.ui.PagingTableModel;
import com.pitshop.bean.PartsBean;
import com.pitshop.PitshopException;
import com.pitshop.manager.PartsManager;

import javax.swing.*;

public class PartsTableModel extends PagingTableModel {
    private static final String[] HEADERS = {"Part No.", "Manufacturer", "Description", "Category", "Quantity", "Source", "SRP", "Maximum Level", "Reorder Level"};
    private PartsManager manager = new PartsManager();

    public PartsTableModel(JLabel page) {
        super(page);
    }

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    public Object getRealValueAt(int rowIndex, int columnIndex) {
        PartsBean bean = (PartsBean) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bean.getNumber();
            case 1:
                return bean.getManufacturer();
            case 2:
                return bean.getDescription();
            case 3:
                return bean.getCategory();
            case 4:
                return String.valueOf(bean.getQuantity());
            case 5:
                return bean.getSource();
            case 6:
                return String.valueOf(bean.getSrp());
            /*case 7:
                return bean.getDiscount();*/
            case 7:
                return bean.getMaximumLevel();
            case 8:
                return bean.getReorderLevel();
        }

        return "";

    }

    public void initData() throws PitshopException {
        data = manager.getInventory();
        fireTableDataChanged();
        updatePage();
    }

    public void addParts(PartsBean bean) throws PitshopException {
        try {
            manager.addParts(bean);
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

    public void deleteParts(int row) throws PitshopException {
        PartsBean bean = (PartsBean) getObject(row);
        try {
            manager.deleteParts(bean.getNumber());
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

    public void updateParts(int rowIndex, PartsBean bean) throws PitshopException {
        try {
            manager.updateParts(bean);
        } catch(PitshopException pe) {
            throw new PitshopException(pe.getMessage());
        }

        int realRow = rowIndex + (pageOffset * pageSize);
        this.data.set(realRow, bean);
        fireTableRowsUpdated(rowIndex, rowIndex);
        updatePage();
    }    
}
