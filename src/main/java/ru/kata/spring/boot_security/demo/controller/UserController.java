package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {
    private final UserService userServiceImpl;

    @Autowired
    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/index")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "index";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/admin")
    public String showAdminInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("admin", user);

        return "admin";
    }

    @GetMapping("/admin/users/{id}")
    public String showUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "show";
    }


    @GetMapping(value = "admin/users/{id}/edit")
    public String getEditUserPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "edit";
    }

    @PostMapping("/")
    public String createUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/index";
    }

    @PatchMapping(value = "admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userServiceImpl.updateUser(id, user);
        return "redirect:/index";
    }

    @DeleteMapping("admin/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/index";
    }
}
