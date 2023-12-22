package com.searchEngine.searchEngine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VIewController {
    @GetMapping("/")
    public String home(){
        return "views/index.html";
    }

    @GetMapping("/results")
    public String results(){
        return "views/searchResults.html";
    } 
}
