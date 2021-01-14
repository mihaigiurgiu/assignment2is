package org.launchcode.controllers;
import org.launchcode.models.User;
import org.launchcode.data.UserRepository;
import org.launchcode.models.dto.EventTagDTO;
import org.launchcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/index")
    public String home( ) {
        return "users/index";
    }
    @GetMapping("/users/view")
    public String displayAllUsers(Model model){
        model.addAttribute("title", "All users");
        model.addAttribute("users", userRepository.findAll());

        return "users/view";

    }
    @GetMapping("/users/delete")
    public String displayDeleteUserForm(Model model){
        model.addAttribute("title", "Delete users");
        model.addAttribute("users", userRepository.findAll());
        return "users/delete";
    }

    @PostMapping("/users/delete")
    public String processDeleteUserForm(@RequestParam(required = false) int[] userIds){

        if(userIds != null) {
            for (int id : userIds) {
               userRepository.deleteById(id);
            }
        }
        return "/users/delete";
    }

    @GetMapping("/users/update")
    public String displayUpdateEmailForm(Model model){
        model.addAttribute("title", "Update User");
        model.addAttribute("formerEmail", new User());
        model.addAttribute("newEmail", new User());
        return "users/update";
    }

   @PostMapping("users/update")
    public String processUpdateEmailForm(@ModelAttribute User newUser) {
        String[] emails= newUser.getEmail().split(",");
        String formerEmail = emails[0];
        String newEmail = emails[1];
        User formerUser = userRepository.findByEmail(formerEmail);
        formerUser.setEmail(newEmail);
        userRepository.save(formerUser);
        return "users/index";
    }
}
