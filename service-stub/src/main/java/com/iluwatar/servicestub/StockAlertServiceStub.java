/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.servicestub;

/**
 * This is service-stub implementation of the stock alert service. 
 * Test should use this implementation for testing.
 * 
 * @author jdoetricksy
 *
 */

public class StockAlertServiceStub implements StockAlertServiceInterface{
	
	private Rule rule;
	private Action dispatcher;
	
	/**
	 * Constructor
	 * @param name
	 */
	public StockAlertServiceStub(Rule rule, Action dispatcher) {
		this.rule = rule;
		this.dispatcher = dispatcher;
	}


	public boolean execute(String symbol)
    {
        boolean eval = false;
        
        eval = rule.checkPrice(symbol);
        if (eval){
            dispatcher.sell();
        }else{
        	dispatcher.error();
        }
        
        return eval;
    }

}
