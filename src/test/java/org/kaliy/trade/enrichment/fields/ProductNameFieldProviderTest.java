package org.kaliy.trade.enrichment.fields;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kaliy.trade.enrichment.DataProvider.aTrade;

class ProductNameFieldProviderTest {
    @Test
    void returns_product_name_for_trade() {
        val trade = aTrade().withProductId("123").build();
        val fieldProvider = new ProductNameFieldProvider((any) -> {
            assertThat(any).isEqualTo("123");
            return Optional.of("Product Name");
        });

        assertThat(fieldProvider.getField(trade)).isEqualTo("Product Name");
    }

    @Test
    void returns_proper_column_name() {
        val fieldProvider = new ProductNameFieldProvider((any) -> Optional.of("Product Name"));

        assertThat(fieldProvider.columnName()).isEqualTo("product_name");
    }

    @Test
    void defaults_product_name_to_missing_product_if_not_available() {
        val trade = aTrade().withProductId("123").build();
        val fieldProvider = new ProductNameFieldProvider((any) -> {
            assertThat(any).isEqualTo("123");
            return Optional.empty();
        });

        assertThat(fieldProvider.getField(trade)).isEqualTo("Missing Product Name");
    }
}
