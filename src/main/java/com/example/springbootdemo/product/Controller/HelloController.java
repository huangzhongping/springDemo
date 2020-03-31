package com.example.springbootdemo.product.Controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name, Model model){
        //将获取到的值赋值给model
        model.addAttribute("name",name);
        return  "hello";//找到resources/templates/hello.html
    }
}
