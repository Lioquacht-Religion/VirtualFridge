package com.example.VirtualFridge.dataManagerImpl;

import com.example.VirtualFridge.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PropertyFileUserManager {
    String fileName;

    static PropertyFileUserManager propertyFileUserManager = null;

    private PropertyFileUserManager(String fileName) {
        this.fileName = fileName;
    }

    static public PropertyFileUserManager getPropertyFileUserManager(String fileName) {
        if (propertyFileUserManager == null)
            propertyFileUserManager = new PropertyFileUserManager(fileName);
        return propertyFileUserManager;
    }

    //@Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        Properties properties = new Properties();

        int i = 1;

        try {
            properties.load(new FileInputStream(fileName));

            while(properties.containsKey("User." + i + ".name")) {
                users.add(
                        new User(
                                properties.getProperty("User." + i + ".name"),
                                properties.getProperty("User." + i + ".email"),
                                properties.getProperty("User." + i + ".password") ));

                i++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return users;
    }
}
