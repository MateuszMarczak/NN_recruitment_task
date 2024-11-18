package pl.marczak.nn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marczak.nn.dto.ExchangeRequestDto;
import pl.marczak.nn.service.CurrencyExchangeService;

@RestController
@RequestMapping("api/v1/exchanges")
@RequiredArgsConstructor
class ExchangeController {

  private final CurrencyExchangeService currencyExchangeService;

  @PostMapping
  ResponseEntity<?> exchange(@RequestBody ExchangeRequestDto request) {
    currencyExchangeService.exchange(request);
    return ResponseEntity.accepted().build();
  }


}

