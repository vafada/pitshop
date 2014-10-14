package com.pitshop.ui.admin;

import com.pitshop.ui.SimpleInternalFrame;
import com.pitshop.ui.PagingTableModel;
import com.pitshop.PitshopException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPanel extends JPanel implements ActionListener {
    private static final String[] types = {"Category", "Discount Level", "Encoder", "Manufacturer", "Sales type", "Source"};

    private SimpleInternalFrame internalFrame;
    private JTable table;
    private PagingTableModel tableModel;
    private JLabel page;
    private AdminCommonPanel bottomCommonPanel;
    private DiscountPanel discountPanel;
    private JComboBox typesCombo;
    private JPanel dummyPanel;
    private int currentTypeIndex = 0;

    public AdminPanel() {
        initComponents();
        buildUI();
    }

    private void initComponents() {
        internalFrame = new SimpleInternalFrame("ADMINISTRATION - Category");
        page = new JLabel("page 0 / 0");
        typesCombo = new JComboBox(types);
        typesCombo.addActionListener(this);
        dummyPanel = new JPanel(new BorderLayout());
        tableModel = new AdminCommonTableModel(page, "category", "Category");
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bottomCommonPanel = new AdminCommonPanel(table);

        try {
            tableModel.initData();
        } catch (PitshopException e) {
            setErrorMessage(e.getMessage());
        }
    }

    private void setErrorMessage(String message) {
        ((AdminCommonPanel) bottomCommonPanel).setErrorMessage(message);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Type:"));
        topPanel.add(typesCombo);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        JPanel pagingPanel = new JPanel(new BorderLayout());
        JButton prev = new JButton("< prev");
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((PagingTableModel) tableModel).prev();
            }
        });
        JButton next = new JButton("next >");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((PagingTableModel) tableModel).next();
            }
        });

        pagingPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pagingPanel.add(prev, BorderLayout.WEST);
        pagingPanel.add(next, BorderLayout.EAST);
        pagingPanel.add(page, BorderLayout.CENTER);
        page.setHorizontalAlignment(JLabel.CENTER);
        tablePanel.add(pagingPanel, BorderLayout.SOUTH);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        dummyPanel.add(bottomCommonPanel, BorderLayout.WEST);
        mainPanel.add(dummyPanel, BorderLayout.SOUTH);

        internalFrame.setContent(mainPanel);
        add(internalFrame, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                if (e.getSource() == table.getSelectionModel()) {
                    int row = table.getSelectedRow();
                    if (currentTypeIndex != 1) {
                        bottomCommonPanel.fillForm(row);
                        bottomCommonPanel.action = AdminCommonPanel.EDIT;
                    } else {
                        discountPanel.fillForm(row);
                        discountPanel.action = AdminCommonPanel.EDIT;
                    }
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        int selectedIndex = typesCombo.getSelectedIndex();
        if (currentTypeIndex != selectedIndex) {
            currentTypeIndex = selectedIndex;
            switch (currentTypeIndex) {
                case 0: //category
                    internalFrame.setTitle("ADMINISTRATOR - Category");
                    tableModel = new AdminCommonTableModel(page, "category", "Category");
                    bottomCommonPanel = new AdminCommonPanel(table);
                    break;
                case 1: //discount
                    internalFrame.setTitle("ADMINISTRATOR - Discount");
                    tableModel = new DiscountTableModel(page);
                    discountPanel = new DiscountPanel(table);
                    break;
                case 2: //encoder
                    internalFrame.setTitle("ADMINISTRATOR - Encoder");
                    tableModel = new AdminCommonTableModel(page, "encoder", "Encoder");
                    bottomCommonPanel = new AdminCommonPanel(table);
                    break;
                case 3: //manufacturer
                    internalFrame.setTitle("ADMINISTRATOR - Manufacturer");
                    tableModel = new AdminCommonTableModel(page, "manufacturer", "Manufacturer");
                    bottomCommonPanel = new AdminCommonPanel(table);
                    break;
                case 4: //sales type
                    internalFrame.setTitle("ADMINISTRATOR - Sales type");
                    tableModel = new AdminCommonTableModel(page, "sales_type", "Sales type");
                    bottomCommonPanel = new AdminCommonPanel(table);
                    break;
                case 5: //source
                    internalFrame.setTitle("ADMINISTRATOR - Source");
                    tableModel = new AdminCommonTableModel(page, "source", "Source");
                    bottomCommonPanel = new AdminCommonPanel(table);
                    break;
            }

            try {
                ((PagingTableModel) tableModel).initData();
            } catch (PitshopException pe) {
                setErrorMessage(pe.getMessage());
            }

            table.setModel(tableModel);
            dummyPanel.removeAll();
            if (currentTypeIndex != 1)
                dummyPanel.add(bottomCommonPanel, BorderLayout.WEST);
            else
                dummyPanel.add(discountPanel, BorderLayout.WEST);

            dummyPanel.updateUI();

        }
    }
}
