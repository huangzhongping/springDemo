package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action")String action, Model model, HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page",defaultValue = "1")int page, @RequestParam(name = "size",defaultValue = "5")int size

    ){
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");

        }else if("reply".equals(action)){
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","最新回复");
        }
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return  "redirect:/";
        }
        PagetationDTO pagetationDTO = questionService.getList(user.getId(),page,size);
        model.addAttribute("pagetation",pagetationDTO);


        return  "profile";
    }

}
