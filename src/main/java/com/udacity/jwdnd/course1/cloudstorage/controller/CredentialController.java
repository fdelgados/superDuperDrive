package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UnableToDeleteCredentialException;
import com.udacity.jwdnd.course1.cloudstorage.services.UnableToSaveCredentialException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping(value = "/credential")
    public String postCredential(CredentialDto credentialDto, Model model) {

        try {
            credentialService.saveCredential(credentialDto);
            model.addAttribute("success", true);
        } catch (CredentialNotFoundException | UnableToSaveCredentialException e) {
            model.addAttribute("error", true);
        }

        return "result";
    }

    @GetMapping(value = "/credential/remove")
    public String removeCredential(@RequestParam("id") Integer id, Model model) {
        try {
            credentialService.removeCredential(id);
            model.addAttribute("success", true);
        } catch (UnableToDeleteCredentialException e) {
            model.addAttribute("error", true);
        }

        return "result";
    }
}
