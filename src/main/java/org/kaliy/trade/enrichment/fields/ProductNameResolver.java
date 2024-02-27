package org.kaliy.trade.enrichment.fields;

import java.util.Optional;

@FunctionalInterface
interface ProductNameResolver {
    Optional<String> getProductName(String productId);
}
