package com.doo.sell.dao;

import com.doo.sell.bean.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单详情
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String > {

    //根据OrderMaster的order_id查询订单详情
    List<OrderDetail> findByOrOrderId(String orderId);
}
