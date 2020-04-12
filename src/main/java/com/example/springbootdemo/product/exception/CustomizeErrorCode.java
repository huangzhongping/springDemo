package com.example.springbootdemo.product.exception;

/**
 * 异常枚举
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode{
    RESULT_SUCCESS(200,"请求成功"),
    QUESTION_NOT_FOUNT(2001,"你找的问题不在了，要不换一个试试～"),
    SYS_ERROR(2002,"服务器出错，请一会再试试"),
    USER_NOT(2003,"用户不存在，请登录"),
    TARGET_PARAM_NOT_FOUND(2004,"评论的问题不存在了。请换一个试试"),
    TYPE_PARAM_WARN(2005,"评论类型错误或不存在"),
    COMMENT_PARAM_NOT_FOUND(2006,"你找的评论不在了，要不换一个试试～"),
    COMMENT_CONTENT_EMPTY(2006,"输入内容不能为null"),

    ;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private int code;

     CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code  =code;
    }


}
