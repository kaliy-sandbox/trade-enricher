package org.kaliy.trade.enrichment.fields;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kaliy.trade.enrichment.DataProvider.aTrade;

class CurrencyFieldProviderTest {
    private final CurrencyFieldProvider currencyFieldProvider = new CurrencyFieldProvider();

    @Test
    void returns_currency_from_trade() {
        val trade = aTrade().withCurrency("EUR").build();

        assertThat(currencyFieldProvider.getField(trade)).isEqualTo("EUR");
    }

    @Test
    void returns_proper_column_name() {
        assertThat(currencyFieldProvider.columnName()).isEqualTo("currency");
    }
}
