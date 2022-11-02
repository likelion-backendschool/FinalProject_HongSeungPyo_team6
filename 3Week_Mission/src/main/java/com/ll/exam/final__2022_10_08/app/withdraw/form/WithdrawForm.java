package com.ll.exam.final__2022_10_08.app.withdraw.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WithdrawForm {
    @NotBlank
    private String bankName;
    @NotBlank
    private String bankAccountNo;
    @NotBlank
    private String price;
}
