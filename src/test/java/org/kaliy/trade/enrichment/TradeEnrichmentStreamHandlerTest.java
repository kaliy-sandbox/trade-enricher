package org.kaliy.trade.enrichment;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.kaliy.trade.enrichment.fields.TradeFieldProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TradeEnrichmentStreamHandlerTest {

    @Test
    void uses_all_available_handlers_for_headers() throws IOException {
        TradeEnrichmentStreamHandler handler = new TradeEnrichmentStreamHandler(
                List.of(new Field1Handler(), new Field2Handler())
        );

        val inputStream = new ByteArrayInputStream("""
                date,product_id,currency,price
                20160101,1,EUR,10.0""".getBytes(StandardCharsets.UTF_8)
        );
        val writer = new StringWriter();

        handler.enrich(inputStream, writer);

        assertThat(writer.toString()).startsWith("column-from-field-1,column-from-field-2");
    }

    @Test
    void processes_all_trades() throws IOException {
        TradeEnrichmentStreamHandler handler = new TradeEnrichmentStreamHandler(
                List.of(new Field1Handler(), new Field2Handler())
        );

        val inputStream = new ByteArrayInputStream("""
                date,product_id,currency,price
                20160101,1,EUR,10.0
                20160102,1,EUR,10.0""".getBytes(StandardCharsets.UTF_8)
        );
        val writer = new StringWriter();

        handler.enrich(inputStream, writer);

        assertThat(writer.toString().split("\n"))
                .containsSequence("field1,field2", "field1,field2");
    }

    private static class Field1Handler implements TradeFieldProvider {
        @Override
        public String getField(Trade trade) {
            return "field1";
        }

        @Override
        public String columnName() {
            return "column-from-field-1";
        }
    }

    private static class Field2Handler implements TradeFieldProvider {
        @Override
        public String getField(Trade trade) {
            return "field2";
        }

        @Override
        public String columnName() {
            return "column-from-field-2";
        }
    }

}
