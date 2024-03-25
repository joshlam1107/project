package com.fsse2401.project.data.transactionProduct.dto;

import com.fsse2401.project.data.product.dto.ProductResponseDto;
import com.fsse2401.project.data.transactionProduct.domainObject.TransactionProductResponseData;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class TransactionProductResponseDto {
    private Integer tpid;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal subtotal;

    public TransactionProductResponseDto(TransactionProductResponseData data) {
        this.tpid = data.getTpid();
        this.product = new ProductResponseDto(data);
        this.quantity = data.getQuantity();
        this.subtotal = data.getPrice().multiply(new BigDecimal(data.getQuantity()));
    }
}