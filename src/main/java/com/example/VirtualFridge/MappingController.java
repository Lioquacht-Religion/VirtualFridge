package com.example.VirtualFridge;

import com.example.VirtualFridge.dataManagerImpl.PostgresUserManager;
import com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager;
import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
import com.example.VirtualFridge.model.UserList;
import com.example.VirtualFridge.model.alexa.OutputSpeechRO;
import com.example.VirtualFridge.model.alexa.ResponseRO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.VirtualFridge.model.alexa.AlexaRO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    @GetMapping("/user/email"
    )
    public Collection<User> getUser(@RequestParam String email
    ){
        return getPostgresUserManager().getUser("email", email);
    }

    //TODO: Fix
    @PutMapping(path= "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public String putUser(@RequestBody User user
    ){
        getPostgresUserManager().putUser(user);
        return "updated User: " + user.getEmail();
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

    @PostMapping(
            path = "/storage/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createStorageTable() {
        Logger.getLogger("Test").log(Level.INFO, "Start Post create Table");

        final PostgresUserManager postgresUserManager =
                getPostgresUserManager();
        postgresUserManager.createTableStorage();

        return "Database Storage Table created";
    }


    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO getTasks(@RequestBody AlexaRO alexaRO) {

        if(alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")){
            return prepareResponse(alexaRO, "Welcome to the Virtual Fridge", false);
        }

        if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskReadIntent"))){
            StringBuilder outText  = new StringBuilder("");
        //TODO: UserList zu passender Grocery Liste ändern
            /*try {
                Storage storage = new Storage(getPostgresUserManager().getUser("email", "klaus@mail.com")));
                storage.setGroceries();
                AtomicInteger i = new AtomicInteger(0);
                storage.getGroceries().forEach(
                        groceries -> {
                            outText.append("Task number " + i.incrementAndGet() + "is: ");
                            outText.append(groceries.getName() + " and has priority " + groceries.getUnit() + ". " + groceries.getAount());
                        }
                );
                outText.append("Thank you for using our service");
            }
            catch (Exception e){
                outText.append("Unfortunately, we cannot reach heroku. Our REST server is not responding");
            }
*/
            return
                    prepareResponse(alexaRO, outText.toString(), true);
        }
        return prepareResponse(alexaRO, "We could not help you", true);


        //String outText = "";


        //return alexaRO;
    }

    private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {

        alexaRO.setRequest(null);
        alexaRO.setSession(null);
        alexaRO.setContext(null);
        OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
        outputSpeechRO.setType("PlainText");
        outputSpeechRO.setText(outText);
        ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
        alexaRO.setResponse(response);
        return alexaRO;
    }

}
