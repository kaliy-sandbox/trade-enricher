package org.kaliy.trade.enrichment.fields;

import org.kaliy.trade.enrichment.Trade;

public interface TradeFieldProvider {
    String getField(Trade trade);

    String columnName();
}
