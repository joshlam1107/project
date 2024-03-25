package com.fsse2401.project.data.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project.data.product.domainObject.ProductResponseData;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class GetAllProductResponseDto {
    private Integer pid;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    private BigDecimal price;
    @JsonProperty("has_stock")
    private boolean hasStock;

    public GetAllProductResponseDto(ProductResponseData data) {
        this.pid = data.getPid();
        this.name = data.getName();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        //return true when stock is available
        this.hasStock = data.getStock() > 0;
    }
}