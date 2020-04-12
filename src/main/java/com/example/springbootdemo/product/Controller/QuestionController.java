package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.CommentDTO;
import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.enums.CommentTypeEnum;
import com.example.springbootdemo.product.service.CommentService;
import com.example.springbootdemo.product.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Long id,
                           Model model){
        if(id==null){
            return "index";
        }
       QuestionDto questionDto =  questionService.getById(id);
        questionService.setViewCount(id);
        //获取评论列表
        List<CommentDTO> comments = commentService.listById(id, CommentTypeEnum.QUEESTION);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return  "question";

    }
}
