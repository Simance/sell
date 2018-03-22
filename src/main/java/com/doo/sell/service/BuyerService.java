package com.doo.sell.service;

import com.doo.sell.dto.OrderDTO;

/**
 * 用于处理订单详情的查询和取消
 */
public interface BuyerService {

    //查询订单的详情
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
