package com.spring.creamBank.account.dto;

import com.spring.creamBank.account.domain.Account;
import lombok.*;

@ToString
@Getter
@Setter
public class AccountResponse {
    private final Long id;
    private final String accountNumber;
    private final String owner;
    private final double balance;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.owner = account.getOwner();
        this.balance = account.getBalance();
    }

    @Builder
    public AccountResponse(Long id, String accountNumber, String owner, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }
}
