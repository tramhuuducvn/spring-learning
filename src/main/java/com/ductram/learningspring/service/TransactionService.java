package com.ductram.learningspring.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final BankAccountService bankAccountService;

    public boolean pay(String id) {
        bankAccountService.getBankInfo(id);
        return false;
    }
}
