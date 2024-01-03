package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AccountCreate {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private final String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private final String owner;
    private final Long balance;

    @Builder
    public AccountCreate(String accountNumber, String owner, Long balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }


}
