package org.kaliy.trade.enrichment.fields;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CsvProductNameResolver implements ProductNameResolver {

    private final Map<String, String> productNames;

    public CsvProductNameResolver(@Value("classpath:products.csv") Resource resourceFile) throws IOException {
        this.productNames = populateProductNames(resourceFile);
    }

    @Override
    public Optional<String> getProductName(String productId) {
        return Optional.ofNullable(productNames.get(productId));
    }

    private Map<String, String> populateProductNames(Resource csv) throws IOException {
        InputStream inputStream = csv.getInputStream();
        Assert.notNull(inputStream, "Products file not found");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        return new CsvToBeanBuilder<ProductInformation>(reader)
                .withType(ProductInformation.class).build().stream()
                .collect(Collectors.toMap(ProductInformation::getProductId, ProductInformation::getProductName));
    }

    @Data
    public static class ProductInformation {
        @CsvBindByName(column = "product_id")
        private String productId;
        @CsvBindByName(column = "product_name")
        private String productName;
    }
}
