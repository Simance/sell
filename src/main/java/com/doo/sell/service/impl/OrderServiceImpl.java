package com.doo.sell.service.impl;

import com.doo.sell.bean.OrderDetail;
import com.doo.sell.bean.OrderMaster;
import com.doo.sell.bean.ProductInfo;
import com.doo.sell.converter.OrderMaster2OrderDTOConverter;
import com.doo.sell.dao.OrderDetailRepository;
import com.doo.sell.dao.OrderMasterRepository;
import com.doo.sell.dto.CartDTO;
import com.doo.sell.dto.OrderDTO;
import com.doo.sell.enums.OrderStatusEnum;
import com.doo.sell.enums.PayStatusEnum;
import com.doo.sell.enums.ResultEnum;
import com.doo.sell.exception.SellException;
import com.doo.sell.service.OrderService;
import com.doo.sell.service.ProductService;
import com.doo.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单service实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO); // 订单总价
        List<CartDTO> cartDTOList = new ArrayList<>();

        //1.查询商品（数量，价格）
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail :orderDetailList) {

            ProductInfo productInfo = productService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            //2. 计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
            //订单详情写入数据库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);

        }

        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        //List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String openId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，不是新订单,orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2. 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【取消订单】更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单详情错误, orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //4. 如果已支付则退款
        //TODO
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ){
            log.error("【完结订单】订单状态不正确，不是新订单,orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【完结订单】更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ){
            log.error("【支付订单】订单状态不正确，不是新订单,orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getOrderStatus().equals(PayStatusEnum.WAIT.getCode()) ){
            log.error("【支付订单】支付状态不正确,orderId = {}, payStatus = {}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【支付订单】更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        return null;
    }
}
