package com.fsse2401.project.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(Integer tid, String firebaseUid){
        super(String.format("Transaction not found - tid:%s, uid:%s", tid, firebaseUid));
    }
}