package com.fsse2401.project.data.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionSuccessResponseDto {
    public String result;
    public TransactionSuccessResponseDto() {
        setResult("SUCCESS");
    }
}