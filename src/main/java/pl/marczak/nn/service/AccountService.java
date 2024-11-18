package pl.marczak.nn.service;

import java.math.BigDecimal;
import pl.marczak.nn.dto.AccountInfoDto;
import pl.marczak.nn.dto.CreatedAccountRequestDto;
import pl.marczak.nn.dto.CreatedAccountResponseDto;
import pl.marczak.nn.model.Account;
import pl.marczak.nn.model.Currency;

public interface AccountService {

  CreatedAccountResponseDto create(CreatedAccountRequestDto request);

  Account fetchByUuid(String s);

  AccountInfoDto fetchAccountInfo(String accountId);


  void updateAccountBalance(Account account, Currency currency, BigDecimal amount);
}
