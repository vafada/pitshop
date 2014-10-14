package com.pitshop.bean;

import com.pitshop.Utility;

public class PartsSalesBean {
    private String partNumber;
    private String description;
    private double discount;
    private double unitPrice;
    private int quantity;


    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountedPrice() {
        if (discount == 0)
            return 0;
        else {
            double discountPrice = ((discount / 100) * getUnitPrice());
            return discountPrice;
        }
    }

    public double getNetPrice() {
        return (getUnitPrice() - getDiscountedPrice());
    }

    public double getSubtotal() {
        return (getNetPrice() * quantity);
    }
}