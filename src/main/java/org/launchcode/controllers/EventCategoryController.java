package org.launchcode.controllers;

import org.launchcode.data.EventCategoryRepository;
import org.launchcode.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping("eventCategory")
    public String displayAllCategories(Model model){
        model.addAttribute("categories", eventCategoryRepository.findAll());
        model.addAttribute("title", "All categories!");
        return "eventCategory/index";
    }

    @RequestMapping("eventCategory/create")
    public String displayCreateCategoryForm(Model model){
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());
        return "eventCategory/create";
    }

    @PostMapping("eventCategory/create")
    public String processCreateCategoryForm(@ModelAttribute @Valid EventCategory eventCategory, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "eventCategory/create";
        }
        eventCategoryRepository.save(eventCategory);
        return "redirect:";
    }
}
