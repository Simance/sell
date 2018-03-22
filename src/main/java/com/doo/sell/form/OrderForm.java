package com.doo.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 提交的创建订单的表单
 * @author doo
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
