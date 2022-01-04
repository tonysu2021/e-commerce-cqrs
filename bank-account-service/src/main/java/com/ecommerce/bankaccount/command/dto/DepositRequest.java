package com.ecommerce.bankaccount.command.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequest {

    private String accountId;
    private BigDecimal amount;
}
