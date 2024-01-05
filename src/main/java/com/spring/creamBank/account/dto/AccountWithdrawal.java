package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountWithdrawal {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private String owner;
    @NotBlank(message = "출금할 금액을 입력해주세요.")
    private Long withdrawalAmount;

    @Builder
    public AccountWithdrawal(String accountNumber, String owner, Long withdrawalAmount) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.withdrawalAmount = withdrawalAmount;
    }
}
