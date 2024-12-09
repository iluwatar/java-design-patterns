package com.iluwatar.fragment.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooterFragment {

    @GetMapping("/footer")
    public String getFooter() {
        return "<footer><p>&copy; 2024 My Website</p></footer>";
    }
}
