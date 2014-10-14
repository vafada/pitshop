package com.pitshop.ui.admin;

import com.pitshop.ui.InfoPanel;
import com.pitshop.bean.AdminCommonBean;
import com.pitshop.PitshopException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminCommonPanel extends JPanel {
    public static final int NEW = 1;
    public static final int EDIT = 2;

    protected int action = NEW;

    protected JTable table;
    protected JTextField name;
    protected JButton newButton;
    protected JButton addEditButton;
    protected JButton deleteButton;
    protected InfoPanel infoPanel;

    public AdminCommonPanel(JTable table) {
        this.table = table;
        initComponents();
        buildUI();
    }

    protected void initComponents() {
        name = new JTextField(30);
        newButton = new JButton("New");
        addEditButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        infoPanel = new InfoPanel(true);
    }

    private void buildUI() {
        setLayout(new FlowLayout());
        add(new JLabel("Name:"));
        add(name);
        add(newButton);
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newAction();
            }
        });
        add(addEditButton);
        addEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEditAction();
            }
        });
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });
    }

    public void setSuccessMessage(String message) {
        infoPanel.setSuccessText(message);
        super.add(infoPanel);
        updateUI();
    }

    public void removeInfoPanel() {
        super.remove(infoPanel);
        updateUI();
    }

    public void setErrorMessage(String message) {
        infoPanel.setErrorText(message);
        super.add(infoPanel);
        updateUI();
    }

    public void fillForm(int row) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            AdminCommonTableModel model = (AdminCommonTableModel) table.getModel();
            AdminCommonBean bean = (AdminCommonBean) model.getObject(selectedRow);
            action = EDIT;
            this.name.setText(bean.getName());
            this.addEditButton.setText("Update");
        } else {
            action = NEW;
            this.name.setText("");
            this.addEditButton.setText("Add");
        }
    }

    public void addEditAction() {
        if (validateInput()) {
            AdminCommonBean bean = new AdminCommonBean();
            bean.setName(this.name.getText());
            AdminCommonTableModel model = (AdminCommonTableModel) table.getModel();
            int row = table.getSelectedRow();

            try {
                if (action == NEW) {
                    model.addData(bean);
                    setSuccessMessage("Successfully added data");
                } else if (action == EDIT && row != -1) {
                    model.updateData(row, bean);
                    setSuccessMessage("Successfully updated data");
                }
                this.addEditButton.setText("Add");
                this.name.setText("");
                action = NEW;
            } catch (PitshopException pe) {
                setErrorMessage(pe.getMessage());
            }
        }
    }

    private boolean validateInput() {
        if (name.getText().trim().length() > 50) {
            setErrorMessage("Name must be less than 50 characters");
            return false;
        }
        if (name.getText().trim().length() == 0) {
            setErrorMessage("Invalid name");
            return false;
        }
        removeInfoPanel();
        return true;
    }

    public void deleteAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            AdminCommonTableModel model = (AdminCommonTableModel) table.getModel();
            try {
                model.deleteData(selectedRow);
                this.name.setText("");
                this.action = NEW;
                this.addEditButton.setText("Add");
            } catch (PitshopException e) {
                setErrorMessage(e.getMessage());
            }
        }
    }

    public void newAction() {
        this.name.setText("");
        this.action = NEW;
        this.addEditButton.setText("Add");
    }


}

