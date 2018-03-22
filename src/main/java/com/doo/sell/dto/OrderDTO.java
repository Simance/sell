package com.doo.sell.dto;

import com.doo.sell.bean.OrderDetail;
import com.doo.sell.enums.OrderStatusEnum;
import com.doo.sell.enums.PayStatusEnum;
import com.doo.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclu sion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)  //不返回为null的属性 //配置文件使用全局设置
public class OrderDTO {

    private String orderId;

    /**  */
    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)  //处理属性
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList = new ArrayList<>(); //设置默认值，防止返回null
}
