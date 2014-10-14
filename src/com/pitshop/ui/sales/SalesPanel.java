package com.pitshop.ui.sales;

import com.pitshop.ui.SimpleInternalFrame;
import com.pitshop.ui.InfoPanel;
import com.pitshop.Utility;
import com.pitshop.PitshopException;
import com.pitshop.bean.*;
import com.pitshop.manager.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.Caret;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.text.ParseException;

public class SalesPanel extends JPanel {
    private static final String[] membersChoices = {"No", "Yes"};

    private JComboBox name;
    private JTextField date;
    private JTextField address;
    private JTextField remarks;
    private JTextField phone;
    private JComboBox discountLvl;
    private JComboBox salesType;
    private JComboBox member;
    private JComboBox encoderBy;
    private JButton dateButton;
    private JFrame parent;
    private JTextField partNumber;
    private JTable partsTable;
    private SalesTableModel tableModel;
    private JButton addCustomer;
    private JTextField quantity;
    private JTextField unitPrice;
    private JTextField description;
    private JTextField total;

    private JButton newButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton processButton;

    private DefaultComboBoxModel customerModel;
    private DefaultComboBoxModel encoderModel;
    private DefaultComboBoxModel salesTypeModel;
    private DefaultComboBoxModel discountLvlModel;

    private AdminCommonManager adminManager;
    private DiscountManager discountManager;
    private CustomerManager customerManager;
    private PartsManager partsManager;
    private SalesManager salesManager;

    private InfoPanel partsInfoPanel;
    private JPanel partsInfoContainer;

    public SalesPanel(JFrame parent) {
        this.salesManager = new SalesManager();
        this.partsManager = new PartsManager();
        this.adminManager = new AdminCommonManager();
        this.customerManager = new CustomerManager();
        this.discountManager = new DiscountManager();
        this.parent = parent;
        initComponents();
        buildUI();
    }

    private void initComponents() {
        customerModel = new DefaultComboBoxModel();
        name = new JComboBox(customerModel);
        new ComboBoxAutoCompleteMgr(name);
        date = new JTextField(20);
        date.setEditable(false);
        address = new JTextField(20);
        remarks = new JTextField(20);
        phone = new JTextField(20);
        discountLvlModel = new DefaultComboBoxModel();
        discountLvl = new JComboBox(discountLvlModel);
        salesTypeModel = new DefaultComboBoxModel();
        salesType = new JComboBox(salesTypeModel);
        encoderModel = new DefaultComboBoxModel();
        encoderBy = new JComboBox(encoderModel);
        member = new JComboBox(membersChoices);
        partNumber = new JTextField(20);
        quantity = new JTextField(20);
        unitPrice = new JTextField(20);
        description = new JTextField(20);
        total = new JTextField(20);
        total.setEditable(false);
        total.setHorizontalAlignment(JTextField.RIGHT);

        addCustomer = new JButton("Add customer");
        newButton = new JButton("New");
        addButton = new JButton("Add");
        editButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        processButton = new JButton("Process");

        tableModel = new SalesTableModel(total);
        partsTable = new JTable(tableModel);
        partsTable.getTableHeader().setReorderingAllowed(false);
        partsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        partsInfoPanel = new InfoPanel(true);
        partsInfoContainer = new JPanel(new BorderLayout());

    }

    private void buildUI() {
        setLayout(new BorderLayout());
        SimpleInternalFrame internalFrame = new SimpleInternalFrame("SALES");

        FormLayout layout = new FormLayout("left:max(40dlu;pref), 5dlu, 80dlu, 15dlu, left:max(40dlu;pref), 5dlu, 80dlu, 15dlu, left:max(20dlu;pref), 5dlu, 80dlu, right:p:grow",
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p, 5dlu, p, 5dlu, p, 5dlu, fill:1dlu:grow, 5dlu, p");

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        int y = 1;

        builder.addSeparator("DETAILS", cc.xyw(1, y, 12));

        y += 2;

        builder.addLabel("Name:", cc.xy(1, y));
        builder.add(name, cc.xy(3, y));

        JPanel datePanel = new JPanel(new BorderLayout());
        dateButton = new JButton(Utility.readImageIcon("calendar.gif"));
        dateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(SalesPanel.this.parent, true);
                JCalendar calendar = new JCalendar();
                calendar.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("calendar")) {
                            Calendar calendar = (Calendar) evt.getNewValue();
                            date.setText(Utility.formatDate(calendar.getTime()));
                        }
                    }
                });
                dialog.getContentPane().add(calendar);
                dialog.pack();
                Dimension paneSize = dialog.getSize();
                Dimension screenSize = dialog.getToolkit().getScreenSize();
                dialog.setLocation((screenSize.width - paneSize.width) / 2, (screenSize.height - paneSize.height) / 2);
                dialog.setVisible(true);
            }
        });

        dateButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        datePanel.add(date, BorderLayout.CENTER);
        date.setText(Utility.formatDate(new Date()));
        date.setEditable(false);
        datePanel.add(dateButton, BorderLayout.EAST);

        builder.addLabel("Address:", cc.xy(5, y));
        builder.add(address, cc.xy(7, y));
        builder.addLabel("Date:", cc.xy(9, y));
        builder.add(datePanel, cc.xy(11, y));

        y += 2;

        builder.addLabel("Phone:", cc.xy(1, y));
        builder.add(phone, cc.xy(3, y));
        builder.addLabel("Remarks:", cc.xy(5, y));
        builder.add(remarks, cc.xy(7, y));
        builder.addLabel("Sales type:", cc.xy(9, y));
        builder.add(salesType, cc.xy(11, y));

        y += 2;

        builder.addLabel("Member:", cc.xy(1, y));
        builder.add(member, cc.xy(3, y));
        builder.addLabel("Encoded by:", cc.xy(5, y));
        builder.add(encoderBy, cc.xy(7, y));


        y += 2;

        ButtonBarBuilder bbuilder = new ButtonBarBuilder();
        bbuilder.addGriddedButtons(new JButton[]{addCustomer});
        builder.add(bbuilder.getPanel(), cc.xyw(1, y, 3));


        addCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerBean bean = generateCustomerBean();
                try {
                    int id = customerManager.addCustomer(bean);
                    bean.setId(id);
                    customerModel.addElement(bean);
                    name.setSelectedItem(bean);
                    fillCustomerDetails(bean);
                    //setSuccessMessage("Added customer");
                } catch (PitshopException pe) {
                    //setErrorMessage(pe.getMessage());
                }
            }
        });

        y += 2;

        builder.addSeparator("PARTS", cc.xyw(1, y, 12));

        y += 2;

        builder.addLabel("Part number:", cc.xy(1, y));
        builder.add(partNumber, cc.xy(3, y));
        builder.addLabel("Description:", cc.xy(5, y));
        description.setEditable(false);
        description.setFocusable(false);
        builder.add(description, cc.xy(7, y));
        builder.addLabel("Quantity:", cc.xy(9, y));
        builder.add(quantity, cc.xy(11, y));

        y += 2;

        builder.addLabel("Discount:", cc.xy(1, y));
        builder.add(discountLvl, cc.xy(3, y));
        builder.addLabel("Unit Price:", cc.xy(5, y));
        builder.add(unitPrice, cc.xy(7, y));
        builder.add(partsInfoContainer, cc.xyw(9, y, 3));

        y += 2;

        bbuilder = new ButtonBarBuilder();
        bbuilder.addGriddedButtons(new JButton[]{newButton, addButton, editButton, deleteButton});
        builder.add(bbuilder.getPanel(), cc.xyw(1, y, 11));

        y += 2;

        SimpleInternalFrame tableInternalFrame = new SimpleInternalFrame("PARTS");

        tableInternalFrame.setContent(new JScrollPane(partsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        builder.add(tableInternalFrame, cc.xyw(1, y, 12));

        y += 2;

        bbuilder = new ButtonBarBuilder();
        bbuilder.addGriddedButtons(new JButton[]{processButton});
        builder.add(bbuilder.getPanel(), cc.xyw(1, y, 3));

        JPanel totalPanel = new JPanel(new FlowLayout());
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(total);
        builder.add(totalPanel, cc.xy(12, y));

        internalFrame.setContent(builder.getPanel());
        add(internalFrame, BorderLayout.CENTER);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK), "focus");
        this.getActionMap().put("focus", new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                setFocusOnPart();
            }
        });

        partNumber.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (partNumber.getText().trim().length() != 0) {
                    try {
                        PartsBean bean = partsManager.getParts(partNumber.getText());
                        description.setText(bean.getDescription());
                        removePartInfoPanel();
                    } catch (PitshopException e1) {
                        setErrorMessagePartInfoPanel(e1.getMessage());
                        description.setText("");
                    }
                }
            }
        });

        partNumber.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    // \n was detected
                    try {
                        PartsBean bean = partsManager.getParts(partNumber.getText());
                        description.setText(bean.getDescription());
                        removePartInfoPanel();
                    } catch (PitshopException e1) {
                        setErrorMessagePartInfoPanel(e1.getMessage());
                        description.setText("");
                    }
                }
            }

            public void keyTyped(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    int index = tableModel.getIndexOfKey(partNumber.getText().trim());
                    if (index != -1) {
                        int answer = JOptionPane.showConfirmDialog(SalesPanel.this, "Product already exist. Replace existing entry?", "", JOptionPane.YES_NO_OPTION);
                        if (answer == 0) { //yes
                            PartsSalesBean bean = generatePartSalesBean();
                            try {
                                tableModel.updateSales(index, bean);
                                clearAll();
                            } catch (PitshopException e1) {
                                setErrorMessagePartInfoPanel(e1.getMessage());
                            }
                        } else {
                            clearAll();
                        }
                    } else {
                        removePartInfoPanel();
                        PartsSalesBean bean = generatePartSalesBean();
                        try {
                            tableModel.addParts(bean);
                            clearAll();
                        } catch (PitshopException e1) {
                            setErrorMessagePartInfoPanel(e1.getMessage());
                        }
                    }
                }
            }
        });

        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = partsTable.getSelectedRow();
                if (selectedRow != -1) {
                    if (validateInput()) {
                        PartsSalesBean bean = generatePartSalesBean();
                        try {
                            tableModel.updateSales(selectedRow, bean);
                            clearAll();
                        } catch (PitshopException e1) {
                            setErrorMessagePartInfoPanel(e1.getMessage());
                        }
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = partsTable.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        tableModel.deleteSales(selectedRow);
                        clearAll();
                    } catch (PitshopException pe) {
                        //setErrorMessage(pe.getMessage());
                    }
                }
            }
        });

        partsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                if (e.getSource() == partsTable.getSelectionModel()) {
                    int row = partsTable.getSelectedRow();
                    fillForm(row);
                }
            }
        });

        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.util.List data = tableModel.getData();
                if (data.size() > 0) {
                    try {
                        SalesBean bean = generateSalesBean();
                        salesManager.insertSales(bean);
                        resetData();
                        setSuccessMessagePartInfoPanel("Processed Transaction");
                    } catch (ParseException e1) {
                        setErrorMessagePartInfoPanel("Cannot parse date");
                    } catch (PitshopException e1) {
                        setErrorMessagePartInfoPanel("Error processing sales");
                    }
                } else {
                    setErrorMessagePartInfoPanel("No items to be sold");
                }
            }
        });
    }

    private void resetData() {
        name.setSelectedIndex(0);
        date.setText(Utility.formatDate(new Date()));
        remarks.setText("");
        salesType.setSelectedIndex(0);
        encoderBy.setSelectedIndex(0);

        clearAll();
        tableModel.clearData();
    }

    private void fillForm(int row) {
        if (row != -1) {
            PartsSalesBean bean = (PartsSalesBean) tableModel.getObject(row);
            this.partNumber.setEditable(false);
            this.partNumber.setText(bean.getPartNumber());
            this.description.setText(bean.getDescription());
            this.quantity.setText(String.valueOf(bean.getQuantity()));
            this.unitPrice.setText(String.valueOf(bean.getUnitPrice()));
            editButton.setEnabled(true);
        } else {
            clearAll();
        }
    }

    private SalesBean generateSalesBean() throws ParseException {
        SalesBean bean = new SalesBean();
        bean.setCustomerId(((CustomerBean)name.getSelectedItem()).getId());
        bean.setCustomer(name.getSelectedItem().toString());
        bean.setAddress(address.getText().trim());
        bean.setCreateDate(Utility.parseDate(date.getText()));
        bean.setPhone(phone.getText().trim());
        bean.setRemarks(remarks.getText().trim());
        bean.setSalesType(salesType.getSelectedItem().toString());
        bean.setMember("yes".equalsIgnoreCase(member.getSelectedItem().toString()));
        bean.setEncodedBy(encoderBy.getSelectedItem().toString());
        bean.setTotal(tableModel.getTotal());
        bean.setParts(tableModel.getData());
        return bean;
    }

    private void clearAll() {
        this.editButton.setEnabled(false);
        this.partNumber.setEditable(true);
        this.partNumber.setText("");
        this.description.setText("");
        this.quantity.setText("");
        this.unitPrice.setText("");
        this.discountLvl.setSelectedIndex(0);

    }

    private PartsSalesBean generatePartSalesBean() {
        PartsSalesBean bean = new PartsSalesBean();
        bean.setPartNumber(partNumber.getText().trim());
        bean.setDescription(description.getText());
        DiscountBean discount = (DiscountBean) discountLvl.getSelectedItem();
        bean.setDiscount(discount.getValue());
        bean.setQuantity(Integer.parseInt(quantity.getText().trim()));
        bean.setUnitPrice(Double.parseDouble(unitPrice.getText().trim()));

        return bean;
    }

    private boolean validateInput() {
        try {
            partsManager.getParts(partNumber.getText());
        } catch (PitshopException e1) {
            setErrorMessagePartInfoPanel(e1.getMessage());
            return false;
        }

        if (quantity.getText().length() == 0) {
            setErrorMessagePartInfoPanel("Enter quantity");
            return false;
        } else {
            try {
                Integer.parseInt(quantity.getText().trim());
            } catch (NumberFormatException nfe) {
                setErrorMessagePartInfoPanel("Invalid quantity");
                return false;
            }
        }

        if (unitPrice.getText().length() == 0) {
            setErrorMessagePartInfoPanel("Enter unit price");
            return false;
        } else {
            try {
                Double.parseDouble(unitPrice.getText().trim());
            } catch (NumberFormatException nfe) {
                setErrorMessagePartInfoPanel("Invalid unit price");
                return false;
            }
        }

        return true;
    }

    public void setFocusOnPart() {
        this.partNumber.requestFocus();
    }

    private CustomerBean generateCustomerBean() {
        CustomerBean bean = new CustomerBean();
        JTextField jtf = (JTextField) name.getEditor().getEditorComponent();
        bean.setName(jtf.getText());
        bean.setAddress(address.getText());
        bean.setPhoneNumber(phone.getText());
        bean.setMember(member.getSelectedIndex() == 0 ? false : true);

        return bean;
    }

    public void refreshData() {
        this.partNumber.requestFocus();

        try {
            java.util.List data = customerManager.getCustomers();
            Object o = name.getSelectedItem();
            updateComboBox(customerModel, data);
            if (o != null)
                name.setSelectedItem(o);

            CustomerBean customer = (CustomerBean) name.getSelectedItem();
            if (customer != null) {
                fillCustomerDetails(customer);
            }

            o = salesType.getSelectedItem();
            data = adminManager.getDatas("sales_type");
            updateComboBox(salesTypeModel, data);
            if (o != null)
                salesType.setSelectedItem(o);

            o = encoderBy.getSelectedItem();
            data = adminManager.getDatas("encoder");
            updateComboBox(encoderModel, data);
            if (o != null)
                encoderBy.setSelectedItem(o);

            o = discountLvl.getSelectedItem();
            data = discountManager.getDatas();
            DiscountBean noDiscount = new DiscountBean();
            noDiscount.setId(0);
            noDiscount.setName("No Discount");
            noDiscount.setValue(0);
            data.add(0, noDiscount);
            updateComboBox(discountLvlModel, data);
            if (o != null) {
                discountLvl.setSelectedItem(o);
            }
        } catch (PitshopException pe) {

        }
    }

    private void updateComboBox(DefaultComboBoxModel model, java.util.List data) {
        model.removeAllElements();
        Iterator iter = data.iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            if (o instanceof AdminCommonBean) {
                AdminCommonBean bean = (AdminCommonBean) o;
                model.addElement(bean);
            } else if (o instanceof CustomerBean) {
                CustomerBean bean = (CustomerBean) o;
                model.addElement(bean);
            } else if (o instanceof DiscountBean) {
                DiscountBean bean = (DiscountBean) o;
                model.addElement(bean);
            }
        }
    }

    private void fillCustomerDetails(CustomerBean bean) {
        address.setText(bean.getAddress());
        address.setEditable(false);
        phone.setText(bean.getPhoneNumber());
        phone.setEditable(false);
        member.setSelectedIndex(bean.isMember() ? 1 : 0);
        member.setEnabled(false);
        addCustomer.setEnabled(false);
    }

    private void enableCustomerDetails() {
        address.setText("");
        address.setEditable(true);
        phone.setText("");
        phone.setEditable(true);
        member.setSelectedIndex(0);
        member.setEnabled(true);
        addCustomer.setEnabled(true);
    }

    private void setErrorMessagePartInfoPanel(String message) {
        partsInfoPanel.setErrorText(message);
        partsInfoContainer.add(partsInfoPanel, BorderLayout.CENTER);
        partsInfoContainer.updateUI();
    }

    private void setSuccessMessagePartInfoPanel(String message) {
        partsInfoPanel.setSuccessText(message);
        partsInfoContainer.add(partsInfoPanel, BorderLayout.CENTER);
        partsInfoContainer.updateUI();
    }

    private void removePartInfoPanel() {
        partsInfoContainer.remove(partsInfoPanel);
        partsInfoContainer.updateUI();
    }

    class ComboBoxAutoCompleteMgr implements ActionListener {
        private JComboBox ManagedCB;

        public ComboBoxAutoCompleteMgr(JComboBox jcb) {
            ManagedCB = jcb;
            ManagedCB.setEditable(true);
            JTextField jtf = (JTextField) ManagedCB.getEditor().getEditorComponent();
            jtf.addActionListener(this);
            jtf.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent ev) {
                }   // Nil respose

                public void removeUpdate(DocumentEvent ev) {
                    enableCustomerDetails();
                }

                public void insertUpdate(DocumentEvent ev) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            findAutoCompleteText(((JTextComponent) ManagedCB.getEditor().getEditorComponent()).getText());
                        }
                    });
                }
            });
        }

        public void actionPerformed(ActionEvent ae) {
            JTextField jtf = (JTextField) ManagedCB.getEditor().getEditorComponent();
            String s = jtf.getText();
            jtf.getCaret().setDot(jtf.getText().length());
            for (int i = 0; i < ManagedCB.getItemCount(); i++) {
                Object o = ManagedCB.getItemAt(i);
                String sTemp = o.toString().toUpperCase();
                if (sTemp.equals(s.toUpperCase())) {
                    return;
                }
            }
        }

        public void findAutoCompleteText(String s) {
            for (int i = 0; i < ManagedCB.getItemCount(); i++) {
                Object o = ManagedCB.getItemAt(i);
                String sTemp = o.toString();
                String upper = sTemp.toUpperCase();
                // Don't do anything if the text exactly matches...
                if (upper.equals(s.toUpperCase())) {
                    CustomerBean customer = (CustomerBean) name.getSelectedItem();
                    if (customer != null) {
                        fillCustomerDetails(customer);
                    }
                    return;
                }

                if (upper.startsWith(s.toUpperCase())) {
                    ManagedCB.setSelectedIndex(i);
                    JTextComponent jtc = (JTextComponent) ManagedCB.getEditor().getEditorComponent();
                    // Insert the suggested text
                    jtc.setText(sTemp);
                    // Select the inserted text from the end to the current
                    // edit position.
                    Caret c = jtc.getCaret();
                    c.setDot(sTemp.length());
                    c.moveDot(s.length());
                    break;
                }
            }
            enableCustomerDetails();
        }
    }
}