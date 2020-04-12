package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long likeCount;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
