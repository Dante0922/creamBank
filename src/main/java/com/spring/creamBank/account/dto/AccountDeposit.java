package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDeposit {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private String owner;
    @NotBlank(message = "예금금액을 입력해주세요.")
    private Long depositAmount;

    @Builder
    public AccountDeposit(String accountNumber, String owner, Long depositAmount) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.depositAmount = depositAmount;
    }
}
