package com.example.springbootdemo.product.exception;

public class CustomizeException extends  RuntimeException {
    private String message;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    //通过接口传递参数
    public CustomizeException(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
    }

    public CustomizeException(String message) {
       this.message  = message;
    }

    public CustomizeException( Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
