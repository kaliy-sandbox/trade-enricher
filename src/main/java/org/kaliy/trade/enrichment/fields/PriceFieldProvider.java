package org.kaliy.trade.enrichment.fields;

import org.kaliy.trade.enrichment.Trade;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(4)
class PriceFieldProvider implements TradeFieldProvider {
    private static final String COLUMN_NAME = "price";

    @Override
    public String getField(Trade trade) {
        return trade.getPrice().toPlainString();
    }

    @Override
    public String columnName() {
        return COLUMN_NAME;
    }
}
