package com.pitshop.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bryu
 * Date: Sep 15, 2004
 * Time: 2:08:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SalesBean {
    private int id;
    private int customerId;
    private String customer;
    private String address;
    private Date createDate;
    private String phone;
    private String remarks;
    private String salesType;
    private boolean member;
    private String encodedBy;
    private double total;

    private List parts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public String getEncodedBy() {
        return encodedBy;
    }

    public void setEncodedBy(String encodedBy) {
        this.encodedBy = encodedBy;
    }

    public List getParts() {
        return parts;
    }

    public void setParts(List parts) {
        this.parts = parts;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
