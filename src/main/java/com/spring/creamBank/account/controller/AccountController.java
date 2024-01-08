package com.spring.creamBank.account.controller;

import com.spring.creamBank.account.dto.*;
import com.spring.creamBank.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;


    @GetMapping
    public List<AccountResponse> findAccountList() {
        return accountService.findAccountList();
    }

    @PostMapping
    public AccountResponse createAccount(@RequestBody @Valid AccountCreate request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse findAccount(@PathVariable("accountNumber") String accountNumber) {
        return accountService.findAccount(accountNumber);
    }

    @PatchMapping("/{accountNumber}/deposit")
    public AccountResponse deposit(@PathVariable("accountNumber") String accountNumber, @RequestBody @Valid AccountDeposit request){
        /* AccountDeposit 객체에 이미 accountNumber를 가지고 있는데,,
        * 굳이 pathVariable, API경로르 위해 또 받아야 하나??*/
        return accountService.deposit(request);
    }
    @PatchMapping("/{accountNumber}/withdrawal")
    public AccountResponse withdraw(@PathVariable("accountNumber") String accountNumber, @RequestBody @Valid AccountWithdrawal request){
        return accountService.withdraw(request);
    }

    @PostMapping("/{accountNumber}/transfer")
    public AccountResponse transfer(@PathVariable("accountNumber") String accountNumber, @RequestBody @Valid AccountTransfer accountTransfer){
        return accountService.transfer(accountTransfer);
    }

}
