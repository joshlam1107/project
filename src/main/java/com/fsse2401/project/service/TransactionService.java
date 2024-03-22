package com.fsse2401.project.service;

import com.fsse2401.project.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project.data.user.domainObject.FirebaseUserData;

public interface TransactionService {
    TransactionResponseData createTransaction (FirebaseUserData firebaseUserData);
    TransactionResponseData getTransactionByTid (FirebaseUserData firebaseUserData, Integer tid);
    boolean payTransaction (FirebaseUserData firebaseUserData, Integer tid);
    TransactionResponseData finishTransaction (FirebaseUserData firebaseUserData, Integer tid);
}
