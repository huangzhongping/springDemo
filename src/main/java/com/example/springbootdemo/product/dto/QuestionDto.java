package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String desc;
    private String tag;
    private Integer  commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
