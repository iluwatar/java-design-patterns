package com.callusage.domain;

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
		assertEquals(false,this.messageHeader.equals(tempMessageHeader));
	}
    
}
