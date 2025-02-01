package com.eilco.ecommerce.controller;

import jakarta.servlet.http.Cookie;
import org.springframework.ui.Model;
import com.eilco.ecommerce.dto.AuthResponse;
import com.eilco.ecommerce.model.entities.User;
import com.eilco.ecommerce.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        authService.register(user);
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", user.getRole());
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.authenticate(user);

            User authenticatedUser = authService.getUserDetails(user.getUsername());

            // Store JWT in a cookie
            Cookie jwtCookie = new Cookie("jwt", authResponse.getAccessToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            model.addAttribute("firstName", authenticatedUser.getFirstName());
            model.addAttribute("lastName", authenticatedUser.getLastName());
            model.addAttribute("username", authenticatedUser.getUsername());
            model.addAttribute("role", authenticatedUser.getRole());

            return "welcome";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Échec de la connexion : " + e.getMessage());
            return "login";
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshToken(request, response);
    }

}