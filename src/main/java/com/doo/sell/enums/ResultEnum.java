package com.doo.sell.enums;

import lombok.Getter;

/**
 * 返回前端的异常枚举
 */
@Getter
public enum ResultEnum {
    PARAM_ERROT(9, "参数错误"),
    PRODUCT_NOT_EXIT(10, "商品不存在"),
    STOCK_NOT_ENOUGH(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    PAY_STATUS_ERROR(16, "支付状态不正确"),
    UPDATE_FAIL(17, "订单更新失败"),
    CART_EMPTY(18, "购物车为空"),
    ORDER_OWNER_ERROR(19, "此订单不属于当前用户"),
    WECHAT_MP_ERROR(20, "微信网页授权出错"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
