package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) {
        User user = userService.getLoggedUser();

        try {
            fileService.createFile(file, user.getUserId());
            model.addAttribute("success", true);
        } catch (UnableToUploadFileException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/file/remove")
    public String removeFile(@RequestParam("id") Integer id, Model model) {
        try {
            fileService.removeFile(id);
            model.addAttribute("success", true);
        } catch (UnableToDeleteFileException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "result";
    }

    @GetMapping(value = "/file/download")
    public void viewFile(
            @RequestParam("id") Integer id,
            HttpServletResponse response) throws IOException {

        try {
            File file = fileService.getFile(id);

            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
            InputStream inputStream = new ByteArrayInputStream(file.getFileData());

            int nRead;
            while ((nRead = inputStream.read()) != -1) {
                response.getWriter().write(nRead);
            }
        } catch (FileNotFoundException e) {
            response.sendRedirect("/file/download-error/" + e.getMessage());
        }
    }

    @GetMapping("/file/download-error/{message}")
    public String downloadError(@PathVariable("message") String message, Model model) {
        model.addAttribute("error", message);

        return "result";
    }
}
