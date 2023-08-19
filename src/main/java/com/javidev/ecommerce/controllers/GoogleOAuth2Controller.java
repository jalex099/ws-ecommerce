package com.javidev.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleOAuth2Controller {
    @GetMapping("/login/google")
    public String redirectToGoogleAuth() {
        return "redirect:/oauth2/authorization/google";
    }
}
