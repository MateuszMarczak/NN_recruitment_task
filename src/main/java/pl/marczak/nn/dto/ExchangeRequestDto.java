package pl.marczak.nn.dto;

import java.math.BigDecimal;
import pl.marczak.nn.model.Currency;

public record ExchangeRequestDto(String accountUUID, Currency from, Currency to, BigDecimal amount) {

}
