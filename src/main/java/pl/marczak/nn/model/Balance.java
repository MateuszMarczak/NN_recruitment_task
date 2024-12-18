package pl.marczak.nn.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Balance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private Currency currency;
  private BigDecimal amount;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  public static Balance of(Currency currency, BigDecimal amount, Account account) {
    return Balance.builder()
        .currency(currency)
        .amount(amount)
        .account(account)
        .build();
  }

  public void update(BigDecimal updatedBalance) {
    this.amount = updatedBalance;
  }


  void sell(BigDecimal amount) {
    if (this.amount.compareTo(amount) < 0) {
      throw new IllegalStateException("Not enough money to sell");
    }
    this.amount = this.amount.subtract(amount);
  }

  void buy(BigDecimal amount) {
    this.amount = this.amount.add(amount);
  }
}
