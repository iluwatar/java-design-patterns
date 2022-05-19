package com.iluwatar.filtercache;

import com.iluwatar.filtercache.controller.AopController;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class FilterCacheApplicationTests {

	/**
	 * tool object which has some function.
	 */
	@Autowired
	AopController tooluser;

	/**
	 * Test wheather get function works well or not.
	 */
	@Test
	void testGet() {
		var user_info = tooluser.sayGet(2);
		System.out.println(user_info);
		var user_info2 = tooluser.sayGet(11);
		System.out.println(user_info2);
		var user_info3 = tooluser.sayGet(13);
		System.out.println(user_info3);
	}
	/**
	 * Test wheather post function works well or not.
	 */
	@Test
	void testPost(){
		HashMap<String,String> map1=new HashMap<>();
		map1.put("id","2");
		HashMap<String,String> map2=new HashMap<>();
		map2.put("id","8");
		HashMap<String,String> map3=new HashMap<>();
		map3.put("id","11");
		var user_info = tooluser.sayPost(map1);
		System.out.println(user_info);
		var user_info2 = tooluser.sayPost(map2);
		System.out.println(user_info2);
		var user_info3 = tooluser.sayPost(map3);
		System.out.println(user_info3);
	}

}
