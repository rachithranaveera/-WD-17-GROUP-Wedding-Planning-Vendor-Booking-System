package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.Booking;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.model.Review;
import com.weddingapp.wd17weddingplanner.model.Vendor;
import com.weddingapp.wd17weddingplanner.services.BookingService;
import com.weddingapp.wd17weddingplanner.services.InvoiceService;
import com.weddingapp.wd17weddingplanner.services.ReviewService;
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
    @Autowired
    private ReviewService reviewService;

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


        List<Booking> myBookings = bookingService.getBookingsByCouple(couple);
        double spent = myBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(b -> b.getVendor().getPrice())
                .sum();

        //rifsky,chandira

        model.addAttribute("couple", couple);
        model.addAttribute("vendors", vendorService.getAllVendors());

        model.addAttribute("myBookings", myBookings);

        model.addAttribute("spent", spent);

        //rachith
        model.addAttribute("remaining", couple.getEstimatedBudget() - spent);

        model.addAttribute("reviews", reviewService.getAllReviews());


        return "couple_dashboard";
    }

    //chandira 

    @PostMapping("/book")
    public String bookVendor(@RequestParam Long vendorId, @RequestParam String date, HttpSession session) {
        Couple couple = getLoggedCouple(session);
        if (couple == null) return "redirect:/login";

       LocalDate bookedDate = LocalDate.parse(date);

        if (!bookedDate.isAfter(LocalDate.now())) {
            return "redirect:/couple/dashboard?error=invalid_date";
        }

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

    //raveshan

    @PostMapping("/review")
    public String submitReview(@RequestParam String content, @RequestParam int rating, HttpSession session) {
        Couple couple = getLoggedCouple(session);
        if (couple == null) return "redirect:/login";

        Review review = new Review();
        review.setCouple(couple);
        review.setContent(content);
        review.setRating(rating);
        reviewService.saveReview(review);

        return "redirect:/couple/dashboard";
    }

    //Rachith

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoice")
    public String sendInvoice(@RequestParam String recipientEmail, HttpSession session) {
        Couple couple = getLoggedCouple(session);
        if (couple == null) return "redirect:/login";

        List<Booking> myBookings = bookingService.getBookingsByCouple(couple);
        double spent = myBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(b -> b.getVendor().getPrice())
                .sum();

        invoiceService.generateAndSendInvoice(couple, myBookings, spent, recipientEmail);

        return "redirect:/couple/dashboard?sent=true";
    }
}
