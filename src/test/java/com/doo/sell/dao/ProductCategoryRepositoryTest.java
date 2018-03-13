package com.doo.sell.dao;

import com.doo.sell.bean.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    //@Transactional //用于测试时自动回滚所有操作
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("类别2", 5);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        Assert.assertEquals(productCategory.getCategoryType(), new Integer(3));
    }

    @Test
    public void findByCategoryTypeTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}