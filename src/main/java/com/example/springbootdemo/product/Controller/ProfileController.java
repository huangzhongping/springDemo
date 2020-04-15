package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.ResultDTO;
import com.example.springbootdemo.product.exception.CustomizeErrorCode;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.service.NotificationService;
import com.example.springbootdemo.product.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action")String action, Model model, HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page",defaultValue = "1")int page, @RequestParam(name = "size",defaultValue = "5")int size

    ){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return  "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");

            PagetationDTO pagetationDTO = questionService.getList(user.getId(),page,size);
            model.addAttribute("pagetation",pagetationDTO);

        }else if("reply".equals(action)){
            //查看通知
            PagetationDTO pagetationDTO = notificationService.getList(user.getId(),page,size);
            long unReadConut = notificationService.getUnReadCount(user.getId());
            model.addAttribute("pagetation",pagetationDTO);
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unReadConut",unReadConut);

        }

        return  "profile";
    }
    @ResponseBody
    @RequestMapping("/profile/read/{id}")
    public ResultDTO<String> read(@PathVariable("id")long id, HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return  new ResultDTO<String>( CustomizeErrorCode.USER_NOT,null);
        }
        notificationService.read(id);
        return  new ResultDTO<String>(CustomizeErrorCode.RESULT_SUCCESS,null);
    }

}
