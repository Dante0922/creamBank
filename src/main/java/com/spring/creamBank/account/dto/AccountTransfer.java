package com.spring.creamBank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class AccountTransfer {


    @NotBlank(message = "송금계좌번호를 입력해주세요.")
    private String senderAccount;
    @NotBlank(message = "예금주명을 입력해주세요.")
    private String senderName;
    @NotBlank(message = "수신계좌을 입력해주세요.")
    private String recipientAccount;
    @NotBlank(message = "수신인명을 입력해주세요.")
    private String recipientName;
    @NotBlank(message = "송금할 금액을 입력해주세요.")
    private Long transferAmount;

    @Builder
    public AccountTransfer(String senderAccount, String senderName, String recipientAccount, String recipientName, Long transferAmount) {
        this.senderAccount = senderAccount;
        this.senderName = senderName;
        this.recipientAccount = recipientAccount;
        this.recipientName = recipientName;
        this.transferAmount = transferAmount;
    }
}
