package com.memory.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.memory.dao.db.UserDAO;
import com.memory.dao.pojo.User;

public class App {
	public static void main(String[] args) {
		
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				"file:src/main/resources/beans.xml");
		
		final UserDAO dao = (UserDAO)context.getBean("userDao");
		for (final User user : dao.findAll()) {
			System.out.println(user);
		}
		
		((ClassPathXmlApplicationContext)context).close();
	}
}
