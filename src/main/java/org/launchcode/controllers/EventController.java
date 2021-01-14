package org.launchcode.controllers;

import org.launchcode.data.EventCategoryRepository;
import org.launchcode.data.EventRepository;
import org.launchcode.data.TagRepository;
import org.launchcode.models.Event;
import org.launchcode.models.EventCategory;
import org.launchcode.models.Tag;
import org.launchcode.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model){
        if(categoryId == null) {
            model.addAttribute("title", "All events");
            model.addAttribute("events", eventRepository.findAll());
        } else{
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if(result.isEmpty()){
                model.addAttribute("title", "Invalid category: " + categoryId);
            }else{
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }
        return "events/index";

    }

    @GetMapping("create")
    public String renderCreateEventForm(Model model){
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model){
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds){

        if(eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("update")
    public String displayUpdateNameForm(Model model) {
        model.addAttribute("title", "Update name");
        model.addAttribute("formerName", new Event());
        model.addAttribute("newName", new Event());
        return "events/update";
    }

    @PostMapping("update")
    public String processUpdateNameForm(@ModelAttribute Event newName) {
        String[] names = newName.getName().split(",");
        String formerName = names[0];
        String updatedName = names[1];
        Event formerEvent = eventRepository.findByName(formerName);
        formerEvent.setName(updatedName);
        eventRepository.save(formerEvent);
        return "redirect:";
    }

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model){

        Optional<Event> result = eventRepository.findById(eventId);

        if(result.isEmpty()){
            model.addAttribute("title" + "Invalid event Id : " + eventId);
        }else{
            Event event = result.get();
            model.addAttribute("title", event.getName() + " details ");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }
        return "events/detail";
    }
    
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();

        model.addAttribute("title", "Add tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("event", event);
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);

        return "events/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag, Errors errors){
        if(!errors.hasErrors()){
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();

            if( !event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }

            return "redirect:detail?eventId=" + event.getId();
        }

        return "events/add-tag";
    }

}
