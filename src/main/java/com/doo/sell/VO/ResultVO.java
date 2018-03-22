package com.doo.sell.VO;

import lombok.Data;

/**
 * 返回前端的最外层对象
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg = ""; //设置默认值，防止返回null

    private T data;
}
