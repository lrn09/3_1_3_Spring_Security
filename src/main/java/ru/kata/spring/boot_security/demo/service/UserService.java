package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {


    public List<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByUsername(String username);

    public void saveUser(User user);

    public void updateUser(int id, User user);

    public void deleteUser(int id);

}
