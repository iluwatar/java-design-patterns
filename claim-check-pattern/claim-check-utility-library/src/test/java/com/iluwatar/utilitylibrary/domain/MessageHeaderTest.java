/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.utilitylibrary.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MessageHeaderTest {

	@Parameters(name = "{index}: fun({0},{1},\"{2}\")={3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
            {"input1.json", "C://tmp", "Cost Calculator", false}, 
            {"input.json", "C://tmpp", "Cost Calculator", false},
            {"input.json", "C://tmp", "Cost Calculatorr", false},
           });
    }

    private MessageHeader messageHeader;
    
    private String dataLocation;
    private String dataFileName;
    private String operataionName;
    private boolean expected;
    
    public MessageHeaderTest(String dataLocation, String dataFileName, String operataionName, boolean expected) {
    	this.dataFileName = dataFileName;
    	this.dataLocation = dataLocation;
    	this.operataionName = operataionName;
    	this.expected = expected;
    	
    	this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation("C://tmp");
		this.messageHeader.setOperataionName("Cost Calculator");
    }
    
    @Test
	public void testNotEqualsObject() {
		MessageHeader tempMessageHeader = new MessageHeader();
		tempMessageHeader.setDataFileName(this.dataFileName);
		tempMessageHeader.setDataLocation(this.dataLocation);
		tempMessageHeader.setOperataionName(this.operataionName);
		assertEquals(this.expected,this.messageHeader.equals(tempMessageHeader));
	}
    
}
