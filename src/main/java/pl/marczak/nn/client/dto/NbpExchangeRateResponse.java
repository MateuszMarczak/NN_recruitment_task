package pl.marczak.nn.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record NbpExchangeRateResponse(
    String table,
    String currency,
    String code,
    List<Rate> rates
) {
  public static record Rate(
      String no,
      LocalDate effectiveDate,
      BigDecimal bid,
      BigDecimal ask
  ) {}
}