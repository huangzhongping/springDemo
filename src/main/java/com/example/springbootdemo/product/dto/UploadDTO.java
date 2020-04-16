package com.example.springbootdemo.product.dto;

import lombok.Data;

@Data
public class UploadDTO {
    private int success;
    private String message;
    private String url;
}

