package com.doo.sell.service.impl;

import com.doo.sell.bean.ProductInfo;
import com.doo.sell.dao.ProductInfoRepository;
import com.doo.sell.dto.CartDTO;
import com.doo.sell.enums.ProductEnum;
import com.doo.sell.enums.ResultEnum;
import com.doo.sell.exception.SellException;
import com.doo.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findById(String productId) {
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findSellingAll() {
        return repository.findByProductStatus(ProductEnum.SELLING.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw  new SellException(ResultEnum.STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
