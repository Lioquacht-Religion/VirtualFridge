package com.example.VirtualFridge;

import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
import com.example.VirtualFridge.model.UserList;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager.getPropertyFileUserManager;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1.0")
public class MappingController {

    @PostMapping
    public User createUser(
            @RequestParam(value = "email", defaultValue = "none")
            String email,
            @RequestParam(value = "password", defaultValue = "none")
            String password
    ){

        return new User("Bob Test" ,"test@mail.com", "pass");
    }


    @GetMapping("/user/all")
    public UserList getUsers(//@RequestParam(value = "email", defaultValue = "none")
                             //String email
    ){

        UserList userList = new UserList();
        userList.setUsers();

        return userList;
    }


    @GetMapping("/user/Storage/all")
    public LinkedList<Storage> getUserStorages(){

        return new LinkedList<Storage>();
    }


}
