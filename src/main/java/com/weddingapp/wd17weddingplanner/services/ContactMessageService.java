package com.weddingapp.wd17weddingplanner.services;

import com.weddingapp.wd17weddingplanner.model.ContactMessage;
import com.weddingapp.wd17weddingplanner.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public void saveMessage(ContactMessage contactMessage) {
        contactMessage.setSubmittedAt(LocalDateTime.now());
        contactMessageRepository.save(contactMessage);
    }
}