package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private String id;
    private String title;
    private String desc;
    private String tag;
    private String  commentCount;
    private String viewCount;
    private String likeCount;
    private String creator;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
