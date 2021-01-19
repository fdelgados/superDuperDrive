package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UnableToCreateUserException;
import com.udacity.jwdnd.course1.cloudstorage.services.UserIsNotAvailableException;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping
    public String signup(User user, Model model) {
        try {
            userService.signup(user);

            return "redirect:/login?signedup";
        } catch (UserIsNotAvailableException | UnableToCreateUserException e) {
            model.addAttribute("error", e.getMessage());

            return "signup";
        }
    }
}
