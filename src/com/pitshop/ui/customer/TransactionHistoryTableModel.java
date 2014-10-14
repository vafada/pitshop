package com.pitshop.ui.customer;

import com.pitshop.bean.SalesBean;
import com.pitshop.Utility;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TransactionHistoryTableModel extends AbstractTableModel {
    private static final String HEADERS[] = {"Date", "Remarks", "Total"};

    private List data;

    public TransactionHistoryTableModel(List data) {
        this.data = data;
    }
    

    public int getColumnCount() {
        return HEADERS.length;
    }

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        SalesBean sales = (SalesBean) data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return Utility.formatDate(sales.getCreateDate());
            case 1:
                return sales.getRemarks();
            case 2:
                return Utility.formatCurrency(sales.getTotal());
        }
        return "";
    }
}
