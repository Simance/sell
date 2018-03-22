package com.doo.sell.converter;

import com.doo.sell.bean.OrderDetail;
import com.doo.sell.dto.OrderDTO;
import com.doo.sell.enums.ResultEnum;
import com.doo.sell.exception.SellException;
import com.doo.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> itemList = new ArrayList<>();
        try{
            itemList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】参数错误, string = {}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROT);
        }

        orderDTO.setOrderDetailList(itemList);

        return orderDTO;
    }
}
