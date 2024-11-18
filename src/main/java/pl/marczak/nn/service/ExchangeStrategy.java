package pl.marczak.nn.service;

import java.math.BigDecimal;

@FunctionalInterface
public interface ExchangeStrategy {
    BigDecimal exchange(BigDecimal amount, BigDecimal rate);
}