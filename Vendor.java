package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.ContactMessage;
import com.weddingapp.wd17weddingplanner.services.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageService contactMessageService;

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactMessage contactMessage) {
        contactMessageService.saveMessage(contactMessage);
        return "redirect:/contact?success=true";
    }
}