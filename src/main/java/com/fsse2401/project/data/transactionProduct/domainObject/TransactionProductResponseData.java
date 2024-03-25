package com.fsse2401.project.data.transactionProduct.domainObject;

import com.fsse2401.project.entity.TransactionProductEntity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class TransactionProductResponseData {
    private Integer tpid;
    private Integer pid;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;
    private Integer quantity;

    public TransactionProductResponseData(TransactionProductEntity entity) {
        this.tpid = entity.getTpid();
        this.pid = entity.getPid();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
        this.price = entity.getPrice();
        this.stock = entity.getStock();
        this.quantity = entity.getQuantity();
    }
}