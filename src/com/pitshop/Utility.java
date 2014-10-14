package com.pitshop;

import javax.swing.*;
import java.net.URL;
import java.util.Date;
import java.text.*;

public class Utility {
    private static DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
    private static NumberFormat nf = new DecimalFormat("#,##0.00");
    private Utility() {

    }

    public static ImageIcon readImageIcon(String filename) {
        URL url = Utility.class.getClassLoader().getResource("images/" + filename);
        return new ImageIcon(url);
    }

    public static AbstractButton createToolBarButton(String iconName) {
        JButton button = new JButton(Utility.readImageIcon(iconName));
        button.setFocusable(false);
        return button;
    }

    public static Date parseDate(String date) throws ParseException {
        return df.parse(date);
    }

    public static String formatDate(Date date) {
        if(date == null)
            return "";
        return df.format(date);
    }

    public static String formatCurrency(double amount) {
        return nf.format(amount);
    }
}
