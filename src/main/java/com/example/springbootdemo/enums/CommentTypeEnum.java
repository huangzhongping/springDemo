package com.example.springbootdemo.enums;

public enum CommentTypeEnum {
    QUEESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.type.equals(type)){
                return  true;
            }
        }
        return false;
    }
}
