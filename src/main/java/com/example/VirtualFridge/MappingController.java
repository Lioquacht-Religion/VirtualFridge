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
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.VirtualFridge.dataManagerImpl.PostgresUserManager.getPostgresUserManager;
import static com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager.getPropertyFileUserManager;

//TODO: CHange all propertyfilemanager zu postgres

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1.0")
public class MappingController {

    @PostMapping(
            path = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public String createUser(@RequestBody User user){
        //getPropertyFileUserManager("src/main/resources/user.properties").addUser(user);
        getPostgresUserManager().addUser(user);
        return "posted user with email " + user.getEmail();
    }


    @GetMapping("/user/all")
    public Collection<User> getUsers(//@RequestParam(value = "email", defaultValue = "none")
                             //String email
    ){

        //UserList userList = new UserList();
        //userList.setUsers();

        return getPostgresUserManager().getAllUsers();
        //return userList;
    }

    @GetMapping(
            path= "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Collection<User> getUser(@RequestBody User user
    ){
        return getPostgresUserManager().getUser(user);
    }

    @PutMapping("/user")
    public Collection<User> putUser(@RequestBody User user
    ){
        return getPostgresUserManager().getUser(user);
    }

    @PostMapping(
            path = "/user/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createUserTable() {
        Logger.getLogger("Test").log(Level.INFO, "Start Post create Table");

        final PostgresUserManager postgresUserManager =
                getPostgresUserManager();
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
        //UserList userList = new UserList();
        //userList.setUsers();
        //userList.deleteUser(user);
        //getPropertyFileUserManager("src/main/resources/user.properties").storeAllUsers(userList.getUsers());
        getPostgresUserManager().deleteUser(user);

        return user.getEmail();
    }


}
