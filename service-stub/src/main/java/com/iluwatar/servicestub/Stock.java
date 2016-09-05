package com.iluwatar.servicestub;

public class Stock {

	  private final String symbol;
	  private final String name;

	  public Stock(String symbol, String name) {
	    this.symbol = symbol;
	    this.name = name;
	  }

	  public String getSymbol() {
	    return symbol;
	  }

	  public String getName() {
	    return name;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Stock other = (Stock) obj;
	    if (symbol == null) {
	      if (other.symbol != null)
	        return false;
	    } else if (!symbol.equals(other.symbol))
	      return false;
	    return true;
	  }
	  
	  
	  
}
