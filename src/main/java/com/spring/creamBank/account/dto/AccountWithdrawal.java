package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountWithdraw {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private  String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private  String owner;

    @NotBlank(message = "출금할 금액을 입력해주세요.")
    private  Long withdrawalAmount;

    @Builder
    public AccountWithdraw(String accountNumber, String owner, Long withdrawalAmount) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.withdrawalAmount = withdrawalAmount;
    }
}
