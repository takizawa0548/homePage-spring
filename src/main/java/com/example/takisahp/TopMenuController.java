package com.example.takisahp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TopMenuController {
    @GetMapping
    public String indexView(){
        return "topMenu";
    }

    @GetMapping("profile")
    public String profileView(){
        return "profile";
    }
}

