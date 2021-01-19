package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ErrorController {
    public String customError() {
        return "error";
    }
}
