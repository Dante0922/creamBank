package com.spring.creamBank.account.controller;

import com.spring.creamBank.account.domain.Account;
import com.spring.creamBank.account.dto.AccountCreate;
import com.spring.creamBank.account.dto.AccountUpdate;
import com.spring.creamBank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/accounts")
    public List<Account> getAccountList() {
        return accountService.getAccountList();
    }

    @PostMapping("/accounts")
    public String createAccount(@RequestBody AccountCreate request) {

        return accountService.create(request);
    }

    @GetMapping("/accounts/{accountId}")
    public Optional<Account> getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PatchMapping("/accounts/{accountId}")
    public String deposit(@PathVariable Long accountId, @RequestBody AccountUpdate request){
        AccountService.deposit(accountId, request)

    }
}
