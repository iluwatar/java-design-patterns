package com.iluwatar;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		PersonDao dao = context.getBean(PersonDao.class);

		Person peter = new Person("Peter", "Sagan");
		Person nasta = new Person("Nasta", "Kuzminova");

		// Add new Person records
		dao.save(peter);
		dao.save(nasta);

		// Count Person records
		System.out.println("Count Person records: " + dao.count());

		// Print all records
		List<Person> persons = (List<Person>) dao.findAll();
		for (Person person : persons) {
			System.out.println(person);
		}

		// Find Person by surname
		System.out.println("Find by surname 'Sagan': "	+ dao.findBySurname("Sagan"));

		// Update Person
		nasta.setName("Barbora");
		nasta.setSurname("Spotakova");
		dao.save(nasta);

		System.out.println("Find by id 2: " + dao.findOne(2L));

		// Remove record from Person
		dao.delete(2L);

		// And finally count records
		System.out.println("Count Person records: " + dao.count());

		context.close();
	}
}
