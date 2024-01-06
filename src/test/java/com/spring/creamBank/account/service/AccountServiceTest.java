package com.spring.creamBank.account.service;

import com.spring.creamBank.account.domain.Account;
import com.spring.creamBank.account.dto.AccountCreate;
import com.spring.creamBank.account.dto.AccountDeposit;
import com.spring.creamBank.account.dto.AccountResponse;
import com.spring.creamBank.account.dto.AccountWithdrawal;
import com.spring.creamBank.account.exception.AccountNotFound;
import com.spring.creamBank.account.exception.BalanceNotEnough;
import com.spring.creamBank.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void clean() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 계좌 가져오기")
    void findAccountList() throws Exception {
        //given
        List<Account> accounts = IntStream.range(1, 31)
                .mapToObj(i -> {
                    return Account.builder()
                            .accountNumber("계좌" + i)
                            .owner("주인" + i)
                            .balance(i * 10000)
                            .build();
                }).collect(Collectors.toList());
        //when
        accountRepository.saveAll(accounts);
        //then
        List<AccountResponse> accountList = accountService.findAccountList();
        assertEquals(accountList.size(), accounts.size());
    }

    @Test
    @DisplayName("계좌 단건 조회")
    void findAccount() throws Exception {
        //given
        AccountCreate acc = AccountCreate.builder()
                .accountNumber("0103322")
                .owner("홍길동")
                .balance(50000L)
                .build();
        //when
        AccountResponse accountResponse = accountService.createAccount(acc);
        //then
        AccountResponse correctResult = accountService.findAccount(accountResponse.getAccountNumber());

        assertEquals(acc.getAccountNumber(), correctResult.getAccountNumber());
        assertEquals(50000L, correctResult.getBalance());
        assertThrows(AccountNotFound.class, () -> {
            accountService.findAccount(accountResponse.getAccountNumber()+"2");

        });
    }

    @Test
    @DisplayName("계좌 생성")
    void createAccount() throws Exception{
        //given
        AccountCreate acc1 = AccountCreate.builder()
                .accountNumber("0103332")
                .owner("크림")
                .balance(300L)
                .build();

        AccountCreate acc2 = AccountCreate.builder()
                .accountNumber("0103332")
                .owner("크림")
                .build();
        //when
        AccountResponse accountResponse1 = accountService.createAccount(acc1);
        AccountResponse accountResponse2 = accountService.createAccount(acc2);
        //then
        assertEquals("0103332",accountResponse1.getAccountNumber());
        assertEquals("크림", accountResponse2.getOwner());
        assertEquals(2L, accountRepository.count());
    }

    @Test
    @DisplayName("입금 테스트")
    void deposit() throws Exception{
        //given
        AccountCreate acc = AccountCreate.builder()
                .accountNumber("0103332")
                .owner("크림")
                .balance(3000L)
                .build();
        AccountDeposit depositInfo = AccountDeposit.builder()
                .accountNumber("0103332")
                .owner("크림")
                .depositAmount(5000L)
                .build();
        //when
        AccountResponse accountResponse = accountService.createAccount(acc);
        accountService.deposit(depositInfo);
        //then
        assertEquals(8000L, accountService.findAccount(accountResponse.getAccountNumber()).getBalance());
    }
    @Test
    @DisplayName("출금 테스트")
    void withdraw() throws Exception{
        //given
        AccountCreate acc = AccountCreate.builder()
                .accountNumber("0103332")
                .owner("크림")
                .balance(3000L)
                .build();
        AccountWithdrawal depositInfo1 = AccountWithdrawal.builder()
                .accountNumber("0103332")
                .owner("크림")
                .withdrawalAmount(2000L)
                .build();
        AccountWithdrawal depositInfo2 = AccountWithdrawal.builder()
                .accountNumber("0103332")
                .owner("크림")
                .withdrawalAmount(5000L)
                .build();

        //when
        AccountResponse accountResponse = accountService.createAccount(acc);
        accountService.withdraw(depositInfo1);
        //then
        assertEquals(1000L, accountService.findAccount(accountResponse.getAccountNumber()).getBalance());
        assertThrows(BalanceNotEnough.class, () -> {
            accountService.withdraw(depositInfo2);
        });
    }


}