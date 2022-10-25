package com.example.VirtualFridge;

import com.example.VirtualFridge.dataManagerImpl.PostgresUserManager;
import com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager;
import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
import com.example.VirtualFridge.model.UserList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager.getPropertyFileUserManager;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1.0")
public class MappingController {

    @PostMapping(
            path = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody User user){
        getPropertyFileUserManager("src/main/resources/user.properties").addUser(user);
    }


    @GetMapping("/user/all")
    public UserList getUsers(//@RequestParam(value = "email", defaultValue = "none")
                             //String email
    ){

        UserList userList = new UserList();
        userList.setUsers();

        return userList;
    }

    @PostMapping(
            path = "/user/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createTask() {

        final PostgresUserManager postgresUserManager =
                PostgresUserManager.getPostgresUserManager();
        postgresUserManager.createTableUser();

        return "Database Table created";
    }


    @GetMapping("/user/Storage/all")
    public LinkedList<Storage> getUserStorages(){

        return new LinkedList<Storage>();
    }

    @DeleteMapping(
            path="/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
            })
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@RequestBody User user){
        UserList userList = new UserList();
        userList.setUsers();
        userList.deleteUser(user);
        getPropertyFileUserManager("src/main/resources/user.properties").storeAllUsers(userList.getUsers());

        return user.getEmail();
    }


}
