package org.launchcode.models;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Event extends AbstractEntity{

    @NotBlank
    @Size(min = 3, max = 50,message = "Size should be between 3 and 50 characters!")
    private String name;

    @ManyToOne
    @NotNull(message = "Category is required")
    private EventCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    public Event(String name, EventCategory category) {
        this.name = name;
        this.category = category;

    }

    public Event() {
    }

    public List<Tag> getTags() {
        return tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }
    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory eventCategory) {
        this.category = eventCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
