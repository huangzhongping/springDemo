package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public ResultDTO(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
