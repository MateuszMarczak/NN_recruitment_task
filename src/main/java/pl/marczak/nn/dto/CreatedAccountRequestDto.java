package pl.marczak.nn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreatedAccountRequestDto(@NotBlank String name, @NotBlank String surname, @Positive BigDecimal balance) {

}
