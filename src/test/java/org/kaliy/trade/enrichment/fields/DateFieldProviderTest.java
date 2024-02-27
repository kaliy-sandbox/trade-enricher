package org.kaliy.trade.enrichment.fields;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kaliy.trade.enrichment.DataProvider.aTrade;

class DateFieldProviderTest {
    
    private DateFieldProvider dateFieldProvider = new DateFieldProvider();

    @Test
    void returns_currency_from_trade() {
        val trade = aTrade().withDate(LocalDate.of(2023, 6, 12)).build();

        assertThat(dateFieldProvider.getField(trade)).isEqualTo("20230612");
    }

    @Test
    void returns_proper_column_name() {
        assertThat(dateFieldProvider.columnName()).isEqualTo("date");
    }
}
