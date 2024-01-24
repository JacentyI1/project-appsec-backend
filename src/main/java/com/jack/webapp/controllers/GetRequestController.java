package com.jack.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GetRequestController {
    @GetMapping("/auth/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/users")
    public String usersPage() {
        return "userAccount";
    }
}
