package com.ekattorit.messmanagement;

public class Meals {
    private String code;
    private String categoryCode;
    private String name;
    private double price;

    public Meals(String code, String categoryCode, String name, double price) {
        this.code = code;
        this.categoryCode = categoryCode;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
