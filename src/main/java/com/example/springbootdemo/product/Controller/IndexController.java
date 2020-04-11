package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.AuthorizeDTO;
import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.UserDTO;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.provider.AuthorizeProvider;
import com.example.springbootdemo.product.service.QuestionService;
import com.example.springbootdemo.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {
    //引入
    @Autowired
    AuthorizeProvider authorizeProvider;

    //引入配置文件参数
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.redirect.uri}")
    private String redictUri;
    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index( Model model,
                        @RequestParam(name = "page",defaultValue = "1")int page,@RequestParam(name = "size",defaultValue = "5")int size
                        ) {
        PagetationDTO pagetationDTO = questionService.getList(page,size);
        model.addAttribute("pagetation",pagetationDTO);
        //将获取到的值赋值给model
        return "index";//找到resources/templates/hello.html
    }


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam("state") String state,
                           HttpServletResponse httpServletResponse) {
        System.out.println(code);
        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        authorizeDTO.setClient_id(clientId);
        authorizeDTO.setRedirect_uri(redictUri);
        authorizeDTO.setState(state);
        authorizeDTO.setCode(code);
        authorizeDTO.setClient_secret(clientSecret);
        String accessToken = authorizeProvider.getAccessToken(authorizeDTO);
        UserDTO githubUser = authorizeProvider.getUser(accessToken);
        //登录成功
        if (githubUser != null) {
            User user = new User();
            user.setAccountId(githubUser.getId());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatarUrl());
           userService.createOrUpdateUser(user);
            //写cookie
            httpServletResponse.addCookie(new Cookie("token",token));
//          获取session
//          httpServletRequest.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }


    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest servletRequest,HttpServletResponse httpServletResponse){
        //清除session
        servletRequest.getSession().removeAttribute("user");
        //清除cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
    }


}
