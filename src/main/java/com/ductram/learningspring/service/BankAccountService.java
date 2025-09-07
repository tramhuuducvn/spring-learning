package com.ductram.learningspring.service;

import org.springframework.stereotype.Service;

import com.ductram.learningspring.aop.annotation.BankAccountLog;

@Service
public class BankAccountService {

    @BankAccountLog
    public String getBankInfo(String id) {
        return "Bank Info of ID = " + id;
    }
}
