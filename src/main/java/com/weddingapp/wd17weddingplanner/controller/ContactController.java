package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.ContactMessage;
import com.weddingapp.wd17weddingplanner.services.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageService contactMessageService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactMessage contactMessage) {
        contactMessageService.saveMessage(contactMessage);

        try {
            SimpleMailMessage userMessage = new SimpleMailMessage();

            userMessage.setTo(contactMessage.getEmail());
            userMessage.setSubject(contactMessage.getSubject());
            userMessage.setText(contactMessage.getMessage());

            mailSender.send(userMessage);
        } catch (Exception e) {
            System.out.println("Failed to send auto-reply to user: " + e.getMessage());
        }

        return "redirect:/contact?success=true";
    }
}
