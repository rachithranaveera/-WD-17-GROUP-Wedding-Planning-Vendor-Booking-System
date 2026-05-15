package com.weddingapp.wd17weddingplanner.controller;


import com.weddingapp.wd17weddingplanner.model.Admin;
import com.weddingapp.wd17weddingplanner.services.UserService;
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

}
