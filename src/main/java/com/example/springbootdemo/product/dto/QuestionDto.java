package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String desc;
    private String tag;
    private Long  commentCount;
    private Long viewCount;
    private Long likeCount;
    private Long creator;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
