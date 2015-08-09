package com.iluwatar.layers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		CakeLayerDao cakeLayerDao = context.getBean(CakeLayerDao.class);
		cakeLayerDao.save(new CakeLayer("strawberry", 1200));
		System.out.println("Count CakeLayer records: " + cakeLayerDao.count());

		context.close();
	}
}
