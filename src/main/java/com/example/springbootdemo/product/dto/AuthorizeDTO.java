package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class AuthorizeDTO {
    private String client_id;
    private String redirect_uri;
    private String client_secret;
    private String code;
    private String state;

}
