package com.iluwatar.fragment.composition;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class FragmentController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/compose")
    public String getPage(Model model) {
        String header = restTemplate.exchange("http://localhost:8080/header", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();
        String content = restTemplate.exchange("http://localhost:8080/content", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();
        String footer = restTemplate.exchange("http://localhost:8080/footer", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();

        model.addAttribute("header", header);
        model.addAttribute("content", content);
        model.addAttribute("footer", footer);

        return "fragments";
    }
}
