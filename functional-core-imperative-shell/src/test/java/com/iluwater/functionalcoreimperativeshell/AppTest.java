package com.iluwater.functionalcoreimperativeshell;

import com.iluwatar.functionalcoreimperativeshell.App;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/** 
* App Tester. 
* 
* @author <Authors name> 
* @since <pre>May 25, 2022</pre> 
* @version 1.0 
*/ 
public class AppTest {

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() {
  assertDoesNotThrow(() -> App.main(new String[]{}));
}


} 
