package com.example.VirtualFridge.dataManager;

import com.example.VirtualFridge.model.Grocery;
import com.example.VirtualFridge.model.User;

import java.util.Collection;

public interface StorageManager {
    Collection<Grocery> getAllTasks(User user);
    void addGrocery(Grocery grocery, User user);

    // TODO
    // removeTask, getTasksInOrder, getTaskByTaskID, ...

    // TODO
    // Make the TaskManager handling students.
}
