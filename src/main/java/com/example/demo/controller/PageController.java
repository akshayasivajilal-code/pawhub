package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/register", "/register.html"})
    public String registerPage() {
        return "register";
    }

    @GetMapping({"/login", "/login.html"})
    public String loginPage() {
        return "login";
    }

    @GetMapping({"/home", "/home.html"})
    public String homePage() {
        return "home";
    }

    @GetMapping({"/dashboard", "/dashboard.html"})
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping({"/pet-services", "/pet-services.html"})
    public String petservicesPage() {
        return "pet-services";
    }

    @GetMapping({"/my-pets", "/my-pets.html"})
    public String myPetsPage() {
        return "my-pets";
    }

    @GetMapping({"/add-pet", "/add-pet.html"})
    public String addPetPage() {
        return "add-pet";
    }

    @GetMapping({"/my-bookings", "/my-bookings.html"})
    public String myBookingsPage() {
        return "my-bookings";
    }

    @GetMapping({"/checkout", "/checkout.html"})
    public String checkout() {
        return "checkout";
    }

    @GetMapping({"/order-success", "/order-success.html"})
    public String orderSuccess() {
        return "order-success";
    }
}
