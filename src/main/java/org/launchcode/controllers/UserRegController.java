package org.launchcode.controllers;

import javax.validation.Valid;

import org.launchcode.models.User;
import org.launchcode.models.dto.UserRegDTO;
import org.launchcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
public class UserRegController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegDTO userRegDTO() {
        return new UserRegDTO();
    }

    @GetMapping
    public String displayRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String processRegistrationForm(@ModelAttribute("user") @Valid UserRegDTO userRegDTO,
                                      BindingResult result){

        User existing = userService.findByEmail(userRegDTO.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userRegDTO);
        return "users/index";
    }

}