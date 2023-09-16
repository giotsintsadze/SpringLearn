package com.springlearn.springlearn.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/")
    public String hello() {
        return "Its Just a first page";
    }

}
