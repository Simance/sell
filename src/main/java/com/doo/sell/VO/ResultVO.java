package com.doo.sell.VO;

import lombok.Data;

/**
 * 返回前端的最外层对象
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
