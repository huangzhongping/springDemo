package com.example.springbootdemo.product.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;


}
