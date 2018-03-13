package com.doo.sell.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品类别
 * @author doo at 2018/03/13
 *
 */
//@Table(name = "s_product_category") //表明与类名不一致
@Entity
@DynamicUpdate  //动态更新
@Data  //利用lombok自动生成getter,setter
public class ProductCategory {
    /* 类别id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /* 类目名字 */
    private String categoryName;

    /* 类目编号 */
    private Integer categoryType;

//    private Date createTime;

//    private Date updateTime;


    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
