package com.doo.sell.dao;

import com.doo.sell.bean.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author doo at 2018/03/13
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>{
    /* 根据类别查询 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
