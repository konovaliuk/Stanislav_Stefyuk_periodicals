package services;

import connection.DBConnection;
import dao.implementations.UserDao;
import entities.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    public boolean checkAuthorization(String email, String password) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        User user = userCon.findByEmail(email) ;

        userCon.closeConnection();

        if (user == null)
            return false;

        if(password.equals(user.getPassword()))
            return true;
        return false;

    }

    public List<User> findAllUsers() throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        List<User> users = userCon.findAll();

        userCon.closeConnection();

        return users;
    }

    public boolean addUser(String name, String email, String password) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);

        userCon.createUser(new User(name, email, password));
        User user = userCon.findByEmail(email);

        userCon.closeConnection();

        if(user.getId()!=0){
            return true;}
        else{return false;}
    }

    public Long getUserIdByEmail(String email) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        User user = userCon.findByEmail(email);

        userCon.closeConnection();

        if (user == null)
            return null;

        return user.getId();
    }

    public User findUserByEmail(String email) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        User user = userCon.findByEmail(email);

        userCon.closeConnection();

        return user;
    }

    public boolean updateUser(long id, User user) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        userCon.updateUser(id, user);

        userCon.closeConnection();

        return user.getId() != 0;
    }

    public void deleteUser(Long userId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        UserDao userCon = new UserDao(conn);
        userCon.deleteById(userId);

        userCon.closeConnection();
    }
}
