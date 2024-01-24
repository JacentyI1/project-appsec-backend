package com.jack.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @GetMapping("/auth/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

//    @GetMapping("/users")
//    public String userPage() {
//        return "user";
//    }
//
//    @GetMapping("/posts")
//    public String messagePage() {
//        return "message";
//    }
//
//    @GetMapping("/api/admin")
//    public String adminPage(){
//        return "admin";
//    }
}
