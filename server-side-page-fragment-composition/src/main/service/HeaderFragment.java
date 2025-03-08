package com.iluwatar.fragment.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderFragment {

    @GetMapping("/header")
    public String getHeader() {
        return "<header><h1>Welcome to My Website</h1></header>";
    }
}
