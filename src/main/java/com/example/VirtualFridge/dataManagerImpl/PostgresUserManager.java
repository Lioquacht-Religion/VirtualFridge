package com.example.VirtualFridge.dataManagerImpl;

import com.example.VirtualFridge.dataManager.UserManager;
import com.example.VirtualFridge.model.Grocery;
import com.example.VirtualFridge.model.Storage;
import com.example.VirtualFridge.model.User;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostgresUserManager implements UserManager {
    String databaseURL = "jdbc:postgresql://ec2-54-147-36-107.compute-1.amazonaws.com:5432/df2uguufu0105c";//"jdbc:postgresql://ec2-3-81-240-17.compute-1.amazonaws.com:5432/d9l0o5gfhlc5co";
    String username = "myfgccvkkfndup";
    String password = "c385301dd22562f1359213916017a689bfd17eb6f44db28aa5eb0ab85c34b436";
    BasicDataSource basicDataSource;

    static PostgresUserManager postgresUserManager = null;

    private PostgresUserManager() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseURL);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
    }

    static public PostgresUserManager getPostgresUserManager() {
        if (postgresUserManager == null)
            postgresUserManager = new PostgresUserManager();
        return postgresUserManager;
    }


    @Override
    public Collection<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }

    //@Override
    public User getUser(String attToSearch, String attribute) {

        User r_user = new User("notFound", "404", "BOB");
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String getUser =
                    "SELECT * FROM users WHERE " + attToSearch + " = '" + attribute + "'";

            ResultSet rs = stmt.executeQuery(getUser);

            if(rs.next()) {
                r_user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                r_user.setID(rs.getInt("id"));
            }
            else{ r_user = new User("notFound", "404", "BOB");}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return r_user;
    }

    @Override
    public String addUser(User user) {

        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String udapteSQL = "INSERT into users (name, email, password) VALUES (" +
                    "'" + user.getName() + "', " +
                    "'" + user.getEmail() + "', " +
                    "'" + user.getPassword() + "')";

            stmt.executeUpdate(udapteSQL);

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user.getEmail();

    }

    public String putUser(User user) {

        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String udapteSQL =
                    "UPDATE users " +
                            "SET name = '" + user.getName() + "', email = '" + user.getEmail() +"', password ='"
                            + user.getPassword() + "' " +
                            "WHERE email = '" + user.getEmail() +"'";


            stmt.executeUpdate(udapteSQL);

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "changed userstuff: " + user.getEmail();

    }

    public void deleteUser(User user){

        Statement stmt = null; Connection connection = null;
        try{
            connection = basicDataSource.getConnection(); stmt = connection.createStatement();

            String deleteGroceries = "DELETE FROM groceries WHERE " +
                    "storedin IN  (SELECT storageid FROM storages WHERE owner = " + user.getID() + "));";
            String deleteStorages = "DELETE FROM storages WHERE " +
                    "owner = " + user.getID() + ";";

            System.out.println("delete User");

            String deleteUser = "DELETE FROM users WHERE " +
                    "email = '" + user.getEmail() + " AND password = '" + user.getPassword() +"';";
            stmt.executeUpdate(deleteUser);
            System.out.println("user Table created");

        }
        catch(SQLException e){e.printStackTrace();}

        try{stmt.close();connection.close();
        }catch(SQLException e){e.printStackTrace();}
    }

    public void createTableUser() {

        // Be carefull: It deletes data if table already exists.
        //
        System.out.println("Starting to create new user Table");
        Statement stmt = null;
        Connection connection = null;
        try{
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            System.out.println("getting datasource");

            String dropTable = "DROP TABLE IF EXISTS users";
            stmt.executeUpdate(dropTable);

            System.out.println("creating user Table");

            String createTable = "CREATE TABLE users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "email varchar(258) NOT NULL, " +
                    "password varchar(100) NOT NULL)";
            stmt.executeUpdate(createTable);
            System.out.println("user Table created");

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public void createTableStorage() {

        // Be carefull: It deletes data if table already exists.
        //
        System.out.println("Starting to create new storage Table");
        Statement stmt = null;
        Connection connection = null;
        try{
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            System.out.println("getting datasource");

            String dropTable = "DROP TABLE IF EXISTS storages";
            stmt.executeUpdate(dropTable);

            System.out.println("creating storage Table");

            String createTable = "CREATE TABLE storages (" +
                    "StorageId SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "Owner int NOT NULL, " +
                    "FOREIGN KEY (Owner) REFERENCES users(id))";
            stmt.executeUpdate(createTable);
            System.out.println("storage Table created");

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public void createTableGroceries() {

        // Be carefull: It deletes data if table already exists.
        //
        System.out.println("Starting to create new groceries Table");
        Statement stmt = null;
        Connection connection = null;
        try{
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            System.out.println("getting datasource");

            String dropTable = "DROP TABLE IF EXISTS groceries";
            stmt.executeUpdate(dropTable);

            System.out.println("creating groceries Table");

            String createTable = "CREATE TABLE groceries (" +
                    "GroceryId SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "amount int, " +
                    "unit varchar(50)," +
                    "StoredIn int NOT NULL, " +
                    "FOREIGN KEY (StoredIn) REFERENCES storages(StorageId))";
            stmt.executeUpdate(createTable);
            System.out.println("groceries Table created");

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            stmt.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public String addStorage(Storage storage) {

        Statement stmt = null;
        Connection connection = null;


        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String getStorOwnerId = "(SELECT id FROM users WHERE email = '" + storage.getOwner().getEmail() + "')";
            String udapteSQL = "INSERT into storages (name, Owner) VALUES (" +
                    "'" + storage.getName() + "', " +
                    getStorOwnerId + ")";

            stmt.executeUpdate(udapteSQL);

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storage.getName();

    }

    public Storage getStorage(String storName, User Owner) {

        Storage storage = new Storage("notFound", Owner);
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String getStorOwnerId = "(SELECT id FROM users WHERE email = '" + Owner.getEmail() + "')";
            String getUser =
                    "SELECT * FROM storages WHERE name = '" + storName + "' AND owner = " + getStorOwnerId;

            ResultSet rs = stmt.executeQuery(getUser);

            if(rs.next()) {
                storage = new Storage(
                        rs.getString("name"),
                        Owner
                );
                storage.setIDs(  rs.getInt("owner"),
                        rs.getInt("storageid") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return storage;
    }

    public String deleteStorage(int userID, int storageID){

        Statement stmt = null; Connection connection = null;
        try{
            connection = basicDataSource.getConnection(); stmt = connection.createStatement();

            System.out.println("delete Storage and its groceries");

            String deleteStorage = "DELETE FROM storages WHERE " +
                    "storageid = " + storageID + " AND owner = " + userID;
            stmt.executeUpdate(deleteStorage);

            String deleteStorageGroc = "DELETE FROM groceries WHERE " +
                    "storedin = " + storageID;
            stmt.executeUpdate(deleteStorageGroc);

        }
        catch(SQLException e){e.printStackTrace();}

        try{stmt.close();connection.close();
        }catch(SQLException e){e.printStackTrace();}
        return "delete Storage and its groceries";
    }

    public String deleteGrocery(int storageID, int groceryID){

        Statement stmt = null; Connection connection = null;
        try{
            connection = basicDataSource.getConnection(); stmt = connection.createStatement();

            System.out.println("delete Grocery from Storage");

            String deleteStorageGroc = "DELETE FROM groceries WHERE " +
                    "storedin = " + storageID + "AND groceryid = " + groceryID;
            stmt.executeUpdate(deleteStorageGroc);

        }
        catch(SQLException e){e.printStackTrace();}

        try{stmt.close();connection.close();
        }catch(SQLException e){e.printStackTrace();}
        return "delete Grocery from Storage";
    }

    public String addGrocery(Storage storage, Grocery grocery) {

        Statement stmt = null;
        Connection connection = null;


        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            String getStorOwnerId = "(SELECT id FROM users WHERE email = '" + storage.getOwner().getEmail() + "')";
            String udapteSQL = "INSERT into groceries (name, amount, unit, storedin) VALUES (" +
                    "'" + grocery.getName() + "', " +
                    "'" + grocery.getAmount() + "', " +
                    "'" + grocery.getUnit() + "', " +
                    "'" + storage.getStorageID()  + "')";

            stmt.executeUpdate(udapteSQL);

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storage.getName();

    }

    public Collection<Grocery> getGroceries(int storageID) {

        Collection<Grocery> groceries = new ArrayList<Grocery>();
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            //String getStorOwnerId = "(SELECT id FROM users WHERE email = '" + Owner.getEmail() + "')";
            String getGroceries =
                    "SELECT * FROM groceries WHERE storedin = '" + storageID + "'";

            ResultSet rs = stmt.executeQuery(getGroceries);

            while(rs.next()) {
                groceries.add( new Grocery(
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getInt("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return groceries;
    }


}
