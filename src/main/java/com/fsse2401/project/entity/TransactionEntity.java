package com.fsse2401.project.entity;

import com.fsse2401.project.data.transaction.status.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "transaction")
@Getter @Setter @NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;
    @ManyToOne
    @JoinColumn(name = "buyer_uid", nullable = false)
    private UserEntity user;
    @Column(nullable = false)
    private LocalDateTime datetime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(nullable = false)
    private BigDecimal total;
    @OneToMany(mappedBy = "transactionInfo")
    private List<TransactionProductEntity> transactionProductList = new ArrayList<>();

    public TransactionEntity (UserEntity user, TransactionStatus status){
        this.user = user;
        this.datetime = LocalDateTime.now();
        this.status = status;
        this.total = BigDecimal.ZERO;
    }
}
