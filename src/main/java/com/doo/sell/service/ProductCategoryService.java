package com.doo.sell.service;

import com.doo.sell.bean.ProductCategory;

import java.util.List;

/**
 * 类别
 * @author doo at 2018
 */
public interface ProductCategoryService {

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
