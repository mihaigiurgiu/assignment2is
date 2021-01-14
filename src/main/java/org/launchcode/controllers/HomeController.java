package org.launchcode.controllers;

import org.launchcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root( Model model) {
        return "events/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}