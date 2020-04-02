package com.example.springbootdemo.product.Controller;

import com.example.springbootdemo.product.dto.AuthorizeDTO;
import com.example.springbootdemo.product.dto.PagetationDTO;
import com.example.springbootdemo.product.dto.QuestionDto;
import com.example.springbootdemo.product.dto.UserDTO;
import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.provider.AuthorizeProvider;
import com.example.springbootdemo.product.service.QuestionService;
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

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model,
                        @RequestParam(name = "page",defaultValue = "1")int page,@RequestParam(name = "size",defaultValue = "5")int size
                        ) {



        if( null!=httpServletRequest.getCookies()) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if (name.equals("token")) {
                    User user = userMapper.selectToken(cookies[i].getValue());
                    httpServletRequest.getSession().setAttribute("user", user);
                }
            }
        }
        PagetationDTO pagetationDTO = questionService.getList(page,size);
        model.addAttribute("pagetation",pagetationDTO);
        //将获取到的值赋值给model
        return "index";//找到resources/templates/hello.html
    }


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam("state") String state,
                           HttpServletRequest httpServletRequest,
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
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
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


}
