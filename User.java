package com.weddingapp.wd17weddingplanner.controller;


import com.weddingapp.wd17weddingplanner.model.Admin;
import com.weddingapp.wd17weddingplanner.model.Vendor;
import com.weddingapp.wd17weddingplanner.services.BookingService;
import com.weddingapp.wd17weddingplanner.services.UserService;
import com.weddingapp.wd17weddingplanner.services.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private BookingService bookingService;


    private boolean isAdmin(HttpSession session) {
        Object user = session.getAttribute("user");
        return user instanceof Admin;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("users", userService.getAllUsers());
        return "admin_dashboard";
    }

    @GetMapping("/users/add-admin")
    public String addAdminPage(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("admin", new Admin());
        return "add_admin";
    }

    @PostMapping("/users/add-admin")
    public String addAdmin(@ModelAttribute Admin admin, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.saveUser(admin);
        return "redirect:/admin/dashboard";
    }

    //Rifsky

    @GetMapping("/vendors/add")
    public String addVendorPage(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("vendor", new Vendor());
        return "add_vendor";
    }

    @PostMapping("/vendors/add")
    public String addVendor(@ModelAttribute Vendor vendor, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        vendorService.saveVendor(vendor);
        return "redirect:/admin/dashboard";
    }

    //chandira

    @PostMapping("/bookings/update")
    public String updateBooking(@RequestParam Long bookingId, @RequestParam String status, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/admin/dashboard";
    }

}
