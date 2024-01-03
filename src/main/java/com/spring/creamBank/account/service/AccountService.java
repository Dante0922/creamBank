package com.spring.creamBank.account.service;

import com.spring.creamBank.account.domain.Account;
import com.spring.creamBank.account.dto.AccountCreate;
import com.spring.creamBank.account.dto.AccountUpdate;
import com.spring.creamBank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> getAccountList() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public String create(AccountCreate accountCreate) {

        Account acc = Account.builder()
                .accountNumber(accountCreate.getAccountNumber())
                .owner(accountCreate.getOwner())
                .balance(accountCreate.getBalance())
                .build();

        accountRepository.save(acc);
        return "성공";
    }

    public void deposit(Long accountId, AccountUpdate request) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(RuntimeException::new);
        acc.addBalance(request.getBalance());
        // 객체로 받아온 것의 금액으로 변경할 것인가,, 아니면 따로 변수로 받아서 add 할 것인가..


    }
}
