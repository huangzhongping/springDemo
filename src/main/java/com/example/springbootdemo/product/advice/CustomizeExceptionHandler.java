package com.example.springbootdemo.product.advice;

import com.example.springbootdemo.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomizeException.class)
    ModelAndView handleControllerException(Throwable ex, Model model) {
        if(ex instanceof  CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }else{
            model.addAttribute("message","服务器出错了，请等会试试");
        }
        return new ModelAndView("error");
    }
}
