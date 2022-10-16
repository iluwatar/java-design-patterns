package com.iluwatar.foreignkeymapping;

/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */
public class App {
  public static void main(String[] args) {

    Person person1 = new Person(1, "John", "Loli", 33);
    Person person2 = new Person(2, "Thomas", "Funny", 22);
    Person person3 = new Person(3, "Arthur", "Chiken", 11);
    Person person4 = new Person(4, "Finn", "Wash", 55);
    Person person5 = new Person(5, "Michael", "Linux", 67);

    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);

    PersonFinder finder = new PersonFinder();
    finder.setDB(db);

    System.out.println(finder.getPerson(2).toString());
    System.out.println(finder.getPerson(4).toString());
    System.out.println(finder.getPerson(5).toString());
    System.out.println(finder.getPerson(2).toString());

    Order order1 = new Order(1, "2132131", 1);
    Order order2 = new Order(2, "12321321", 1);
    Order order3 = new Order(3, "213213123", 3);
    Order order4 = new Order(4, "123213213", 3);
    Order order5 = new Order(5, "12312321231", 1);

    OrderDbSimulatorImplementation Orderdb = new OrderDbSimulatorImplementation();
    Orderdb.insert(order1);
    Orderdb.insert(order2);
    Orderdb.insert(order3);
    Orderdb.insert(order4);
    Orderdb.insert(order5);

    OrderFinder orderFinder = new OrderFinder();
    orderFinder.setDB(Orderdb);

    System.out.println(orderFinder.getOrder(2).toString());
    System.out.println(orderFinder.getOrder(4).toString());
    System.out.println(orderFinder.getOrder(5).toString());
    System.out.println(orderFinder.getOrder(2).toString());

  }
}
