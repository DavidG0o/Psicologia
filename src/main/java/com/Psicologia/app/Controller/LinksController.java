package com.Psicologia.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinksController {

    @GetMapping("/links")
    public String showLinksPage() {
        return "links";
    }
}
