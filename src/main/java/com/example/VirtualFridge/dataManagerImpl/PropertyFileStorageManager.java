package com.example.VirtualFridge.dataManagerImpl;

import com.example.VirtualFridge.dataManager.StorageManager;
import com.example.VirtualFridge.model.Grocery;
import com.example.VirtualFridge.model.User;

import java.util.Collection;
import java.util.LinkedList;

public class PropertyFileStorageManager implements StorageManager {
    public Collection<Grocery> getAllTasks(User user){
        return new LinkedList<Grocery>();
    }
    public void addGrocery(Grocery grocery, User user){

    }

}
