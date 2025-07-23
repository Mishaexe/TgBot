package com.example.tgbot21.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
}
