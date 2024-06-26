package com.fsse2401.project.exception.transaction;

import com.fsse2401.project.data.transaction.status.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionStatusException extends RuntimeException{
    public TransactionStatusException (TransactionStatus transactionStatus){
        super (String.format("Current transaction status is %s, cannot proceed", transactionStatus));
    }
}