package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.services.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/couple")
public class CoupleController {

    @Autowired
    private VendorService vendorService;

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

        model.addAttribute("vendors", vendorService.getAllVendors());

        model.addAttribute("couple", couple);

        return "couple_dashboard";
    }
}
