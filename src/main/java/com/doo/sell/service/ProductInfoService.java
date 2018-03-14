package com.doo.sell.service;

import com.doo.sell.bean.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

public interface ProductInfoService {

    ProductInfo findById(String productId);

    List<ProductInfo> findSellingAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
}
