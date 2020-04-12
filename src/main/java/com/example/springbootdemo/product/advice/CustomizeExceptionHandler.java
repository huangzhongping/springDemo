package com.example.springbootdemo.product.advice;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.product.exception.CustomizeErrorCode;
import com.example.springbootdemo.product.exception.CustomizeException;
import com.example.springbootdemo.product.dto.ResultDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 拦截异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomizeException.class)
    ModelAndView handleControllerException(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {// 返回json格式
            ResultDTO resultDTO;
            PrintWriter writer;
            if (ex instanceof CustomizeException) {
               resultDTO = new ResultDTO(((CustomizeException) ex).getCode(), ex.getMessage());
            } else {
                resultDTO = new ResultDTO(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR);
            }

            return new ModelAndView("error");
        }


    }
}
