package com.iluwatar.consumer.callcostprocessor.application;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iluwatar.consumer.callcostprocessor.application.UsageCostProcessorApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsageCostProcessorApplicationTests {

	@Test
	public void contextLoads() {
		try {
			UsageCostProcessorApplication.main(new String[] {});
		} catch (Exception e) {
			fail("UsageCostProcessorApplication failed: "+e.getStackTrace());
		}
	}

}
