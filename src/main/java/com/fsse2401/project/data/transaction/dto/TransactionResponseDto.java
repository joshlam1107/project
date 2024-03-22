package com.fsse2401.project.data.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fsse2401.project.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project.data.transaction.status.TransactionStatus;
import com.fsse2401.project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.fsse2401.project.data.transactionProduct.dto.TransactionProductResponseDto;
import com.fsse2401.project.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({"tid", "buyer_uid", "datetime", "status", "total", "items"})
public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer buyerUid;
    @JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss")
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseDto> items = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data) {
        this.tid = data.getTid();
        this.buyerUid = data.getUser().getUid();
        this.datetime = data.getDatetime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        setItems(data);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(Integer buyerUid) {
        this.buyerUid = buyerUid;
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

    public List<TransactionProductResponseDto> getItems() {
        return items;
    }

    public void setItems(TransactionResponseData data) {
        for (TransactionProductResponseData responseData : data.getTransactionProductList()){
            this.items.add(new TransactionProductResponseDto(responseData));
        }
    }
}
