package com.example.VirtualFridge.dataManagerImpl;

import com.example.VirtualFridge.dataManager.UserManager;
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
    String databaseURL = "jdbc:postgresql://ec2-44-207-253-50.compute-1.amazonaws.com:5432/d4tcg6dkd9hsnm";//"jdbc:postgresql://ec2-3-81-240-17.compute-1.amazonaws.com:5432/d9l0o5gfhlc5co";
    String username = "ktjwdbskjndsxy";
    String password = "199e5be5e918a0ea5fb12a114222a9471dd3ef0a30f1bc163bf5ebbe956b69a8";
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

    @Override
    public void addUser(User user) {

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

    }

    public void createTableTask() {

        // Be carefull: It deletes data if table already exists.
        //
        Statement stmt = null;
        Connection connection = null;
        try{
            connection = basicDataSource.getConnection();
            stmt = connection.createStatement();

            String dropTable = "DROP TABLE IF EXISTS tasks";
            stmt.executeUpdate(dropTable);

            String createTable = "CREATE TABLE users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "email varchar(258) NOT NULL, " +
                    "password varchar(100) NOT NULL)";
            stmt.executeUpdate(createTable);

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
