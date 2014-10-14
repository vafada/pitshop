package com.pitshop.bean;

public class DiscountBean {
	private int id;
	private String name;
	private int value;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

    public String toString() {
        return name;
    }

}
