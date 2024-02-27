package org.kaliy.trade.enrichment.fields;

import org.kaliy.trade.enrichment.Trade;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Order(1)
class DateFieldProvider implements TradeFieldProvider {
    private static final String COLUMN_NAME = "date";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String getField(Trade trade) {
        return trade.getDate().format(formatter);
    }

    @Override
    public String columnName() {
        return COLUMN_NAME;
    }
}
