package com.example.tgbot21.service;

import com.example.tgbot21.dto.ValuteCursOnDate;
import com.example.tgbot21.entity.ActiveChat;
import com.example.tgbot21.repository.ActiveChatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final ActiveChatRepository activeChatRepository;
    private final BotService botService;
    private final CentralRussianBankService centralRussianBankService;
    private final List<ValuteCursOnDate> previousRates = new ArrayList<>();

    @Scheduled(cron = "0 0 0/3 ? * *")
    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = centralRussianBankService.getCurrenciesFromCbr();
            Set<Long> activeChatIds = activeChatRepository.findAll().stream().map(ActiveChat::getChatId).collect(Collectors.toSet());
            if (!previousRates.isEmpty()) {
                for (int index = 0; index < currentRates.size(); index++) {
                    if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " увеличился на 10 рублей", activeChatIds);
                    } else if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " уменьшился на 10 рублей", activeChatIds);
                    }
                }
            } else {
                previousRates.addAll(currentRates);
            }
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            log.error("Возникла проблема при получении данных от сервисов ЦБ РФ", e);
        }
    }
}
