package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());

        List<CredentialDto> credentials = credentialService.getAllCredentials(user.getUserId());

        model.addAttribute("credentials", credentials);
        model.addAttribute("userId", user.getUserId());

        return "home";
    }
}
