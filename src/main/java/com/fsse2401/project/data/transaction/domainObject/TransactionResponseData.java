package com.fsse2401.project.data.transaction.domainObject;

import com.fsse2401.project.data.transaction.status.TransactionStatus;
import com.fsse2401.project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.fsse2401.project.data.user.UserResponseData;
import com.fsse2401.project.entity.TransactionEntity;
import com.fsse2401.project.entity.TransactionProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TransactionResponseData {
    private Integer tid;
    private UserResponseData user;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseData> transactionProductList = new ArrayList<>();

    public TransactionResponseData(TransactionEntity entity, List<TransactionProductEntity> transactionProductEntityList) {
        this.tid = entity.getTid();
        this.user = new UserResponseData(entity.getUser());
        this.datetime = entity.getDatetime();
        this.status = entity.getStatus();
        this.total = entity.getTotal();
        setTransactionProductList(transactionProductEntityList);
    }
    public void setTransactionProductList(List<TransactionProductEntity> entityList) {
        for (TransactionProductEntity transactionProductEntity : entityList) {
            this.transactionProductList.add(new TransactionProductResponseData(transactionProductEntity));
        }
    }
}
