package com.example.springbootdemo.product.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}
