package com.example.VirtualFridge.model;

public class Grocery {
    private String name = "",
    unit = "";
    private int amount = 0;

    Grocery(String name, String unit, int amount){
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
