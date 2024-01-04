package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AccountDeposit {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private  String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private  String owner;

    @NotBlank(message = "예금할 금액을 입력해주세요.")
    private  Long deposit;

    @Builder
    public AccountDeposit(String accountNumber, String owner, Long deposit) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.deposit = deposit;
    }
}
