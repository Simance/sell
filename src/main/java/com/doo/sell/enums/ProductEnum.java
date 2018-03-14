package com.doo.sell.enums;

import lombok.Data;
import lombok.Getter;

//@Data 枚举类用getter
@Getter
public enum ProductEnum {
    SELLING(0, "在售"),
    DOWN(1, "下架");

    private Integer code;
    private String message;

    ProductEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
