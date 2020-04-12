package com.example.springbootdemo.product.enums;

public enum CommentTypeEnum {
    QUEESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
