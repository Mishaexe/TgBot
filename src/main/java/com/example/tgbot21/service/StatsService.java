package com.example.tgbot21.service;

import com.example.tgbot21.dto.TransactionDTO;
import com.example.tgbot21.entity.Transaction;
import com.example.tgbot21.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final TransactionRepository transactionRepository;

    public List<TransactionDTO> getStats(LocalDate from, LocalDate to, Long amount) {
        List<Transaction> transactions = transactionRepository.findByDateBetween(from, to);

        return transactions.stream()
                .filter(t -> t.getAmount().longValueExact() > amount)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        return dto;
    }
}