package com.example.tgbot21.controllers;

import com.example.tgbot21.dto.ValuteCursOnDate;
import com.example.tgbot21.service.CentralRussianBankService;
import com.example.tgbot21.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST-контроллер для получения данных о валютах.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency Controller", description = "Operations related to fetching currency data from the Central Russian Bank")
public class CurrencyController {

    private final CentralRussianBankService centralRussianBankService;
    private final StatService statService;

    @GetMapping("/getCurrencies")
    @Operation(
            summary = "Получить список валют",
            description = "Возвращает список всех доступных валют с текущими курсами от Центрального банка России",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка валют",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValuteCursOnDate.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )

    
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }

    @GetMapping("/getCurrencies/{code}")
    @Operation(
            summary = "Получение курса определенной валюты",
            description = "Возвращает курс определенной валюты на текущий день от Центрального банка России",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение валюты",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValuteCursOnDate.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Валюта не найдена",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ValuteCursOnDate getCurrencyByCode(@PathVariable String code) throws DatatypeConfigurationException {
        return centralRussianBankService.getCourseForCurrency(code);
    }

    @GetMapping("/getStats")
    @Operation(
            summary = "Получить статистику по пополнениям",
            description = "Возвращает количество пополнений, превышающих указанную сумму",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное выполнение запроса",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный формат суммы",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public int getStatsAboutIncomesThatGreater(@RequestParam("amount") BigDecimal amount) {
        return statService.getCountOfIncomesThatGreaterThan(amount);
    }

    @GetMapping("/stats")
    public void getStatsBetweenDatesWithAmount(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") Long amount) {


    }
}