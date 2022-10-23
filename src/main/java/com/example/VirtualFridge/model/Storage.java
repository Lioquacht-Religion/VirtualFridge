package com.example.VirtualFridge.model;

import java.util.Collection;
import java.util.LinkedList;

public class Storage {
    private String name = "Standard-Lager";
    private User Owner;
    private Collection Groceries = new LinkedList<Grocery>();

}
