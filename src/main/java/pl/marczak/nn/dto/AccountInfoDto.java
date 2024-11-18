package pl.marczak.nn.dto;

import java.math.BigDecimal;

public record AccountInfoDto(String uuid, BigDecimal usdBalance, BigDecimal plnBalance) {

}
