package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

   

    @GetMapping({"/adoption", "/adoption.html"})
    public String adoption() {
        return "adoption";
    }

    @GetMapping({"/marketplace", "/marketplace.html"})
    public String marketplace() {
        return "marketplace";
    }

    @GetMapping({"/lost-found", "/lost-found.html"})
    public String lostFound() {
        return "lost-found";
    }

    @GetMapping({"/vaccination", "/vaccination.html"})
    public String vaccination() {
        return "vaccination";
    }

    @GetMapping({"/vets", "/vets.html"})
    public String vets() {
        return "vets";
    }

    @GetMapping({"/community", "/community.html"})
    public String community() {
        return "community";
    }
}