package com.pitshop.bean;

public class CustomerBean {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private boolean member;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean equals(Object obj) {
        if(obj instanceof CustomerBean) {
            CustomerBean o = (CustomerBean) obj;
            if(o.getId() == id)
                return true;
            return false;
        } else {
            return false;
        }
    }

    public String toString() {
        return name;
    }
}
