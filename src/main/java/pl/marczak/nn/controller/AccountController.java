package pl.marczak.nn.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marczak.nn.dto.AccountInfoDto;
import pl.marczak.nn.dto.CreatedAccountRequestDto;
import pl.marczak.nn.dto.CreatedAccountResponseDto;
import pl.marczak.nn.service.AccountService;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
class AccountController {

  private final AccountService accountService;

  @GetMapping
  ResponseEntity<CreatedAccountResponseDto> createAccount(@RequestBody @Validated CreatedAccountRequestDto request, UriComponentsBuilder uriComponentsBuilder) {
    log.info("Creating account: {}", request);
    CreatedAccountResponseDto response = accountService.create(request);

    URI location = uriComponentsBuilder
        .path("/api/accounts/{id}")
        .buildAndExpand(response.uuid())
        .toUri();

    log.info("Created account: {}", response);
    return ResponseEntity.created(location).body(response);
  }

  @GetMapping("/{accountId}")
  ResponseEntity<AccountInfoDto> getAccount(@PathVariable String accountId) {
    log.info("Fetching account info for account: {}", accountId);
    return ResponseEntity.ok(accountService.fetchAccountInfo(accountId));
  }


}
