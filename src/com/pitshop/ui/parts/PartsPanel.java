package com.pitshop.ui.parts;

import com.pitshop.ui.SimpleInternalFrame;
import com.pitshop.ui.PagingTableModel;
import com.pitshop.ui.InfoPanel;
import com.pitshop.manager.AdminCommonManager;
import com.pitshop.PitshopException;
import com.pitshop.bean.AdminCommonBean;
import com.pitshop.bean.PartsBean;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.ButtonBarBuilder;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class PartsPanel extends JPanel {
    public static final int NEW = 1;
    public static final int EDIT = 2;

    protected int action = NEW;

    private JTextField partNumber;
    private JTextField description;
    private JTextField quantity;
    private JTextField srp;

    private JComboBox manufacturer;
    private JComboBox category;
    private JComboBox source;
    //private JComboBox discountLvl;

    private DefaultComboBoxModel manufacturerModel;
    private DefaultComboBoxModel categoryModel;
    private DefaultComboBoxModel sourceModel;
    //private DefaultComboBoxModel discountLvlModel;

    private JTextField maxLevel;
    private JTextField reorderLevel;

    private JButton newButton;
    private JButton addEditButton;
    private JButton deleteButton;

    private PartsTableModel tableModel;
    private JTable inventoryTable;
    private JLabel page;

    private JPanel buttonPanel;
    private InfoPanel infoPanel;

    private AdminCommonManager adminManager;

    public PartsPanel() {
        this.adminManager = new AdminCommonManager();
        initComponents();
        buildUI();
    }

    private void initComponents() {
        partNumber = new JTextField(30);
        description = new JTextField(30);
        quantity = new JTextField(30);
        srp = new JTextField(30);


        manufacturerModel = new DefaultComboBoxModel();
        manufacturer = new JComboBox(manufacturerModel);
        categoryModel = new DefaultComboBoxModel();
        category = new JComboBox(categoryModel);
        sourceModel = new DefaultComboBoxModel();
        source = new JComboBox(sourceModel);
        /*discountLvlModel = new DefaultComboBoxModel();
        discountLvl = new JComboBox(discountLvlModel);*/

        maxLevel = new JTextField(30);
        reorderLevel = new JTextField(30);

        newButton = new JButton("New");
        addEditButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        page = new JLabel("page 0 / 0");
        tableModel = new PartsTableModel(page);
        inventoryTable = new JTable(tableModel);

        inventoryTable.getTableHeader().setReorderingAllowed(false);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        infoPanel = new InfoPanel(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        SimpleInternalFrame internalFrame = new SimpleInternalFrame("PARTS & INVENTORY");

        FormLayout layout = new FormLayout("left:max(40dlu;pref), 5dlu, 120dlu, 15dlu, left:max(40dlu;pref), 5dlu, 120dlu, fill:p:grow",
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p, 5dlu, fill:1dlu:grow");

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        int y = 1;

        builder.addSeparator("PARTS INPUT", cc.xyw(1, y, 8));

        y += 2;

        builder.addLabel("Part Number:", cc.xy(1, y));
        builder.add(partNumber, cc.xy(3, y));
        builder.addLabel("Manufacturer:", cc.xy(5, y));
        builder.add(manufacturer, cc.xy(7, y));

        y += 2;

        builder.addLabel("Description:", cc.xy(1, y));
        builder.add(description, cc.xy(3, y));
        builder.addLabel("Category:", cc.xy(5, y));
        builder.add(category, cc.xy(7, y));

        y += 2;

        builder.addLabel("Quantity:", cc.xy(1, y));
        builder.add(quantity, cc.xy(3, y));
        builder.addLabel("Source:", cc.xy(5, y));
        builder.add(source, cc.xy(7, y));

        y += 2;

        builder.addLabel("SRP:", cc.xy(1, y));
        builder.add(srp, cc.xy(3, y));
        builder.addLabel("Reorder Level:", cc.xy(5, y));
        builder.add(reorderLevel, cc.xy(7, y));
        /*builder.addLabel("Discount Level:", cc.xy(5, y));
        builder.add(discountLvl, cc.xy(7, y));*/

        y += 2;

        builder.addLabel("Maximum Level:", cc.xy(1, y));
        builder.add(maxLevel, cc.xy(3, y));


        y += 2;

        ButtonBarBuilder bbuilder = new ButtonBarBuilder();
        bbuilder.addGriddedButtons(new JButton[]{newButton, addEditButton, deleteButton});
        JPanel tempPanel = bbuilder.getPanel();

        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(tempPanel, BorderLayout.WEST);

        builder.add(buttonPanel, cc.xyw(1, y, 7));

        y += 2;

        builder.addSeparator("INVENTORY DATABASE", cc.xyw(1, y, 8));

        y += 2;

        SimpleInternalFrame tableInternalFrame = new SimpleInternalFrame("Inventory Database");

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(inventoryTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
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

        addEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = inventoryTable.getSelectedRow();
                if (validateInput()) {
                    PartsBean bean = generatePartsBean();
                    if (action == NEW) {
                        try {
                            tableModel.addParts(bean);
                            clearAll();
                            setSuccessMessage("Added part");
                        } catch (PitshopException pe) {
                            setErrorMessage(pe.getMessage());
                        }
                    } else if (action == EDIT && row != -1) {
                        try {
                            tableModel.updateParts(row, bean);
                            setSuccessMessage("Updated parts");
                        } catch (PitshopException pe) {
                            setErrorMessage(pe.getMessage());
                        }
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = inventoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        tableModel.deleteParts(selectedRow);
                        clearAll();
                        setSuccessMessage("Deleted part");
                    } catch (PitshopException pe) {
                        setErrorMessage(pe.getMessage());
                    }
                }
            }
        });

        inventoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                if (e.getSource() == inventoryTable.getSelectionModel()) {
                    int row = inventoryTable.getSelectedRow();
                    fillForm(row);
                    action = EDIT;
                }
            }
        });


        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK), "focus");
        this.getActionMap().put("focus", new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                setFocusOnPart();
            }
        });
    }

    private void fillForm(int row) {
        if (row != -1) {
            PartsBean bean = (PartsBean) tableModel.getObject(row);
            action = EDIT;
            this.partNumber.setEditable(false);
            this.partNumber.setText(bean.getNumber());
            this.description.setText(bean.getDescription());
            this.quantity.setText(String.valueOf(bean.getQuantity()));
            this.srp.setText(String.valueOf(bean.getSrp()));
            this.maxLevel.setText(bean.getMaximumLevel());
            this.reorderLevel.setText(bean.getReorderLevel());
            this.manufacturer.setSelectedItem(bean.getManufacturer());
            this.category.setSelectedItem(bean.getCategory());
            this.source.setSelectedItem(bean.getSource());
            /*this.discountLvl.setSelectedItem(bean.getDiscount());*/
            this.addEditButton.setText("Update");
        } else {
            clearAll();
        }
    }

    private void clearAll() {
        action = NEW;
        this.partNumber.setEditable(true);
        this.partNumber.setText("");
        this.description.setText("");
        this.quantity.setText("");
        this.srp.setText("");
        this.maxLevel.setText("");
        this.reorderLevel.setText("");

        this.manufacturer.setSelectedIndex(0);
        this.source.setSelectedIndex(0);
        this.category.setSelectedIndex(0);
        /*this.discountLvl.setSelectedIndex(0);*/

        this.addEditButton.setText("Add");
    }

    public void setFocusOnPart() {
        this.partNumber.requestFocus();
    }

    private boolean validateInput() {
        if (partNumber.getText().trim().length() == 0) {
            setErrorMessage("Enter part number");
            return false;
        } else if (partNumber.getText().trim().length() > 50) {
            setErrorMessage("Name must be less than 50 characters");
            return false;
        }

        if (description.getText().trim().length() == 0) {
            setErrorMessage("Enter description");
            return false;
        } else if (description.getText().trim().length() > 100) {
            setErrorMessage("Description must be less than 100 characters");
            return false;
        }

        if (quantity.getText().trim().length() == 0) {
            setErrorMessage("Enter quantity");
            return false;
        } else {
            try {
                Integer.parseInt(quantity.getText().trim());
            } catch (NumberFormatException npe) {
                setErrorMessage("Invalid quantity");
                return false;
            }
        }

        if (srp.getText().trim().length() == 0) {
            setErrorMessage("Enter SRP");
            return false;
        } else {
            try {
                Double.parseDouble(srp.getText().trim());
            } catch (NumberFormatException npe) {
                setErrorMessage("Invalid SRP");
                return false;
            }
        }

        if (maxLevel.getText().trim().length() > 20) {
            setErrorMessage("Max level must be less than 20 characters");
            return false;
        }

        if (reorderLevel.getText().trim().length() > 20) {
            setErrorMessage("Reorder level must be less than 20 characters");
            return false;
        }

        if (manufacturer.getSelectedIndex() == -1) {
            setErrorMessage("Invalid manufacturer");
            return false;
        }

        if (category.getSelectedIndex() == -1) {
            setErrorMessage("Invalid category");
            return false;
        }

        if (source.getSelectedIndex() == -1) {
            setErrorMessage("Invalid source");
            return false;
        }
/*
        if (discountLvl.getSelectedIndex() == -1) {
            setErrorMessage("Invalid discount level");
            return false;
        }*/


        return true;
    }

    private PartsBean generatePartsBean() {
        PartsBean bean = new PartsBean();
        bean.setNumber(partNumber.getText().trim());
        bean.setDescription(description.getText().trim());
        bean.setQuantity(Integer.parseInt(quantity.getText().trim()));
        bean.setSrp(Double.parseDouble(srp.getText().trim()));
        bean.setMaximumLevel(maxLevel.getText().trim());
        bean.setReorderLevel(reorderLevel.getText().trim());
        AdminCommonBean common = (AdminCommonBean) manufacturerModel.getSelectedItem();
        bean.getManufacturer().setId(common.getId());
        bean.getManufacturer().setName(common.getName());
        common = (AdminCommonBean) category.getSelectedItem();
        bean.getCategory().setId(common.getId());
        bean.getCategory().setName(common.getName());
        common = (AdminCommonBean) source.getSelectedItem();
        bean.getSource().setId(common.getId());
        bean.getSource().setName(common.getName());
//        common = (AdminCommonBean) discountLvl.getSelectedItem();
        /*bean.getDiscount().setId(common.getId());
        bean.getDiscount().setName(common.getName());*/

        return bean;
    }

    public void refreshData() {
        this.partNumber.requestFocus();


        try {
            java.util.List data = adminManager.getDatas("manufacturer");
            Object o = manufacturer.getSelectedItem();
            updateComboBox(manufacturerModel, data);
            if (o != null)
                manufacturer.setSelectedItem(o);

            o = category.getSelectedItem();
            data = adminManager.getDatas("category");
            updateComboBox(categoryModel, data);
            if (o != null)
                category.setSelectedItem(o);

            o = source.getSelectedItem();
            data = adminManager.getDatas("source");
            updateComboBox(sourceModel, data);
            if (o != null)
                source.setSelectedItem(o);

            /*o = discountLvl.getSelectedItem();
            data = adminManager.getDatas("discount");
            updateComboBox(discountLvlModel, data);
            if (o != null)
                discountLvl.setSelectedItem(o);*/
        } catch (PitshopException pe) {

        }

        try {
            tableModel.initData();
        } catch (PitshopException pe) {
            setErrorMessage(pe.getMessage());
        }
    }

    private void updateComboBox(DefaultComboBoxModel model, java.util.List data) {
        model.removeAllElements();
        Iterator iter = data.iterator();
        while (iter.hasNext()) {
            AdminCommonBean bean = (AdminCommonBean) iter.next();
            model.addElement(bean);
        }
    }

    public void removeInfoPanel() {
        buttonPanel.remove(infoPanel);
        buttonPanel.updateUI();
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
}
