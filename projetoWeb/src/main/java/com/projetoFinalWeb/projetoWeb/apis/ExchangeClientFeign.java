package com.projetoFinalWeb.projetoWeb.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchange-client", url = "https://economia.awesomeapi.com.br")
public interface ExchangeClientFeign {

    @GetMapping("/json/last/USD-BRL")
    ExchangeResponseDTO getExchange();
}
