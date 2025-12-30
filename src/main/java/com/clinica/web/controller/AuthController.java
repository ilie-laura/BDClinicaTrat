package com.clinica.web.controller;

import com.clinica.web.dto.UserDto;
import com.clinica.web.model.User;
import com.clinica.web.repository.UserRepository;
import com.clinica.web.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {

        User user;
        try {
            user = userService.findByUsername(username);
        } catch (Exception e) {
            model.addAttribute("error", "Username sau parolă invalidă");
            return "login";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Username sau parolă invalidă");
            return "login";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto,Model model) {
        if (userService.existsByUsername(userDto.getUsername())) {
            model.addAttribute("error", "Username deja existent");
            return "/register";
        }
        try {
            userService.save(userDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
