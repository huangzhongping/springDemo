package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String name;
    private String typeName;
    private String quesitonTitle;
    private long gtm_create;
    private long questionId;
    private User user;
    private int read;

}
