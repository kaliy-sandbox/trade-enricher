package org.kaliy.trade.enrichment;

import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DataProvider {
    public static Trade.TradeBuilder aTrade() {
        return Trade.builder()
                .withDate(LocalDate.now())
                .withPrice(BigDecimal.valueOf(100))
                .withCurrency("USD")
                .withProductId("123456");
    }
}
