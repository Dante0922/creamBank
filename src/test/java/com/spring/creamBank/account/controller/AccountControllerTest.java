package com.spring.creamBank.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.creamBank.account.domain.Account;
import com.spring.creamBank.account.dto.AccountCreate;
import com.spring.creamBank.account.dto.AccountDeposit;
import com.spring.creamBank.account.dto.AccountTransfer;
import com.spring.creamBank.account.dto.AccountWithdrawal;
import com.spring.creamBank.account.exception.AccountNotFound;
import com.spring.creamBank.account.repository.AccountRepository;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void clean() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 계좌를 불러온다.")
    void findAccountList() throws Exception {
        //given
        List<Account> accountStream = IntStream.range(1, 11)
                .mapToObj(i -> {
                    return Account.builder()
                            .accountNumber("010" + i)
                            .owner("계좌" + i)
                            .balance(i)
                            .build();
                }).toList();
        //when
        accountRepository.saveAll(accountStream);
        //then
        mockMvc.perform(get("/accounts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andDo(print());
    }

    @Test
    @DisplayName("계좌 단건 조회")
    void findAccount() throws Exception {
        //given
        Account acc = Account.builder()
                .accountNumber("0102311")
                .owner("크림")
                .balance(50000L)
                .build();
        //when
        Account savedAcc = accountRepository.save(acc);

        //then
        mockMvc.perform(get("/accounts/{accountNumber}", savedAcc.getAccountNumber())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("0102311"))
                .andExpect(jsonPath("$.owner").value("크림"))
                .andExpect(jsonPath("$.balance").value(50000L))
                .andDo(print());
    }

    @Test
    void createAccount() throws Exception {
        //given
        AccountCreate acc = AccountCreate.builder()
                .accountNumber("0102311")
                .owner("크림")
                .balance(50000L)
                .build();

        //when
        String json = objectMapper.writeValueAsString(acc);

        mockMvc.perform(post("/accounts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        Account account = accountRepository.findAll().get(0);

        //then
        assertEquals("0102311", account.getAccountNumber());
        assertEquals("크림", account.getOwner());
        assertEquals(50000L, account.getBalance());
    }

    @Test
    void deposit() throws Exception{
        //given
        Account acc = Account.builder()
                .accountNumber("0102311")
                .owner("크림")
                .balance(50000L)
                .build();
        accountRepository.save(acc);

        AccountDeposit deposit = AccountDeposit.builder()
                .accountNumber("0102311")
                .owner("크림")
                .depositAmount(5000000L)
                .build();

        //when
        String json = objectMapper.writeValueAsString(deposit);

        mockMvc.perform(patch("/accounts/{accountNumber}/deposit", acc.getAccountNumber())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        Account result = accountRepository.findById(acc.getId()).orElseThrow(AccountNotFound::new);
        assertEquals(5050000L, result.getBalance());
    }
    @Test
    void withdrawal() throws Exception{
        //given
        Account acc = Account.builder()
                .accountNumber("0102311")
                .owner("크림")
                .balance(50000L)
                .build();
        accountRepository.save(acc);

        AccountWithdrawal withdrawal1 = AccountWithdrawal.builder()
                .accountNumber("0102311")
                .owner("크림")
                .withdrawalAmount(5000000L)
                .build();
        AccountWithdrawal withdrawal2 = AccountWithdrawal.builder()
                .accountNumber("0102311")
                .owner("크림")
                .withdrawalAmount(30000L)
                .build();

        //when
        String json1 = objectMapper.writeValueAsString(withdrawal1);
        String json2 = objectMapper.writeValueAsString(withdrawal2);

        //then
        mockMvc.perform(patch("/accounts/{accountNumber}/withdrawal", acc.getAccountNumber())
                        .contentType(APPLICATION_JSON)
                        .content(json1))
                .andDo(print())
                .andExpect(status().isForbidden());
        mockMvc.perform(patch("/accounts/{accountNumber}/withdrawal", acc.getAccountNumber())
                        .contentType(APPLICATION_JSON)
                        .content(json2))
                .andDo(print())
                .andExpect(status().isOk());

        Account result = accountRepository.findById(acc.getId()).orElseThrow(AccountNotFound::new);
        assertEquals(20000L, result.getBalance());
    }

    @Test
    void transfer() throws Exception{
        //given
        Account acc1 = Account.builder()
                .accountNumber("0102311")
                .owner("크림")
                .balance(10000L)
                .build();
        Account acc2 = Account.builder()
                .accountNumber("0109981")
                .owner("햄스터")
                .balance(5000L)
                .build();
        accountRepository.save(acc1);
        accountRepository.save(acc2);

        AccountTransfer transferDTO = AccountTransfer.builder()
                .senderAccount(acc1.getAccountNumber())
                .senderName(acc1.getOwner())
                .recipientAccount(acc2.getAccountNumber())
                .recipientName(acc2.getOwner())
                .transferAmount(3000L)
                .build();

        String json = objectMapper.writeValueAsString(transferDTO);

        //when
        mockMvc.perform(post("/accounts/{accountNumber}/transfer", acc1.getAccountNumber())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        Account result1 = accountRepository.findById(acc1.getId()).orElseThrow(AccountNotFound::new);
        Account result2 = accountRepository.findById(acc2.getId()).orElseThrow(AccountNotFound::new);

        assertEquals(7000L, result1.getBalance());
        assertEquals(8000L, result2.getBalance());

    }
}
