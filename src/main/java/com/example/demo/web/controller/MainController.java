package com.example.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "main/main";
    }

    @GetMapping("/main")
    public String mainInit() {
        return "main/main";
    }

}
