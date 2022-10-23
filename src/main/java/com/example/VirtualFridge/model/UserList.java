package com.example.VirtualFridge.model;

import com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager;

import java.util.Collection;

public class UserList {
    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers() {
        PropertyFileUserManager UserManager = PropertyFileUserManager.getPropertyFileUserManager("src/main/resources/user.properties");
        //TaskManager taskManager = PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
        users = UserManager.getAllUsers();
    }
}
