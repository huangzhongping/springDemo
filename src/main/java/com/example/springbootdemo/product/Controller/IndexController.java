package com.example.springbootdemo.product.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.springbootdemo.product.dto.AuthorizeDTO;
import com.example.springbootdemo.product.provider.AuthorizeProvider;
import com.sun.javafx.util.Logging;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class IndexController {
    //引入
    @Autowired
    AuthorizeProvider authorizeProvider;

    @GetMapping("/")
    public String index(){

        //将获取到的值赋值给model
        return  "index";//找到resources/templates/hello.html
    }

    @GetMapping("/callback")
    public String getAuthorize(@RequestParam(name = "code")String code,@RequestParam("state")String state){
        System.out.println(code);
        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        authorizeDTO.setClient_id("9bed93dab6f7fe0d264f");
        authorizeDTO.setRedirect_uri("http://localhost:8887/callback");
        authorizeDTO.setState(state);
        authorizeDTO.setCode(code);
        authorizeDTO.setClient_secret("546cfe080d802248d92edd33c44f900a57f46b7d");
        String accessToken = authorizeProvider.getAccessToken(authorizeDTO);
        authorizeProvider.getUser(accessToken);
        return "index";
    }


}
