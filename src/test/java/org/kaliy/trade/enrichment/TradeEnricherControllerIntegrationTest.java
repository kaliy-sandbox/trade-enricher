package org.kaliy.trade.enrichment;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.kaliy.trade.TradeEnricherApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TradeEnricherApplication.class)
@AutoConfigureMockMvc
class TradeEnricherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void enriches_input_csv_with_data() throws Exception {
        String inputCsv = """
                date,product_id,currency,price
                20160101,1,EUR,10.0
                20160101,2,EUR,20.1
                20160101,3,EUR,30.34""";
        val body = mockMvc.perform(post("/api/v1/enrich")
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .accept("text/csv")
                        .content(inputCsv))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andReturn();
        assertThat(body.getResponse().getContentAsString()).isEqualTo("""
                date,product_name,currency,price
                20160101,Treasury Bills Domestic,EUR,10.0
                20160101,Corporate Bonds Domestic,EUR,20.1
                20160101,REPO Domestic,EUR,30.34
                """
        );
    }

    @Test
    void performs_data_validation() throws Exception {
        String inputCsv = """
                date,product_id,currency,price
                20160101,123123,EUR,10.0
                2015-01-02,1,EUR,35.34
                20150102,1,EUR,strange-price""";
        val body = mockMvc.perform(post("/api/v1/enrich")
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .accept("text/csv")
                        .content(inputCsv))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andReturn();
        assertThat(body.getResponse().getContentAsString()).isEqualTo("""
                date,product_name,currency,price
                20160101,Missing Product Name,EUR,10.0
                """
        );

    }
}
