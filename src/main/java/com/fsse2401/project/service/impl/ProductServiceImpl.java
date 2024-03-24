package com.fsse2401.project.service.impl;

import com.fsse2401.project.data.product.domainObject.ProductResponseData;
import com.fsse2401.project.entity.ProductEntity;
import com.fsse2401.project.exception.product.ProductNotFoundException;
import com.fsse2401.project.repository.ProductRepository;
import com.fsse2401.project.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseData getProductByProductId (Integer pid){
        try {
            return new ProductResponseData(getEntityByPid(pid));
        } catch (ProductNotFoundException ex){
            logger.warn("Search Product: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public List<ProductResponseData> getAllProduct () {
        List<ProductResponseData> productResponseDataList = new ArrayList<>();
        for (ProductEntity productEntity : productRepository.findAll()) {
            ProductResponseData responseData = new ProductResponseData(productEntity);
            productResponseDataList.add(responseData);
        }
        return productResponseDataList;
    }
    @Override
    public ProductEntity getEntityByPid (Integer pid){
        return productRepository.findProductEntityByPid(pid).orElseThrow(() -> new ProductNotFoundException(pid));
    }

    @Override
    public boolean isValidQuantity (Integer pid, Integer quantity){
        ProductEntity productEntity = getEntityByPid(pid);
        return productEntity.getStock() >= quantity;
    }
    @Override
    public boolean deductStock(Integer pid, Integer quantity){
        //check if product stock is enough for transaction, then deduct stock and return true
        ProductEntity productEntity = getEntityByPid(pid);
        if(productEntity.getStock() >= quantity){
            productEntity.setStock(productEntity.getStock() - quantity);
            productRepository.save(productEntity);
            return true;
        }
        return false;
    }
}
