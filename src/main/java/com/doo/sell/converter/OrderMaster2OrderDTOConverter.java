package com.doo.sell.converter;

import com.doo.sell.bean.OrderMaster;
import com.doo.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ordermaster对象转换orderDTO对象转换器
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){

        return orderMasterList.stream().map(e -> converter(e)).collect(Collectors.toList());
    }
}
