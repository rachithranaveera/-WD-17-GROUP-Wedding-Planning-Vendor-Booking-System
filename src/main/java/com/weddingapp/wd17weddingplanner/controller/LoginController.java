package com.weddingapp.wd17weddingplanner.controller;

import com.weddingapp.wd17weddingplanner.model.Admin;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.model.User;
import com.weddingapp.wd17weddingplanner.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if (user instanceof Admin) {
                return "redirect:/admin/dashboard";
            } else if (user instanceof Couple) {
                return "redirect:/couple/dashboard";
            }
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("couple", new Couple());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Couple couple, Model model) {
        userService.saveUser(couple);
        return "redirect:/login";
    }
}
