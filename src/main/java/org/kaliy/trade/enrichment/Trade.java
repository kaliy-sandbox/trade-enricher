package org.kaliy.trade.enrichment;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Trade {
    @CsvDate(value = "yyyyMMdd")
    @CsvBindByName
    LocalDate date;
    @CsvBindByName(column = "product_id")
    String productId;
    @CsvBindByName
    String currency;
    @CsvBindByName
    BigDecimal price;
}
