package com.fsse2401.project.data.transaction.dto;

public class TransactionSuccessResponseDto {
    public String result;

    public TransactionSuccessResponseDto() {
        setResult("SUCCESS");
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
