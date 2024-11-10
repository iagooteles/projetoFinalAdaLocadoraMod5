package com.projetoFinalWeb.projetoWeb.apis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ExchangeResponseDTO {

    @JsonProperty("USDBRL")
    private ExchangeData usdBrl;

    public ExchangeData getUsdBrl() {
        return usdBrl;
    }

    public void setUsdBrl(ExchangeData usdBrl) {
        this.usdBrl = usdBrl;
    }

    public static class ExchangeData {
        private String bid;
        private String ask;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }

        public BigDecimal getBidAsBigDecimal() {
            return new BigDecimal(bid);
        }

        public BigDecimal getAskAsBigDecimal() {
            return new BigDecimal(ask);
        }
    }
}
