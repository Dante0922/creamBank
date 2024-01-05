package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountCreate {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String accountNumber;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private String owner;
    private Long balance;

    @Builder
    public AccountCreate(String accountNumber, String owner, Long balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }


}
