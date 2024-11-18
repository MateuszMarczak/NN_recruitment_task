package pl.marczak.nn.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marczak.nn.client.NbpClient;
import pl.marczak.nn.client.dto.NbpExchangeRateResponse;
import pl.marczak.nn.dto.ExchangeRequestDto;
import pl.marczak.nn.model.Account;
import pl.marczak.nn.model.Currency;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

  public static final String JSON = "json";
  private final AccountService accountService;
  private final NbpClient nbpClient;

  @Override
  public void exchange(ExchangeRequestDto request) {
    Account account = accountService.fetchByUuid(request.accountUUID());

    BigDecimal exchangeRate = getCurrentExchangeRate(request.from(), request.to());
    BigDecimal convertedAmount = calculateConvertedAmount(request.from(), request.to(), request.amount(), exchangeRate);

    if (account.getCurrencyBalance(request.from()).compareTo(request.amount()) < 0) {
      throw new IllegalArgumentException("Insufficient funds in " + request.from() + " currency");
    }

    accountService.updateAccountBalance(account, request.from(), request.amount().negate());
    accountService.updateAccountBalance(account, request.to(), convertedAmount);
  }


  private BigDecimal getCurrentExchangeRate(Currency from, Currency to) {
    NbpExchangeRateResponse response = nbpClient.getExchangeRate(Currency.USD.name(), "json");

    if (response == null || response.rates().isEmpty()) {
      throw new IllegalStateException("Unable to fetch exchange rate from NBP API");
    }

    BigDecimal midRate = response.rates().getFirst().mid();

    if (from == Currency.PLN && to == Currency.USD) {
      return midRate;
    } else if (from == Currency.USD && to == Currency.PLN) {
      return BigDecimal.ONE.divide(midRate, 4, RoundingMode.HALF_UP);
    } else {
      throw new IllegalArgumentException("Unsupported currency pair: " + from + " to " + to);
    }
  }

  private BigDecimal calculateConvertedAmount(Currency from, Currency to, BigDecimal amount, BigDecimal exchangeRate) {
    return amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
  }
}
