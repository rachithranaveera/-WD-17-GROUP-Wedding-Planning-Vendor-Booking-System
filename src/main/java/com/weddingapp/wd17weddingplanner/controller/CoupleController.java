package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.Booking;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.model.Vendor;
import com.weddingapp.wd17weddingplanner.services.BookingService;
import com.weddingapp.wd17weddingplanner.services.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/couple")
public class CoupleController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private BookingService bookingService;

    private Couple getLoggedCouple(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Couple) {
            return (Couple) user;
        }
        return null;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Couple couple = getLoggedCouple(session);
        if (couple == null) return "redirect:/login";

        //rifsky,chandira

        List<Booking> myBookings = bookingService.getBookingsByCouple(couple);
        double spent = myBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(b -> b.getVendor().getPrice())
                .sum();

        model.addAttribute("couple", couple);
        model.addAttribute("vendors", vendorService.getAllVendors());

        model.addAttribute("myBookings", myBookings);

        model.addAttribute("spent", spent);
        model.addAttribute("remaining", couple.getEstimatedBudget() - spent);


        return "couple_dashboard";
    }

    //chandira

    @PostMapping("/book")
    public String bookVendor(@RequestParam Long vendorId, @RequestParam String date, HttpSession session) {
        Couple couple = getLoggedCouple(session);
        if (couple == null) return "redirect:/login";

        Vendor vendor = vendorService.getVendorById(vendorId);
        if (vendor != null) {
            Booking booking = new Booking();
            booking.setCouple(couple);
            booking.setVendor(vendor);
            booking.setBookingDate(LocalDate.parse(date));
            bookingService.createBooking(booking);
        }
        return "redirect:/couple/dashboard";
    }
}
