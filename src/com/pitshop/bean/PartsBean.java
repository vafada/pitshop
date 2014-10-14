package com.pitshop.bean;

public class PartsBean {
    private String number;
    private String description;
    private int quantity;
    private double srp;
    private String maximumLevel;
    private String reorderLevel;

    private AdminCommonBean manufacturer;
    private AdminCommonBean category;
    private AdminCommonBean source;

    public PartsBean() {
        manufacturer = new AdminCommonBean();
        category = new AdminCommonBean();
        source = new AdminCommonBean();
    }

    public AdminCommonBean getCategory() {
        return category;
    }

    public void setCategory(AdminCommonBean category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdminCommonBean getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(AdminCommonBean manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMaximumLevel() {
        return maximumLevel;
    }

    public void setMaximumLevel(String maximumLevel) {
        this.maximumLevel = maximumLevel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(String reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public AdminCommonBean getSource() {
        return source;
    }

    public void setSource(AdminCommonBean source) {
        this.source = source;
    }

    public double getSrp() {
        return srp;
    }

    public void setSrp(double srp) {
        this.srp = srp;
    }

    public boolean equals(Object obj) {
        if(obj instanceof PartsBean) {
            PartsBean o = (PartsBean) obj;
            if(o.getNumber().equals(number))
                return true;
            return false;
        } else {
            return false;
        }
    }

}
