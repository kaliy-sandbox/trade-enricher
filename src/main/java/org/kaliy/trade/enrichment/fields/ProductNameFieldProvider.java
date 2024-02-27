package org.kaliy.trade.enrichment.fields;

import lombok.RequiredArgsConstructor;
import org.kaliy.trade.enrichment.Trade;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@RequiredArgsConstructor
class ProductNameFieldProvider implements TradeFieldProvider {
    private final ProductNameResolver productNameResolver;

    @Override
    public String getField(Trade trade) {
        return productNameResolver.getProductName(trade.getProductId())
                .orElse("Missing Product Name");
    }

    @Override
    public String columnName() {
        return "product_name";
    }
}
