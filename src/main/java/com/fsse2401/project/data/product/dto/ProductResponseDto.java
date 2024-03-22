package com.fsse2401.project.data.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project.data.product.domainObject.ProductResponseData;
import com.fsse2401.project.data.transactionProduct.domainObject.TransactionProductResponseData;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class ProductResponseDto {
    private Integer pid;
    private String name;
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;

    public ProductResponseDto(ProductResponseData data) {
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
    }
    public ProductResponseDto(TransactionProductResponseData data){
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
    }
}
