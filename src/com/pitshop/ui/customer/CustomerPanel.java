package com.pitshop.ui.customer;

import com.pitshop.ui.SimpleInternalFrame;
import com.pitshop.ui.InfoPanel;
import com.pitshop.ui.PagingTableModel;
import com.pitshop.PitshopException;
import com.pitshop.manager.SalesManager;
import com.pitshop.bean.CustomerBean;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.ButtonBarBuilder;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerPanel extends JPanel {
    private static final String[] membersChoices = {"No", "Yes"};
    public static final int NEW = 1;
    public static final int EDIT = 2;

    protected int action = NEW;

    private JTextField name;
    private JTextField phoneNumber;
    private JTextField address;
    private JComboBox member;

    private JButton newButton;
    private JButton addEditButton;
    private JButton deleteButton;
    private JButton transactionHistoryButton;
    private JPanel buttonPanel;

    private CustomerTableModel tableModel;
    private JTable customerTable;
    private JLabel page;
    private InfoPanel infoPanel;

    private SalesManager salesManager;

    public CustomerPanel() {
        this.salesManager = new SalesManager();
        initComponents();
        buildUI();
    }

    private void initComponents() {
        name = new JTextField(30);
        phoneNumber = new JTextField(30);
        address = new JTextField(30);
        member = new JComboBox(membersChoices);

        newButton = new JButton("New");
        addEditButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        transactionHistoryButton = new JButton("Transaction history");
        page = new JLabel("page 0 / 0");
        tableModel = new CustomerTableModel(page);
        customerTable = new JTable(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoPanel = new InfoPanel(true);

        try {
            tableModel.initData();
        } catch (PitshopException e) {
            setErrorMessage(e.getMessage());
        }

    }

    private void setErrorMessage(String message) {
        infoPanel.setErrorText(message);
        buttonPanel.add(infoPanel, BorderLayout.CENTER);
        buttonPanel.updateUI();
    }

    public void setSuccessMessage(String message) {
        infoPanel.setSuccessText(message);
        buttonPanel.add(infoPanel);
        buttonPanel.updateUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        SimpleInternalFrame internalFrame = new SimpleInternalFrame("CUSTOMER DATA");

        FormLayout layout = new FormLayout("left:max(40dlu;pref), 5dlu, 120dlu, 15dlu, left:max(40dlu;pref), 5dlu, 120dlu, fill:p:grow",
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p, 5dlu, fill:1dlu:grow, 5dlu, p");

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        int y = 1;

        builder.addSeparator("CUSTOMER DATA INPUT", cc.xyw(1, y, 8));

        y += 2;

        builder.addLabel("Name:", cc.xy(1, y));
        builder.add(name, cc.xy(3, y));
        builder.addLabel("Phone Number:", cc.xy(5, y));
        builder.add(phoneNumber, cc.xy(7, y));

        y += 2;

        builder.addLabel("Address:", cc.xy(1, y));
        builder.add(address, cc.xy(3, y));
        builder.addLabel("Member:", cc.xy(5, y));
        builder.add(member, cc.xy(7, y));

        y += 2;

        ButtonBarBuilder bbuilder = new ButtonBarBuilder();
        bbuilder.addGriddedButtons(new JButton[]{newButton, addEditButton, deleteButton, transactionHistoryButton});
        JPanel tempPanel = bbuilder.getPanel();

        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(tempPanel, BorderLayout.WEST);

        builder.add(buttonPanel, cc.xyw(1, y, 8));

        y += 2;

        builder.addSeparator("CUSTOMER DATABASE", cc.xyw(1, y, 8));

        y += 2;

        SimpleInternalFrame tableInternalFrame = new SimpleInternalFrame("Customer List");

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(customerTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        JPanel pagingPanel = new JPanel(new BorderLayout());
        JButton prev = new JButton("< prev");
        JButton next = new JButton("next >");

        pagingPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pagingPanel.add(prev, BorderLayout.WEST);
        pagingPanel.add(next, BorderLayout.EAST);
        pagingPanel.add(page, BorderLayout.CENTER);
        page.setHorizontalAlignment(JLabel.CENTER);
        tablePanel.add(pagingPanel, BorderLayout.SOUTH);

        tableInternalFrame.setContent(tablePanel);
        builder.add(tableInternalFrame, cc.xyw(1, y, 8));
        internalFrame.setContent(builder.getPanel());

        add(internalFrame, BorderLayout.CENTER);

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((PagingTableModel) tableModel).prev();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((PagingTableModel) tableModel).next();
            }
        });

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAll();
                removeInfoPanel();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        tableModel.deleteCustomer(selectedRow);
                        clearAll();
                        setSuccessMessage("Deleted customer");
                    } catch (PitshopException pe) {
                        setErrorMessage(pe.getMessage());
                    }
                }
            }
        });

        addEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = customerTable.getSelectedRow();
                if (validateInput()) {
                    if (action == NEW) {
                        CustomerBean bean = generateCustomerBean();
                        try {
                            tableModel.addCustomer(bean);
                            setSuccessMessage("Added customer");
                        } catch (PitshopException pe) {
                            setErrorMessage(pe.getMessage());
                        }
                    } else if (action == EDIT && row != -1) {
                        CustomerBean bean = generateCustomerBean();
                        try {
                            tableModel.updateCustomer(row, bean);
                            setSuccessMessage("Updated customer");
                        } catch (PitshopException pe) {
                            setErrorMessage(pe.getMessage());
                        }
                    }
                }
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        CustomerBean bean = (CustomerBean) tableModel.getObject(selectedRow);
                        java.util.List sales = salesManager.getSalesOfUser(bean.getId());
                        if (sales.size() > 0) {
                            new TransactionHistoryDialog(bean, sales);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No transaction history.",
                                    "No sales",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (PitshopException pe) {
                        setErrorMessage(pe.getMessage());
                    }
                }
            }
        });

        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                if (e.getSource() == customerTable.getSelectionModel()) {
                    int row = customerTable.getSelectedRow();
                    fillForm(row);
                    action = EDIT;
                }
            }
        });
    }

    private void fillForm(int row) {
        if (row != -1) {
            CustomerBean bean = (CustomerBean) tableModel.getObject(row);
            action = EDIT;
            this.name.setText(bean.getName());
            this.phoneNumber.setText(bean.getPhoneNumber());
            this.address.setText(bean.getAddress());
            this.member.setSelectedIndex(bean.isMember() ? 1 : 0);
            this.addEditButton.setText("Update");
        } else {
            clearAll();
        }
    }

    private CustomerBean generateCustomerBean() {
        CustomerBean bean = new CustomerBean();
        bean.setName(name.getText().trim());
        bean.setPhoneNumber(phoneNumber.getText().trim());
        bean.setAddress(address.getText().trim());
        bean.setMember(member.getSelectedIndex() == 0 ? false : true);

        return bean;
    }

    private boolean validateInput() {
        if (name.getText().length() == 0) {
            setErrorMessage("Enter name");
            return false;
        } else if (name.getText().trim().length() > 50) {
            setErrorMessage("Name must be less than 50 characters");
            return false;
        }

        if (phoneNumber.getText().length() == 0) {
            setErrorMessage("Enter phone number");
            return false;
        } else if (phoneNumber.getText().trim().length() > 50) {
            setErrorMessage("Phone number must be less than 50 characters");
            return false;
        }

        if (address.getText().length() == 0) {
            setErrorMessage("Enter address");
            return false;
        } else if (name.getText().trim().length() > 100) {
            setErrorMessage("Address must be less than 100 characters");
            return false;
        }


        return true;
    }

    private void clearAll() {
        action = NEW;
        this.name.setText("");
        this.phoneNumber.setText("");
        this.address.setText("");
        this.member.setSelectedIndex(0);
        this.addEditButton.setText("Add");
    }

    public void removeInfoPanel() {
        buttonPanel.remove(infoPanel);
        buttonPanel.updateUI();
    }
}
