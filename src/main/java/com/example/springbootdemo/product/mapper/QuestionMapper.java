package com.example.springbootdemo.product.mapper;

import com.example.springbootdemo.product.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,desc,tag,comment_count,view_count,like_count,creator,gmt_create,gmt_modified) values (#{title},#{desc},#{tag},#{commentCount},#{viewCount},#{likeCount},#{creator},#{gmtCreate},#{gmtModified})")
    void add(Question question);

    @Select("select * from question limit #{offset},#{limit}")
    List<Question> list(@Param("offset") int offset, @Param("limit")int limit);
    @Select("select count(1) from question")
    Integer getCount();
    @Select("select count(1) from question where creator=#{userId}")
    Integer getCountById(@Param("userId") int userId);

    @Select("select * from question where creator=#{userId} limit #{offset},#{limit}")
    List<Question> listById(@Param("userId") int userId,@Param("offset") int offset, @Param("limit")int limit);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") String id);
}
