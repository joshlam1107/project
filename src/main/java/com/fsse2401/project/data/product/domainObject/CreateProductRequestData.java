package com.fsse2401.project.data.product.domainObject;

import com.fsse2401.project.data.product.dto.CreateProductRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class CreateProductRequestData {
    private Integer pid;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;

    public CreateProductRequestData(CreateProductRequestDto dto) {
        this.pid = dto.getPid();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
    }
}
