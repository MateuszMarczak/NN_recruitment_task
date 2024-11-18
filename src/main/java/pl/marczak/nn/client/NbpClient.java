package pl.marczak.nn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.marczak.nn.client.dto.NbpExchangeRateResponse;

@FeignClient(name = "nbpClient", url = "https://api.nbp.pl/api/exchangerates/rates/A")
public interface NbpClient {

  @GetMapping("/{currency}")
  NbpExchangeRateResponse getExchangeRate(
      @PathVariable("currency") String currency,
      @RequestParam(name = "format", defaultValue = "json") String format
  );
}
