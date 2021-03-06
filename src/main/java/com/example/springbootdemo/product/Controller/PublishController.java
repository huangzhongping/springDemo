package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.QuestionDto;

import com.example.springbootdemo.product.dto.UploadDTO;
import com.example.springbootdemo.product.model.Question;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Long id, Model model,
                           HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
        }

        QuestionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDesc());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);
        return  "publish";
    }

    @GetMapping("/publish")
    public String publish( Model model,
                           HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
        }

        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            @RequestParam("id")Long id,
                            Model model,
                            HttpServletRequest httpServletRequest
                            ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
        if(title==null||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return  "publish";
        }
        if(description==null||description.equals("")){
            model.addAttribute("error","内容不能为空");
            return  "publish";
        }
        if(tag==null||tag.equals("")){
            model.addAttribute("error","tag不能为空");
            return  "publish";
        }
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return  "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDesc(description);
        question.setTag(tag);
        question.setCreator(user.getId().toString());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        if(id!=null){
            question.setId(id);
        }
        question.setCommentCount(0L);
        question.setLikeCount(0L);

        questionService.createOrUpdate(question);

        return  "redirect:/";
    }


    @ResponseBody
    @RequestMapping("/upload")
    public UploadDTO uploadImg(@RequestParam("guid")String url){
        UploadDTO uploadDTO = new UploadDTO();
        uploadDTO.setSuccess(1);
        uploadDTO.setUrl("/images/wechat.png");
        return  uploadDTO;
    }



}
