package org.kaliy.trade.enrichment.fields;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kaliy.trade.enrichment.DataProvider.aTrade;

class PriceFieldProviderTest {
    private PriceFieldProvider priceFieldProvider = new PriceFieldProvider();

    @Test
    void returns_currency_from_trade() {
        val trade = aTrade().withPrice(new BigDecimal("100.20")).build();

        assertThat(priceFieldProvider.getField(trade)).isEqualTo("100.20");
    }

    @Test
    void returns_proper_column_name() {
        assertThat(priceFieldProvider.columnName()).isEqualTo("price");
    }
}
