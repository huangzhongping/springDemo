package com.example.springbootdemo.product.enums;

public enum NotificationStatusEnum {
    QUEESTIONSTATUS(0,"回复了问题"), COMMENTSTATUS(1,"回复了评论")
    ;

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getName(int type){
        return type==NotificationStatusEnum.QUEESTIONSTATUS.type?NotificationStatusEnum.QUEESTIONSTATUS.name:NotificationStatusEnum.COMMENTSTATUS.name;
    }
}
