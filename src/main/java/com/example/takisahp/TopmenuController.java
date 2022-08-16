package com.example.takisahp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopmenuController {
    @GetMapping("/")
    public String indexView(){
        return "topmenu";
    }

    @GetMapping("profile")
    public String profileView(){
        return "profile";
    }
}

