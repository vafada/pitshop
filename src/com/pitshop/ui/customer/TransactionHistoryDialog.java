package com.pitshop.ui.customer;

import com.pitshop.bean.CustomerBean;
import com.pitshop.ui.SimpleInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;

import javax.swing.*;
import java.util.List;
import java.awt.*;

public class TransactionHistoryDialog extends JDialog {
    private List data;
    private CustomerBean customer;
    public TransactionHistoryDialog(CustomerBean customer, List data) {
        this.customer = customer;
        this.data = data;
        setModal(true);
        setTitle("Transaction History");

        initComponents();
        buildUI();
    }

    private void initComponents() {



    }

    private void buildUI() {
        getContentPane().setLayout(new BorderLayout());
        SimpleInternalFrame internalFrame = new SimpleInternalFrame("TRANSACTION HISTORY");

        FormLayout layout = new FormLayout("left:max(40dlu;pref), 5dlu, 120dlu, 15dlu, left:max(40dlu;pref), 5dlu, 120dlu, fill:p:grow",
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, fill:1dlu:grow, 5dlu, p");

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        int y = 1;

        builder.addSeparator("CUSTOMER DATA", cc.xyw(1, y, 8));

        y += 2;

        builder.addLabel("Name:", cc.xy(1, y));
        builder.add(new JLabel(customer.getName()), cc.xy(3, y));

        y += 2;

        builder.addLabel("Address:", cc.xy(1, y));
        builder.add(new JLabel(customer.getAddress()), cc.xy(3, y));

        y += 2;

        builder.addLabel("Phone Number:", cc.xy(1, y));
        builder.add(new JLabel(customer.getPhoneNumber()), cc.xy(3, y));

        y += 2;

        builder.addLabel("Member:", cc.xy(1, y));
        builder.add(new JLabel(customer.isMember() ? "Yes" : "No"), cc.xy(3, y));

        y += 2;

        builder.addSeparator("ACCUMULATED TRANSACTIONS", cc.xyw(1, y, 8));

        y += 2;

        SimpleInternalFrame tableInternalFrame = new SimpleInternalFrame("TRANSACTION LISTING");

        JTable table = new JTable(new TransactionHistoryTableModel(data));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableInternalFrame.setContent(new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        builder.add(tableInternalFrame, cc.xyw(1, y, 8));
        internalFrame.setContent(builder.getPanel());

        getContentPane().add(internalFrame, BorderLayout.CENTER);

        pack();
        Dimension screenSize = getToolkit().getScreenSize();
        setSize((int) (screenSize.getWidth() * .80), (int) (screenSize.getHeight() * .80));
        locateOnScreen(this);
        setVisible(true);
    }

    protected void locateOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation((screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
    }
}
