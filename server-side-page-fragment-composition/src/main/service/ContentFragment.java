package com.iluwatar.fragment.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentFragment {

    @GetMapping("/content")
    public String getContent() {
        return "<section><p>This is the main content of the page.</p></section>";
    }
}
