package dao.interfaces;

import entities.User;

import java.util.List;

public interface UserInterface {
    User findById(long id);
    User findByEmail(String email);
    List<User> findAll();
    void createUser(User user);
    void updateUser(long id, User user);
    void deleteById(long id);
}
