package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UnableToDeleteNoteException;
import com.udacity.jwdnd.course1.cloudstorage.services.UnableToSaveNoteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String postNote(NoteDto noteDto, Model model) {
        try {
            noteService.saveNote(noteDto);
            model.addAttribute("success", true);
        } catch (UnableToSaveNoteException | NoteNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "result";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam("id") Integer id, Model model) {
        try {
            noteService.deleteNote(id);
            model.addAttribute("success", true);
        } catch (UnableToDeleteNoteException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "result";
    }
}
