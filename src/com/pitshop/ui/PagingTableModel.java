package com.pitshop.ui;

import com.pitshop.PitshopException;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public abstract class PagingTableModel extends AbstractTableModel {
    protected JLabel page;
    protected int pageSize = 40;
    protected int pageOffset = 0;
    protected List data;

    protected PagingTableModel(JLabel page) {
        this.page = page;
    }

    public int getRowCount() {
        if(data == null)
            return 0;

        int rc = data.size();
        if (rc <= pageSize)
            return rc;
        else if (pageOffset == getPageCount() - 1) {
            return rc - (pageOffset * pageSize);
        } else {
            return pageSize;
        }
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public int getPageCount() {
        if(data == null)
            return 0;
        return (int) Math.ceil((double) data.size() / pageSize);
    }

    // Update the page offset and fire a data changed (all rows).
    public void next() {
        if (pageOffset < getPageCount() - 1) {
            pageOffset++;
            fireTableDataChanged();
            updatePage();
        }
    }

    // Update the page offset and fire a data changed (all rows).
    public void prev() {
        if (pageOffset > 0) {
            pageOffset--;
            fireTableDataChanged();
            updatePage();
        }
    }

    public final Object getValueAt(int rowIndex, int columnIndex) {
        int realRow = rowIndex + (pageOffset * pageSize);
        return getRealValueAt(realRow, columnIndex);
    }

    public abstract Object getRealValueAt(int rowIndex, int columnIndex);
    public abstract void initData() throws PitshopException;

    protected void updatePage() {
        if(page == null)
            return;

        int totalPage = getPageCount();
        int current = getPageOffset();

        if(totalPage != 0)
            page.setText("page " + (current + 1) + " / " + totalPage);
        else
            page.setText("page 0 / 0");
    }

    public Object getObject(int rowIndex) {
        int realRow = rowIndex + (pageOffset * pageSize);
        return data.get(realRow);
    }
}
