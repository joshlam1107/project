package com.fsse2401.project.data.transaction.domainObject;

import com.fsse2401.project.data.transaction.status.TransactionStatus;
import com.fsse2401.project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.fsse2401.project.data.user.UserResponseData;
import com.fsse2401.project.entity.TransactionEntity;
import com.fsse2401.project.entity.TransactionProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserResponseData getUser() {
        return user;
    }

    public void setUser(UserResponseData user) {
        this.user = user;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseData> getTransactionProductList() {
        return transactionProductList;
    }

    public void setTransactionProductList(List<TransactionProductEntity> entityList) {
        for (TransactionProductEntity transactionProductEntity : entityList) {
            this.transactionProductList.add(new TransactionProductResponseData(transactionProductEntity));
        }
    }
}
