package com.pitshop;

import com.pitshop.ui.customer.CustomerPanel;
import com.pitshop.ui.parts.PartsPanel;
import com.pitshop.ui.admin.AdminPanel;
import com.pitshop.ui.sales.SalesPanel;
import com.pitshop.db.McKoiConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.logging.Logger;

public class MainFrame extends JFrame {
    private static Logger logger = Logger.getLogger("com.pitshop.MainFrame");

    private final String ADMIN_PANEL = "Admin Panel";
    private final String CUSTOMER_PANEL = "Customer Panel";
    private final String PARTS_PANEL = "Parts Panel";
    private final String SALES_PANEL = "Sales Panel";

    private JPanel cardPanel;

    private CustomerPanel customerPanel;
    private PartsPanel partsPanel;
    private AdminPanel adminPanel;
    private SalesPanel salesPanel;

    public MainFrame() {
        initDB();
        buildUI();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                closeDB();
                System.exit(0);
            }
        });
    }

    private void closeDB() {
        McKoiConnection.getInstance().close();
    }

    private void initDB() {
        try {
            McKoiConnection.getInstance().initialize();
        } catch (PitshopDbConnectionException pe) {
            pe.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    pe.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void buildUI() {
        buildMenuBar();
        buildToolBar();
        buildMainPanel();
    }

    private void buildMainPanel() {
        cardPanel = new JPanel(new CardLayout());

        customerPanel = new CustomerPanel();
        cardPanel.add(customerPanel, CUSTOMER_PANEL);
        salesPanel = new SalesPanel(this);
        cardPanel.add(salesPanel, SALES_PANEL);
        partsPanel = new PartsPanel();
        cardPanel.add(partsPanel, PARTS_PANEL);
        adminPanel = new AdminPanel();
        cardPanel.add(adminPanel, ADMIN_PANEL);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

    }

    private void buildToolBar() {
        JPanel panel = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

        AbstractButton button = Utility.createToolBarButton("customer.png");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, CUSTOMER_PANEL);
                //todo.. refresh list
            }
        });
        toolBar.add(button);
        button = Utility.createToolBarButton("sales.png");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, SALES_PANEL);
                salesPanel.refreshData();
            }
        });
        toolBar.add(button);
        button = Utility.createToolBarButton("inventory.png");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, PARTS_PANEL);
                partsPanel.refreshData();
            }
        });
        toolBar.add(button);
        button = Utility.createToolBarButton("admin.png");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, ADMIN_PANEL);
            }
        });
        toolBar.add(button);

        panel.add(toolBar, BorderLayout.CENTER);
        panel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.NORTH);
    }

    private void buildMenuBar() {
        JMenuBar menubar = new JMenuBar();

        //File menu
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        JMenuItem menuitem = new JMenuItem("Exit");
        menuitem.setMnemonic('x');
        menuitem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                closeDB();
                System.exit(0);
            }
        });
        menu.add(menuitem);
        menubar.add(menu);

        //Help menu
        menu = new JMenu("Help");
        menu.setMnemonic('H');
        menuitem = new JMenuItem("About");
        menuitem.setMnemonic('A');
        menu.add(menuitem);
        menubar.add(menu);

        setJMenuBar(menubar);
    }

    /**
     * Locates the given component on the screen's center.
     */
    protected void locateOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation((screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            logger.warning("Cannot set LAF");
        }

        MainFrame instance = new MainFrame();

        instance.setTitle("Pitshop Cafe and Radio Control Shop");
        instance.setIconImage(Utility.readImageIcon("icon.png").getImage());
        instance.pack();
        Dimension screenSize = instance.getToolkit().getScreenSize();
        instance.setSize((int) (screenSize.getWidth() * .80), (int) (screenSize.getHeight() * .80));
        instance.locateOnScreen(instance);
        instance.setVisible(true);
    }
}
