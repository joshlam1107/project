package com.fsse2401.project.api;

import com.fsse2401.project.data.product.domainObject.ProductResponseData;
import com.fsse2401.project.data.product.dto.GetAllProductResponseDto;
import com.fsse2401.project.data.product.dto.ProductResponseDto;
import com.fsse2401.project.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/public/product")
public class ProductApi {
    private final ProductService productService;

    public ProductApi (ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<GetAllProductResponseDto> getAllProduct (){
        List<GetAllProductResponseDto> responseDtoList = new ArrayList<>();
        for (ProductResponseData responseData : productService.getAllProduct()){
            responseDtoList.add(new GetAllProductResponseDto(responseData));
        }
        return responseDtoList;
    }

    @GetMapping("/{pid}")
    public ProductResponseDto getProductByProductId (@PathVariable Integer pid){
        ProductResponseData responseData = productService.getProductByProductId(pid);
        return new ProductResponseDto(responseData);
    }
}
