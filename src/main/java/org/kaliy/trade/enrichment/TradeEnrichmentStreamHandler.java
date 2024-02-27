package org.kaliy.trade.enrichment;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaliy.trade.enrichment.fields.TradeFieldProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeEnrichmentStreamHandler {
    private final List<TradeFieldProvider> enrichers;

    public void enrich(InputStream csvInputStream, Writer outputWriter) throws IOException {
        CsvToBean<Trade> reader =
                new CsvToBeanBuilder<Trade>(new InputStreamReader(csvInputStream))
                        .withType(Trade.class)
                        .withExceptionHandler(e -> {
                            log.warn("Row {} is invalid: {}", e.getLineNumber(), Arrays.toString(e.getLine()));
                            return e;
                        })
                        .build();
        try (
                var writer = new CSVWriterBuilder(outputWriter)
                        .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                        .withEscapeChar(ICSVWriter.NO_ESCAPE_CHARACTER)
                        .build()
        ) {
            writer.writeNext(enrichers.stream().map(TradeFieldProvider::columnName)
                    .toArray(String[]::new));
            reader.stream().forEach(trade -> {
                writer.writeNext(enrichers.stream()
                        .map(enricher -> enricher.getField(trade))
                        .toArray(String[]::new));
            });
        }
    }
}
