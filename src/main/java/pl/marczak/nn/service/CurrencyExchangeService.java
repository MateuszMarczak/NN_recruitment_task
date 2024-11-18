package pl.marczak.nn.service;

import pl.marczak.nn.dto.ExchangeRequestDto;

public interface CurrencyExchangeService {

  void exchange(ExchangeRequestDto request);
}
