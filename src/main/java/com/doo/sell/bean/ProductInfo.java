package com.doo.sell.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品
 * @author
 */
@Table(name = "product_info")
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId; //随机字符

    /* 名称 */
    private String productName;

    /* 价格 */
    //private double productPrice;
    private BigDecimal productPrice;

    /* 库存 */
    private Integer productStock;

    /* 描述 */
    private String productDescription;

    /* icon图标 */
    private String productIcon;

    /* 状态，0：正常；1：下架 */
    private Integer productStatus;

    /* 类别编号 */
    private Integer categoryType;
}
