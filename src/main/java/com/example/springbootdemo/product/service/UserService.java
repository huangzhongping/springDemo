package com.example.springbootdemo.product.service;

import com.example.springbootdemo.product.mapper.UserMapper;
import com.example.springbootdemo.product.model.User;
import com.example.springbootdemo.product.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdateUser(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
//        User dbUser = userMapper.selectAccountId(user.getAccountId());
        if (users.size() == 0) {
            //创建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        } else { //更新
            User dbUser = users.get(0);
            User record = new User();
            record.setName(user.getName());
            record.setAvatarUrl(user.getAvatarUrl());
            record.setToken(user.getToken());
            record.setGmtModified(System.currentTimeMillis());
            UserExample example1 = new UserExample();
            example1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(record, example1);

        }
    }
}

