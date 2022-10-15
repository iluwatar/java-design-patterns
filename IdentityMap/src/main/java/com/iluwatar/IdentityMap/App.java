package com.iluwatar.IdentityMap;

public class App {
    public static void main(String[] args) {

        Person person1 = new Person(1,"John",27304159);
        Person person2 = new Person(2,"Thomas",42273631);
        Person person3 = new Person(3,"Arthur",27489171);
        Person person4 = new Person(4,"Finn",20499078);
        Person person5 = new Person(5,"Michael",40599078);

        PersonDBSimulatorImplementation DB = new PersonDBSimulatorImplementation();
        DB.insert(person1);
        DB.insert(person2);
        DB.insert(person3);
        DB.insert(person4);
        DB.insert(person5);

        PersonFinder finder = new PersonFinder();
        finder.setDB(DB);

        System.out.println(finder.getPerson(2).toString());
        System.out.println(finder.getPerson(4).toString());
        System.out.println(finder.getPerson(5).toString());
        System.out.println(finder.getPerson(2).toString());

    }
}
