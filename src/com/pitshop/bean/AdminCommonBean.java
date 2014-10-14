package com.pitshop.bean;

public class AdminCommonBean {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object obj) {
        if(obj instanceof AdminCommonBean) {
            AdminCommonBean o = (AdminCommonBean) obj;
            if(name.equals(o.getName()) && o.getId() == id)
                return true;
            return false;
        } else {
            return false;
        }
    }
}
