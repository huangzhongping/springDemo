package com.example.springbootdemo.product.enums;

public enum NotificationTypeEnum {
    UNREAD(0), READ(1)
    ;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    NotificationTypeEnum(int type) {
        this.type = type;
    }
}
