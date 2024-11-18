package pl.marczak.nn.dto;

import java.math.BigDecimal;
import pl.marczak.nn.model.Currency;
import pl.marczak.nn.model.TransactionType;

public record ExchangeRequestDto(String accountUUID, Currency currency, TransactionType transactionType, BigDecimal amount) {

}
