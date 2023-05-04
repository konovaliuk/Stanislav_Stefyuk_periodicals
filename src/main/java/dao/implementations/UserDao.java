package dao.implementations;

import dao.interfaces.UserInterface;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserInterface {
    private final Connection connection;

    public UserDao(Connection connection) throws SQLException{
        this.connection = connection;
    }

    public User getUser(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("mail");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        return new User(id, name, email, password, role);
    }

    @Override
    public List<User> findAll(){
        List<User> userList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `periodicals`.`user`");
            while(resultSet.next()){
                User user = getUser(resultSet);
                userList.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }


        return userList;

    }

    @Override
    public User findById(long id){
        User user = new User();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM user WHERE user.id = ?");
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();
            user = getUser(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public User findByEmail(String email){
        User user = new User();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM user WHERE user.mail = ?");
            stmt.setString(1, email);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                user = null;
            } else {
                user = getUser(resultSet);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public void createUser(User user){
        if (user.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("INSERT INTO `periodicals`.`user` (`name`,`mail`, `password`, `role`) VALUES (?, ?, ?, ?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, "reader");
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    public void updateUser(long id, User user){
        if (user.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("UPDATE `periodicals`.`user` SET `name`=?, `mail`=?, `password`=?, `role`=? WHERE `id` = ?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteById(long id) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM `periodicals`.`user` WHERE user.id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("An error occurred while closing the connection: " + e.getMessage());
            }
        }
    }

}
