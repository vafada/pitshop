package com.pitshop.ui.sales;

import com.pitshop.PitshopException;
import com.pitshop.Utility;
import com.pitshop.bean.PartsSalesBean;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.util.*;

public class SalesTableModel extends AbstractTableModel {
    private static final String[] HEADERS = {"Part No.", "Description", "Quantity", "Unit price", "Discounted Price", "Net Price", "Subtotal"};
    private List data;
    private JTextField total;

    public SalesTableModel(JTextField total) {
        this.total = total;
        this.data = new ArrayList();
        updateTotal();
    }

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        PartsSalesBean bean = (PartsSalesBean) data.get(rowIndex);


        switch (columnIndex) {
            case 0:
                return bean.getPartNumber();
            case 1:
                return bean.getDescription();
            case 2:
                return String.valueOf(bean.getQuantity());
            case 3:
                return Utility.formatCurrency(bean.getUnitPrice());
            case 4:
                return Utility.formatCurrency(bean.getDiscountedPrice());
            case 5:
                return Utility.formatCurrency(bean.getNetPrice());
            case 6:
                return Utility.formatCurrency(bean.getSubtotal());
        }
        return "";
    }

    public void addParts(PartsSalesBean bean) throws PitshopException {
        data.add(bean);
        fireTableDataChanged();
        updateTotal();
    }

    public int getIndexOfKey(String partNumber) {
        int size = data.size();
        for(int i=0; i < size; i++) {
            PartsSalesBean salesParts = (PartsSalesBean) data.get(i);
            if(salesParts.getPartNumber().equals(partNumber)) {
                return i;
            }
        }
        return -1;
    }


    public void deleteSales(int row) throws PitshopException {
        data.remove(row);
        fireTableDataChanged();
        updateTotal();
    }

    public void updateSales(int rowIndex, PartsSalesBean bean) throws PitshopException {
        this.data.set(rowIndex, bean);
        fireTableRowsUpdated(rowIndex, rowIndex);
        updateTotal();
    }

    private void updateTotal() {
        Iterator iter = data.iterator();
        double total = 0;

        while(iter.hasNext()) {
            PartsSalesBean bean = (PartsSalesBean) iter.next();
            total += bean.getSubtotal();
        }

        this.total.setText(Utility.formatCurrency(total));
    }

    public double getTotal() {
        Iterator iter = data.iterator();
        double total = 0;

        while(iter.hasNext()) {
            PartsSalesBean bean = (PartsSalesBean) iter.next();
            total += bean.getSubtotal();
        }

        return total;
    }


    public int getRowCount() {
        return data.size();
    }

    public Object getObject(int rowIndex) {
        return data.get(rowIndex);
    }

    public List getData() {
        return data;
    }

    public void clearData() {
        data.clear();
        total.setText(Utility.formatCurrency(0));
        fireTableDataChanged();
    }
}

