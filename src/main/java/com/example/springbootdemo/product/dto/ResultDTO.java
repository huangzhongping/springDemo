package com.example.springbootdemo.product.dto;

import com.example.springbootdemo.product.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultDTO(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResultDTO(Integer code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;

    }
    public ResultDTO(ICustomizeErrorCode customizeErrorCode,T data) {
        this.message = customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
        this.data = data;
    }
}
