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
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Balance> balances;


  public static Account from(CreatedAccountRequestDto source) {
    initialPlnBalance(source);

    return Account.builder()
        .uuid(UUID.randomUUID().toString())
        .name(source.name())
        .surname(source.surname())
        .balances(initialPlnBalance(source))
        .build();
  }

  private static List<Balance> initialPlnBalance(CreatedAccountRequestDto source) {
    Balance balance = Balance.of(Currency.PLN, source.balance());

    List<Balance> balances = new ArrayList<>();
    balances.add(balance);
    return balances;
  }

  public BigDecimal getCurrencyBalance(Currency lookingCurrency) {
    return balances.stream()
        .filter(balance -> lookingCurrency.equals(balance.getCurrency()))
        .findFirst()
        .map(Balance::getAmount)
        .orElse(BigDecimal.ZERO);
  }

}
