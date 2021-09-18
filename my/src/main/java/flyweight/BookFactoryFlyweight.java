package flyweight;

import java.util.EnumMap;

class Test {
    public static void main(String[] args) {
        BookFactoryFlyweight factory = new BookFactoryFlyweight();
        System.out.println("JAVA=" + testCreate(factory, BookType.JAVA));
        System.out.println("KOTLIN=" + testCreate(factory, BookType.KOTLIN));
        System.out.println("CPP=" + testCreate(factory, BookType.CPP));
        System.out.println("PHP=" + testCreate(factory, BookType.PHP));
    }

    private static boolean testCreate(BookFactoryFlyweight factory, BookType type) {
        Book firstBook = factory.create(type);
        for (int i = 0; i < 10; i++) {
            Book book = factory.create(type);
            if (firstBook != book) {
                return false;
            }
        }
        return true;
    }
}

public class BookFactoryFlyweight {
    private final EnumMap<BookType, Book> map = new EnumMap<>(BookType.class);

    public Book create(BookType type) {
        Book book = map.get(type);
        if (book == null) {
            switch (type) {
                case JAVA:
                    book = new JavaBook();
                    map.put(type, book);
                    break;
                case KOTLIN:
                    book = new KotlinBook();
                    map.put(type, book);
                    break;
                case CPP:
                    book = new CPPBook();
                    map.put(type, book);
                    break;
                case PHP:
                    book = new PHPBook();
                    map.put(type, book);
                    break;
            }
        }
        return book;
    }
}

interface Book {
    /**
     * 读
     */
    void read();
}

class JavaBook implements Book {
    @Override
    public void read() {
        System.out.println("start read java book");
    }
}

class KotlinBook implements Book {
    @Override
    public void read() {
        System.out.println("start read kotlin book");
    }
}

class CPPBook implements Book {
    @Override
    public void read() {
        System.out.println("start read cpp book");
    }
}

class PHPBook implements Book {
    @Override
    public void read() {
        System.out.println("start read php book");
    }
}

enum BookType {
    JAVA, KOTLIN, CPP, PHP
}