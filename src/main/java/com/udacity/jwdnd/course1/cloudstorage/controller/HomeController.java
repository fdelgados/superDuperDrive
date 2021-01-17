package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.FileDto;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value={"", "/", "/home"})
public class HomeController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;

    public HomeController(
        CredentialService credentialService,
        UserService userService,
        FileService fileService,
        NoteService noteService) {

        this.credentialService = credentialService;
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        User user = userService.getLoggedUser();

        Integer userId = user.getUserId();

        List<CredentialDto> credentials = credentialService.getAllCredentials(userId);
        List<FileDto> files = fileService.getUserFiles(userId);
        List<NoteDto> notes = noteService.getUserNotes(userId);

        model.addAttribute("credentials", credentials);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);

        return "home";
    }
}
