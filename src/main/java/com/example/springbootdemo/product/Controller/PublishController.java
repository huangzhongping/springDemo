package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.mapper.QuestionMapper;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.Question;
import com.example.springbootdemo.product.model.User;
import org.h2.util.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish( Model model,
                           HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        if(user==null){
            model.addAttribute("error","用户未登录");
        }

        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            Model model,
                            HttpServletRequest httpServletRequest
                            ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
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
        User user = getUser(httpServletRequest);
        if(user==null){
            model.addAttribute("error","用户未登录");
            return  "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDesc(description);
        question.setTag(tag);
        question.setCreator(user.getAccount_id());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.add(question);

        return  "redirect:/";
    }

    @Nullable
    private User getUser(HttpServletRequest httpServletRequest) {
        User user=null;

        if( null!=httpServletRequest.getCookies()) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if (name.equals("token")) {
                     user = userMapper.selectToken(cookies[i].getValue());
                    httpServletRequest.getSession().setAttribute("user", user);
                }
            }


        }
        return user;
    }
}
