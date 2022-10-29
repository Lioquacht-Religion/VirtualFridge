package com.example.VirtualFridge.model;

public class Grocery {
    private String name = "",
    unit = "";
    private int amount = 0;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID = -1;

    public int getStoredInID() {
        return storedInID;
    }

    public void setStoredInID(int storedInID) {
        this.storedInID = storedInID;
    }

    private int storedInID = -1;


    public Grocery(String name, String unit, int amount){
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public void setIDs(int ID, int storedInID){
        this.ID = ID; this.storedInID = storedInID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }


}
