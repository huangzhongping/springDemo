package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.service.QuestionService;
import com.example.springbootdemo.product.service.UserService;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")String id,
                           Model model){
        if(StringUtils.isNullOrEmpty(id)){
            return "index";
        }
       QuestionDto questionDto =  questionService.getById(id);
        questionService.setViewCount(id);
        model.addAttribute("question",questionDto);
        return  "question";

    }
}
