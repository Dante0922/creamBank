package com.spring.creamBank.account.service;

import com.spring.creamBank.account.domain.Account;
import com.spring.creamBank.account.dto.*;
import com.spring.creamBank.account.exception.AccountNotFound;
import com.spring.creamBank.account.exception.BalanceNotEnough;
import com.spring.creamBank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<AccountResponse> findAccountList() {
        return accountRepository.findAll().stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    public AccountResponse findAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFound::new);
        return new  AccountResponse(account);
    }

    public AccountResponse create(AccountCreate accountCreate) {
        Account acc = Account.builder()
                .accountNumber(accountCreate.getAccountNumber())
                .owner(accountCreate.getOwner())
                .balance(accountCreate.getBalance() != null ? accountCreate.getBalance() : 0L)
                .build();

        Account createdAcc = accountRepository.save(acc);
        return new AccountResponse(createdAcc);
    }

    public AccountResponse deposit(AccountDeposit request) {
        Account acc = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(AccountNotFound::new);

        acc.addBalance(request.getDepositAmount());
        accountRepository.save(acc);

        return new AccountResponse(acc);
    }

    public AccountResponse withdraw(AccountWithdrawal request) {
        Account acc = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(AccountNotFound::new);
        if(acc.getBalance() < request.getWithdrawalAmount()){
            throw new BalanceNotEnough();
        }

        acc.deductBalance(request.getWithdrawalAmount());
        accountRepository.save(acc);
        return new AccountResponse(acc);
    }

    @Transactional
    public AccountResponse transfer(AccountTransfer accountTransfer) {
        Account senderAcc = accountRepository.findByAccountNumber(accountTransfer.getSenderAccount()).orElseThrow(AccountNotFound::new);
        Account recipientAcc = accountRepository.findByAccountNumber(accountTransfer.getRecipientAccount()).orElseThrow(AccountNotFound::new);

        senderAcc.deductBalance(accountTransfer.getTransferAmount());
        recipientAcc.addBalance(accountTransfer.getTransferAmount());

        accountRepository.save(senderAcc);
        accountRepository.save(recipientAcc);

        return new AccountResponse(senderAcc);

    }
}
