package com.iluwatar.servicestub;

import java.io.IOException;
import java.math.BigDecimal;

public interface TickerService {

	BigDecimal currentPrice(String stockSymbol);
	BigDecimal yearLow(String stockSymbol);
}
