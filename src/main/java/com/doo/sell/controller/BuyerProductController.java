package com.doo.sell.controller;


import com.doo.sell.VO.ProductInfoVO;
import com.doo.sell.VO.ProductVO;
import com.doo.sell.VO.ResultVO;
import com.doo.sell.bean.ProductCategory;
import com.doo.sell.bean.ProductInfo;
import com.doo.sell.service.ProductCategoryService;
import com.doo.sell.service.ProductService;
import com.doo.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("/list")
    public ResultVO list(){
        //1. 查询所有在售的商品
        List<ProductInfo> productInfoList = productService.findSellingAll();

        //2. 查询商品类别
        List<Integer> categoryList = new ArrayList<>();
            //2-1.获取类别type
        for (ProductInfo p: productInfoList) {
                categoryList.add(p.getCategoryType());
        }
            //2-2.使用java8的lamda简化获取方法
        /*List<Integer> categoryList2 = productInfoList.stream()
                .map(e->e.getCategoryType()).collect(Collectors.toList());*/
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryList);
        //3. 拼装数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory :productCategoryList) {
            ProductVO productVo = new ProductVO();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){

                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVo.setProductList(productInfoVOList);


            productVOList.add(productVo);
        }

      /*  ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(productVOList); //list*/
      //使用工具类封装返回结果
        return ResultVOUtil.success(productVOList);
    }

}
