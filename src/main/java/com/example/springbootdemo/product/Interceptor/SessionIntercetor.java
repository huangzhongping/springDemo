package com.example.springbootdemo.product.Interceptor;

import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.model.UserExample;
import com.example.springbootdemo.product.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionIntercetor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookies中的token，存到session中
        if (null != request.getCookies()) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if (name.equals("token")) {
                    UserExample example = new UserExample();
                    String value = cookies[i].getValue();
                    example.createCriteria()
                            .andTokenEqualTo(value);
                    List<User> users = userMapper.selectByExample(example);
//                    User user = userMapper.selectToken(cookies[i].getValue());


                    if (users.size() > 0) {
                        long unReadConut = notificationService.getUnReadCount(users.get(0).getId());
                        request.getSession().setAttribute("user", users.get(0));
                        request.getSession().setAttribute("readConut", unReadConut);
                    }

                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
