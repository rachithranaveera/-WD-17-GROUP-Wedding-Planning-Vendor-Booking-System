package com.weddingapp.wd17weddingplanner.services;

import com.weddingapp.wd17weddingplanner.model.Booking;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.model.Invoice;
import com.weddingapp.wd17weddingplanner.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void generateAndSendInvoice(Couple couple, List<Booking> bookings, double totalSpent, String recipientEmail) {
        Invoice invoice = new Invoice();
        invoice.setCouple(couple);
        invoice.setTotalAmount(totalSpent);
        invoice.setGeneratedAt(LocalDateTime.now());
        invoiceRepository.save(invoice);

        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(couple.getFullName()).append(",\n\n");
        content.append("Thank you for using Wedding Planner. Here is your current invoice summary:\n\n");
        content.append("--- BUDGET OVERVIEW ---\n");
        content.append("Estimated Budget: $").append(couple.getEstimatedBudget()).append("\n");
        content.append("Total Amount Spent: $").append(totalSpent).append("\n");
        content.append("Remaining Budget: $").append(couple.getEstimatedBudget() - totalSpent).append("\n\n");

        content.append("--- BOOKING DETAILS ---\n");
        for (Booking b : bookings) {
            content.append("- ").append(b.getVendor().getName())
                    .append(" [").append(b.getVendor().getCategory()).append("]: $")
                    .append(b.getVendor().getPrice()).append("\n");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Wedding Invoice - " + couple.getFullName());
        message.setText(content.toString());

        mailSender.send(message);
    }
}