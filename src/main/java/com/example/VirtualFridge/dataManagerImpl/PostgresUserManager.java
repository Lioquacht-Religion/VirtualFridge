package com.example.VirtualFridge.dataManagerImpl;

import com.example.VirtualFridge.dataManager.UserManager;
import com.example.VirtualFridge.model.User;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
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

    //TODO: FIX does not work
    //@Override
    public Collection<User> getUser(User user) {

        Collection<User> r_user = new ArrayList<>();
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();
            //SELECT * FROM users WHERE name = 'Klaus Riec' AND email = 'klauser@mail.com' AND password = 'wordpass';
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE " +
                    "name = 'Klaus Riec' AND email = 'klauser@mail.com' AND password = 'wordpass'"
            );
            //FLO DU HUND HAHA
            while (rs.next()) {
                r_user.add( new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
               // System.out.println(r_user.getEmail());
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

    public void deleteUser(User user){

        Statement stmt = null;
        Connection connection = null;
        try{
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();

            System.out.println("delete User");

            String deleteUser = "DELETE FROM users WHERE " +
                    "email = '" + user.getEmail() + "';";
            stmt.executeUpdate(deleteUser);
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
}
