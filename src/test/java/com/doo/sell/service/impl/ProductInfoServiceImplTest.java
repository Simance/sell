package com.doo.sell.service.impl;

import com.doo.sell.bean.ProductInfo;
import com.doo.sell.enums.ProductEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productService;

    @Test
    public void findById() {
        ProductInfo productInfo = productService.findById("00001");
        Assert.assertEquals("00001", productInfo.getProductId());
    }

    @Test
    public void findSellingAll() {
        List<ProductInfo> productInfoList = productService.findSellingAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("00002");
        productInfo.setProductName("商品2");
        productInfo.setProductPrice(new BigDecimal(6.5));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(ProductEnum.DOWN.getCode());
        productInfo.setCategoryType(3);
        productInfo.setProductDescription("商品2的描述");
        productInfo.setProductIcon("iconPath");

        ProductInfo result =  productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}