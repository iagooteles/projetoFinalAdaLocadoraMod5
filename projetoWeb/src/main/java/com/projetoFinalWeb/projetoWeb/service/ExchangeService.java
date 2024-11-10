package com.projetoFinalWeb.projetoWeb.service;

import com.projetoFinalWeb.projetoWeb.apis.ExchangeClientFeign;
import com.projetoFinalWeb.projetoWeb.apis.ExchangeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {

    private final ExchangeClientFeign exchangeClientFeign;

    @Autowired
    public ExchangeService(ExchangeClientFeign exchangeClientFeign) {
        this.exchangeClientFeign = exchangeClientFeign;
    }

    public BigDecimal getConvertedPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        ExchangeResponseDTO exchangeResponse = exchangeClientFeign.getExchange();

        if (exchangeResponse == null || exchangeResponse.getUsdBrl() == null) {
            throw new IllegalStateException("Exchange rate not available...");
        }

        BigDecimal exchangeRate = exchangeResponse.getUsdBrl().getBidAsBigDecimal();

        return price.divide(exchangeRate, 2, RoundingMode.HALF_UP);
    }

}
