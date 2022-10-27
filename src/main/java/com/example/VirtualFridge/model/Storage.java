package com.example.VirtualFridge.model;

import java.util.Collection;
import java.util.LinkedList;

public class Storage {
    private String name = "Standard-Lager";
    private User Owner;
    private Collection<Grocery> Groceries = new LinkedList<Grocery>();

    public Collection<Grocery> getGroceries() {
        return Groceries;
    }

    public void setGroceries() {
        ;
    }

    Storage(String name, User Owner){
        this.name = name; this.Owner = Owner;
    }

}
