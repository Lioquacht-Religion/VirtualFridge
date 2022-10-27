package com.example.VirtualFridge.model;

import java.util.Collection;
import java.util.LinkedList;

public class Storage {
    private String name = "Standard-Lager";
    private User Owner;
    private Collection<Grocery> Groceries = new LinkedList<Grocery>();

    Storage(String name, User Owner){
        this.name = name; this.Owner = Owner;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public User getOwner(){
        return Owner;
    }

    public Collection<Grocery> getGroceries() {
        return Groceries;
    }

    public void setGroceries() {
        ;
    }

}
