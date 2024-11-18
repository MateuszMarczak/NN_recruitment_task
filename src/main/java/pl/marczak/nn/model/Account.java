package pl.marczak.nn.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.marczak.nn.dto.CreatedAccountRequestDto;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String uuid;
  private String name;
  private String surname;
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Balance> balances;


  public static Account from(CreatedAccountRequestDto source) {

    Account account = Account.builder()
        .uuid(UUID.randomUUID().toString())
        .name(source.name())
        .surname(source.surname())
        .build();

    account.balances = new ArrayList<>();
    account.balances.add(initialPlnBalance(source, account));
    account.balances.add(initialUSDBalance(source, account));

    return account;
  }

  private static Balance initialUSDBalance(CreatedAccountRequestDto source, Account account) {
    return Balance.of(Currency.USD, BigDecimal.ZERO, account);
  }

  private static Balance initialPlnBalance(CreatedAccountRequestDto source, Account account) {
    return Balance.of(Currency.PLN, source.balance(), account);
  }


  public BigDecimal getCurrencyBalance(Currency lookingCurrency) {
    return balances.stream()
        .filter(balance -> lookingCurrency.equals(balance.getCurrency()))
        .findFirst()
        .map(Balance::getAmount)
        .orElse(BigDecimal.ZERO);
  }

  public void sellCurrency(Currency currency, BigDecimal amount) {
    balances.stream()
        .filter(balance -> currency.equals(balance.getCurrency()))
        .findFirst()
        .ifPresent(balance -> balance.sell(amount));

  }

  public void buyCurrency(Currency currency, BigDecimal amount) {
    balances.stream()
        .filter(balance -> currency.equals(balance.getCurrency()))
        .findFirst()
        .ifPresent(balance -> balance.buy(amount));
  }


}
