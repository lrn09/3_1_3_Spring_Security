package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String name) {
                setValue(roleService.getRoleByName(name));
            }
        });
    }


    @GetMapping("/admin")
    public String showAdminPage(Model model, Principal principal) {
        User admin = userService.getUserByUsername(principal.getName());
        List<User> userList = userService.getAllUsers();
        Set<Role> roleSet = roleService.showAvailableRoles();
        model.addAttribute("admin", admin);
        model.addAttribute("userList", userList);
        model.addAttribute("roles", roleSet);
        return "bootstrap/admin";
    }

    @PostMapping("/admin/create")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/admin/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
