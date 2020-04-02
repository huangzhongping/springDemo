package com.example.springbootdemo.product.model;

import lombok.Data;

@Data
public class Question {
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


}
