package com.example.VirtualFridge;

import com.example.VirtualFridge.dataManagerImpl.PostgresUserManager;
import com.example.VirtualFridge.dataManagerImpl.PropertyFileUserManager;
import com.example.VirtualFridge.model.Grocery;
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
import static java.lang.Integer.parseInt;

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
    public User getUser(@RequestParam String email
    ){
        return getPostgresUserManager().getUser("email", email);
    }

    //TODO: Fix
    @PutMapping(path= "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
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


    @GetMapping("/user/storage/all")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Storage> getUserStorages(@RequestParam String OwnerID){

        return getPostgresUserManager().getStorages(parseInt(OwnerID));
    }

    @GetMapping("/storage")
    @ResponseStatus(HttpStatus.OK)
    public Storage getStorage(@RequestParam String storName, @RequestParam String email){

        return getPostgresUserManager().getStorage(storName,
                getPostgresUserManager().getUser("email", email));
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

    @DeleteMapping(path="/storage")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStorage(@RequestParam int userID, @RequestParam int storageID){
        return getPostgresUserManager().deleteStorage(userID, storageID);
    }

    @DeleteMapping(path="/grocery")
    @ResponseStatus(HttpStatus.OK)
    public String deleteGrocery(@RequestParam int storageID, @RequestParam int groceryID){
        return getPostgresUserManager().deleteGrocery(storageID, groceryID);
    }

    @PostMapping(
            path = "/storage",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public String createStorage(@RequestBody Storage storage){
        //getPropertyFileUserManager("src/main/resources/user.properties").addUser(user);
        getPostgresUserManager().addStorage(storage);
        return "posted storage: " + storage.getName();
    }

    @PostMapping(
            path = "/storage/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createStorageTable() {
        //Logger.getLogger("Test").log(Level.INFO, "Start Post create Table");

        final PostgresUserManager postgresUserManager =
                getPostgresUserManager();
        postgresUserManager.createTableStorage();

        return "Database Storage Table created";
    }

    @PostMapping(
            path = "/grocery",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public String createGrocery(@RequestParam String storName,
                                @RequestParam String ownerEmail,
                                @RequestBody Grocery grocery){
        //getPropertyFileUserManager("src/main/resources/user.properties").addUser(user);
        final PostgresUserManager PostgresManager = getPostgresUserManager();
        User owner = PostgresManager.getUser("email", ownerEmail);
        Storage storage = PostgresManager.getStorage(storName, owner);
        getPostgresUserManager().addGrocery(storage, grocery);
        return "posted grocery: " + grocery.getName() + "into Storage: " + storage.getName();
    }

    @GetMapping("/user/storage/grocery/all"
    )
    public Collection<Grocery> getStorageGroceries(@RequestParam String storName,
                                    @RequestParam String ownerEmail
    ){

        final PostgresUserManager PostgresManager = getPostgresUserManager();
        User owner = PostgresManager.getUser("email", ownerEmail);
        Storage storage = PostgresManager.getStorage(storName, owner);
        return PostgresManager.getGroceries(storage.getStorageID());
    }

    @PostMapping(
            path = "/groceries/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createGroceriesTable() {
        //Logger.getLogger("Test").log(Level.INFO, "Start Post create Table");

        final PostgresUserManager postgresUserManager =
                getPostgresUserManager();
        postgresUserManager.createTableGroceries();

        return "Database Groceries Table created";
    }




    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO getGroceries(@RequestBody AlexaRO alexaRO) {

        if(alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")){
            return prepareResponse(alexaRO, "Welcome to the Virtual Fridge", false);
        }

        if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("ReadGroceriesIntent"))){
            StringBuilder outText  = new StringBuilder("");
        //TODO: UserList zu passender Grocery Liste ändern

            try {
                Storage storage = getPostgresUserManager().getStorage("Lager1",
                        getPostgresUserManager().getUser("email", "klaus@mail.com"));
                storage.setIDs(9, 1);
                storage.setGroceries();
                //AtomicInteger i = new AtomicInteger(0);
                storage.getGroceries().forEach(
                        groceries -> {
                            outText.append(" Storage contains: ");
                            outText.append(groceries.getName() + " with the amount: " +
                                    groceries.getAmount() + " " + groceries.getUnit());
                        }
                );
                outText.append("Thank you for using our service");
            }
            catch (Exception e){
                outText.append("Unfortunately, we cannot reach heroku. Our REST server is not responding");
            }



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
