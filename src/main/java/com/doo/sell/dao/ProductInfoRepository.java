package com.doo.sell.dao;

import com.doo.sell.bean.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    //查询上架的商品
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
