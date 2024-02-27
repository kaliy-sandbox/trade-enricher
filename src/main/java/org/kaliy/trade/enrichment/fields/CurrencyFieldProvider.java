package org.kaliy.trade.enrichment.fields;

import org.kaliy.trade.enrichment.Trade;
import org.kaliy.trade.enrichment.fields.TradeFieldProvider;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(3)
class CurrencyFieldProvider implements TradeFieldProvider {
    private static final String COLUMN_NAME = "currency";

    @Override
    public String getField(Trade trade) {
        return trade.getCurrency();
    }

    @Override
    public String columnName() {
        return COLUMN_NAME;
    }
}
