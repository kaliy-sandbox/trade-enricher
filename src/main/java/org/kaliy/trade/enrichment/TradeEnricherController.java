package org.kaliy.trade.enrichment;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
class TradeEnricherController {

    private final TradeEnrichmentStreamHandler tradeEnrichmentStreamHandler;

    @PostMapping(value = "/api/v1/enrich", consumes = "text/csv", produces = "text/csv")
    public void enrichTrades(InputStream inputStream, HttpServletResponse writer) throws IOException {
        writer.setContentType("text/csv");
        tradeEnrichmentStreamHandler.enrich(inputStream, writer.getWriter());
    }
}
