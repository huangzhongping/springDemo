package com.example.springbootdemo.exception;

/**
 * 异常枚举
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUNT("你找的问题不在了，要不换一个试试～");
    @Override
    public String getMessage() {
        return message;
    }
    private String message;

     CustomizeErrorCode(String message) {
        this.message = message;
    }
}
