package com.doo.sell.exception;

import com.doo.sell.enums.ResultEnum;


/**
 *
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }
}
