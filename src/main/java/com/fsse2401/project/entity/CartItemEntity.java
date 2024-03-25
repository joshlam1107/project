package com.fsse2401.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name = "cart_item")
@Getter @Setter @NoArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer quantity;

    public CartItemEntity(ProductEntity product, UserEntity user, Integer quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }
}