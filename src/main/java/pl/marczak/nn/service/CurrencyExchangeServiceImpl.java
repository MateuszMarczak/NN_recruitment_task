package pl.marczak.nn.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczak.nn.client.NbpClient;
import pl.marczak.nn.client.dto.NbpExchangeRateResponse;
import pl.marczak.nn.dto.ExchangeRequestDto;
import pl.marczak.nn.model.Account;
import pl.marczak.nn.model.Currency;
import pl.marczak.nn.model.TransactionType;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

  public static final String JSON = "json";
  private final AccountService accountService;
  private final NbpClient nbpClient;

  @Override
  @Transactional
  public void exchange(ExchangeRequestDto request) {
    Account account = accountService.fetchByUuid(request.accountUUID());


    NbpExchangeRateResponse rateResponse = nbpClient.getExchangeRate(request.currency().name(), JSON);
    BigDecimal exchangeRate = request.transactionType().equals(TransactionType.BUY) ? rateResponse.rates().getFirst().ask() : rateResponse.rates().getFirst().bid();
    BigDecimal amountToExchange = request.amount(); //10 usd kupic

    if (TransactionType.BUY.equals(request.transactionType())) {
      account.sellCurrency(Currency.PLN, amountToExchange.multiply(exchangeRate));
      account.buyCurrency(request.currency(), amountToExchange);
    }
    else {
      account.buyCurrency(Currency.PLN, amountToExchange.multiply(exchangeRate));
      account.sellCurrency(request.currency(), amountToExchange);
    }

  }



}

