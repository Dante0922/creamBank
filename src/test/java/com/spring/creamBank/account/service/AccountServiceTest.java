package com.spring.creamBank.account.service;

import com.spring.creamBank.account.domain.Account;
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
    void clean() { accountRepository.deleteAll();}


    @Test
    @DisplayName("모든 계좌 가져오기")
    void getAccountList() throws Exception{
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
        List<Account> accountList = accountRepository.findAll();

        assertEquals(accountList.size(), accounts.size());

    }
}