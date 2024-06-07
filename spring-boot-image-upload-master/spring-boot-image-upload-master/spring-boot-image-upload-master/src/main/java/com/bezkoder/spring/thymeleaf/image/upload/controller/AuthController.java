package com.bezkoder.spring.thymeleaf.image.upload.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        // Xử lý đăng xuất
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model, OAuth2AuthenticationToken authentication) {
        model.addAttribute("name", authentication.getPrincipal().getAttribute("name"));
        model.addAttribute("email", authentication.getPrincipal().getAttribute("email"));
        return "profile";
    }
}
