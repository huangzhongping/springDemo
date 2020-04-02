package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,avatar_url,token,gmt_create,gmt_modified) values (#{account_id},#{name},#{avatar_url},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User selectToken(String token);
}
