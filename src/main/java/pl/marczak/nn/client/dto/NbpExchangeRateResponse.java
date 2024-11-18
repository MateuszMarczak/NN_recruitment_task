package pl.marczak.nn.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record NbpExchangeRateResponse(
    String table,
    String currency,
    String code,
    List<Rate> rates
) {

  public record Rate(
      String no,
      String effectiveDate,
      BigDecimal mid
  ) {

  }
}
