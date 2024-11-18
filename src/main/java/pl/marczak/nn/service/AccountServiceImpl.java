package pl.marczak.nn.service;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczak.nn.dto.AccountInfoDto;
import pl.marczak.nn.dto.CreatedAccountRequestDto;
import pl.marczak.nn.dto.CreatedAccountResponseDto;
import pl.marczak.nn.model.Account;
import pl.marczak.nn.model.Balance;
import pl.marczak.nn.model.Currency;
import pl.marczak.nn.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;


  @Override
  @Transactional
  public CreatedAccountResponseDto create(CreatedAccountRequestDto request) {
    Account created = accountRepository.save(Account.from(request));
    return new CreatedAccountResponseDto(created.getUuid());
  }

  @Override
  public Account fetchByUuid(String uuid) {
    return accountRepository.findAccountByUuid(uuid).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public AccountInfoDto fetchAccountInfo(String accountId) {
    Account account = accountRepository.findAccountByUuid(accountId).orElseThrow(EntityNotFoundException::new);
    return new AccountInfoDto(account.getUuid(), account.getCurrencyBalance(Currency.USD), account.getCurrencyBalance(Currency.PLN));
  }

  @Override
  public void updateAccountBalance(Account account, Currency currency, BigDecimal amount) {
    BigDecimal currentBalance = account.getCurrencyBalance(currency);
    BigDecimal updatedBalance = currentBalance.add(amount);

    if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Cannot update balance. Insufficient funds in currency: " + currency);
    }

    Balance curencyBalance = account.getBalances().stream().filter(balance -> currency.equals(balance.getCurrency()))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);

    curencyBalance.update(updatedBalance);

    accountRepository.save(account);
  }
}
