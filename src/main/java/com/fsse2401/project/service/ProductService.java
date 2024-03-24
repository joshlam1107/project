package com.fsse2401.project.service;

import com.fsse2401.project.data.product.domainObject.ProductResponseData;
import com.fsse2401.project.entity.ProductEntity;
import java.util.List;

public interface ProductService {
    ProductResponseData getProductByProductId (Integer pid);
    List<ProductResponseData> getAllProduct ();
    ProductEntity getEntityByPid (Integer pid);
    boolean deductStock(Integer pid, Integer quantity);
    boolean isValidQuantity (Integer pid, Integer quantity);
}