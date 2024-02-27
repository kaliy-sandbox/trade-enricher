package org.kaliy.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = TradeEnricherApplication.class)
public class TradeEnricherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeEnricherApplication.class, args);
	}

}
