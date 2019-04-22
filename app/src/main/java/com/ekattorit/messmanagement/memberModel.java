package com.ekattorit.messmanagement;

public class memberModel {
    String name,number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public memberModel(String name, String number) {
        this.name = name;
        this.number = number;

    }
}
