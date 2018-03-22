package com.doo.sell.service;

import com.doo.sell.bean.ProductInfo;
import com.doo.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

public interface ProductService {

    ProductInfo findById(String productId);

    List<ProductInfo> findSellingAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);
}
