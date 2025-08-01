package com.example.tgbot21.service;

import com.example.tgbot21.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatsRepository statsRepository;

    public int getCountOfIncomesThatGreaterThan(BigDecimal amount) {
        return statsRepository.getCountOfIncomesThatGreaterThan(amount);
    }
}
    