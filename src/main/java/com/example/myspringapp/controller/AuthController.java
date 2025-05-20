package com.example.myspringapp.controller;

import com.example.myspringapp.model.User;
import com.example.myspringapp.repository.UserRepository;
import com.example.myspringapp.service.EmailService;
import com.example.myspringapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public AuthController(UserService userService,
                          UserRepository userRepository,
                          EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Username already exists");
            return "signup";
        }
        User savedUser = userService.registerUser(user);
        emailService.sendConfirmationEmail(savedUser);
        model.addAttribute("message", "Registration successful! Check your email to confirm your account.");
        return "signin";
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token, Model model) {
        User user = userRepository.findByConfirmationToken(token).orElse(null);
        if (user == null) {
            model.addAttribute("message", "Invalid confirmation token.");
            return "error";
        }
        user.setConfirmed(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
        model.addAttribute("message", "Email confirmed! You can now log in.");
        return "signin";
    }

    @GetMapping("/signin")
    public String signinForm(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "logout", required = false) String logout,
                             Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully");
        }
        return "signin";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return "home";
    }
}
