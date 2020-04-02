package com.example.springbootdemo.product.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String bio;
    private String avatarUrl;



    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
