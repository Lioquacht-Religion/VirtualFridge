package com.example.VirtualFridge;

import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

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

        return new User("test@mail.com", "pass");
    }


    @GetMapping("/user")
    public User getUser(@RequestParam(value = "email", defaultValue = "none")
                            String email){

        return new User("test@mail.com", "pass");
    }

    @GetMapping("/user/Storage/all")
    public LinkedList<Storage> getUserStorages(){

        return new LinkedList<Storage>();
    }


}
