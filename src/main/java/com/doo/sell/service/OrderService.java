package com.doo.sell.service;

import com.doo.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

/**
 *
 */
public interface OrderService {

    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询单个用户的订单列表. */
    Page<OrderDTO> findList(String openId, Pageable pageable);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完成订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO pay(OrderDTO orderDTO);

    /** 查询所有订单列表. */
    Page<OrderDTO> findList(Pageable pageable);
}
