package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getUsersList() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user.getId(), user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }


    @RequestMapping("/getUser")
    @ResponseBody
    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getActiveUser")
    public User getActiveUser(@AuthenticationPrincipal User user) {
        return userService.getUserById(user.getId());
    }

    @GetMapping("/getAllRoles")
    public Map<Integer, String> getAllRoles() {
        return roleService.getAvailableRolesAsMap();
    }

    @GetMapping("/api/getRoleIdByName")
    public int getRoleIdByName(@RequestParam("name") String name) {
        Role role = roleService.getRoleByName(name);
        return role.getId();
    }

}