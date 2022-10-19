package com.iluwatar.foreignkeymapping;

/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */
public class App {

  final static String order = "order";
  final static String person = "person";

  public static void main(String[] args) {

    Person person1 = new Person(1, "John", "Loli", 33);
    Person person2 = new Person(2, "Thomas", "Funny", 22);
    Person person3 = new Person(3, "Arthur", "Chiken", 11);
    Person person4 = new Person(4, "Finn", "Wash", 55);
    Person person5 = new Person(5, "Michael", "Linux", 67);

    Order order1 = new Order(1, "2132131", 1);
    Order order2 = new Order(2, "12321321", 1);
    Order order3 = new Order(3, "213213123", 3);
    Order order4 = new Order(4, "123213213", 3);
    Order order5 = new Order(5, "12312321231", 1);

    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1, person);
    database.insert(person2, person);
    database.insert(person3, person);
    database.insert(person4, person);
    database.insert(person5, person);
    database.insert(order1, order);
    database.insert(order2, order);
    database.insert(order3, order);
    database.insert(order4, order);
    database.insert(order5, order);

    System.out.println(database.find(2, person).toString());
    System.out.println(database.find(4, person).toString());
    System.out.println(database.find(5, person).toString());

    System.out.println(database.find(2, order).toString());
    System.out.println(database.find(4, order).toString());
    System.out.println(database.find(5, order).toString());

    // get order owner
    System.out.println(database.find(order2.getPersonNationalId(), person));

    // get person's orders
    System.out.println(person1.getAllOrder(database.getOrderList()));
    //System.out.println(getAllOrder(person1.getPersonNationalId(),database.getOrderList()));

    // person place order
    Order order6 = new Order(5, "12312321231", 1);
    database.insert(order6,order);

  }

}
