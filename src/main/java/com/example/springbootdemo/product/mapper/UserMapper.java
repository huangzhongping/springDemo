package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,avatar_url,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{avatarUrl},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User selectToken(String token);
    @Select("select * from user where id=#{id}")
    User selectId(Integer id);

    @Update("update user set name=#{name},account_id=#{accountId},avatar_url=#{avatarUrl},token=#{token},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    void updateUser(User user);

    @Select("select * from user where account_id=#{id}")
    User selectAccountId(@Param("id") String id);
}
