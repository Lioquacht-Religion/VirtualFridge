package com.example.VirtualFridge;

import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
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
    public Collection<User> getUsers(//@RequestParam(value = "email", defaultValue = "none")
                            //String email
    ){

        return getPropertyFileUserManager("src/main/resources/user.properties").getAllUsers();
    }


    @GetMapping("/user/Storage/all")
    public LinkedList<Storage> getUserStorages(){

        return new LinkedList<Storage>();
    }


}
