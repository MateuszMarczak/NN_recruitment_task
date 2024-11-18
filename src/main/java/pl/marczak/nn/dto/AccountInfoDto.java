package pl.marczak.nn.controller;

import java.math.BigDecimal;

public record AccountInfoDto(String uuid, BigDecimal usdBalance, BigDecimal plnBalance) {

}
