package com.fsse2401.project.api;

import com.fsse2401.project.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project.data.transaction.dto.TransactionResponseDto;
import com.fsse2401.project.data.transaction.dto.TransactionSuccessResponseDto;
import com.fsse2401.project.service.TransactionService;
import com.fsse2401.project.util.JwtUtil;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {
    private final TransactionService transactionService;
    public TransactionApi(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createTransaction (JwtAuthenticationToken jwtToken){
        TransactionResponseData responseData = transactionService.createTransaction(JwtUtil.getFirebaseUserData(jwtToken));
        return new TransactionResponseDto(responseData);
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getTransactionByTid (JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        TransactionResponseData responseData = transactionService.getTransactionByTid(JwtUtil.getFirebaseUserData(jwtToken), tid);
        return new TransactionResponseDto(responseData);
    }

    @PatchMapping("/{tid}/pay")
    public TransactionSuccessResponseDto payTransaction (JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        transactionService.payTransaction(JwtUtil.getFirebaseUserData(jwtToken), tid);
        return new TransactionSuccessResponseDto();
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto finishTransaction (JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        TransactionResponseData responseData = transactionService.finishTransaction(JwtUtil.getFirebaseUserData(jwtToken), tid);
        return new TransactionResponseDto(responseData);
    }
}
