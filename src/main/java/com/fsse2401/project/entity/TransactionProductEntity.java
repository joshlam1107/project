package com.fsse2401.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Entity(name = "transaction_product")
@Getter @Setter @NoArgsConstructor
public class TransactionProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpid;
    @ManyToOne
    @JoinColumn(name = "tid", nullable = false)
    private TransactionEntity transactionInfo;
    @Column(nullable = false)
    private Integer pid;
    @Column(nullable = false)
    private String name;
    private String description;
    private String imageUrl;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer quantity;

    public TransactionProductEntity(TransactionEntity transactionInfo, CartItemEntity cartItemEntity) {
        this.transactionInfo = transactionInfo;
        this.pid = cartItemEntity.getProduct().getPid();
        this.name = cartItemEntity.getProduct().getName();
        this.description = cartItemEntity.getProduct().getDescription();
        this.imageUrl = cartItemEntity.getProduct().getImageUrl();
        this.price = cartItemEntity.getProduct().getPrice();
        this.stock = cartItemEntity.getProduct().getStock();
        this.quantity = cartItemEntity.getQuantity();
    }
}
