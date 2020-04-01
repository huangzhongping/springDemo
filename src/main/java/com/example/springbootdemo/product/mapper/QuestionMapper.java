package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,desc,tag,comment_count,view_count,like_count,user_id,gmt_create,gmt_modified) values (#{title},#{desc},#{tag},#{commentCount},#{viewCount},#{likeCount},#{userId},#{gmtCreate},#{gmtModified})")
    void add(Question question);

}
