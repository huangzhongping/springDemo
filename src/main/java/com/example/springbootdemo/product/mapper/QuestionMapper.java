package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,desc,tag,comment_count,view_count,like_count,creator,gmt_create,gmt_modified) values (#{title},#{desc},#{tag},#{commentCount},#{viewCount},#{likeCount},#{creator},#{gmtCreate},#{gmtModified})")
    void add(Question question);

    @Select("select * from question")
    List<Question> list();
}
