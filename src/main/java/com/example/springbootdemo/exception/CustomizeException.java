package com.example.springbootdemo.exception;

public class CustomizeException extends  RuntimeException {
    private String message;

    //通过接口传递参数
    public CustomizeException(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
    }

    public CustomizeException(String message) {
       this.message  = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
