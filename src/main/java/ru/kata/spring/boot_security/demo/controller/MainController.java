package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String showLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "bootstrap/admin";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "bootstrap/user";
    }
}
