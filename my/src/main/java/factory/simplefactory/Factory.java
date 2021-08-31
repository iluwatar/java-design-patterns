package factory.simplefactory;

class Test {
    public static void main(String[] args) {
        System.out.println(Factory.create(1));
        System.out.println(Factory.create(2));
    }
}

class Factory {
    public static Product create(int type) {
        Product product;
        if (type == 1) {
            product = new Product1();
        } else if (type == 2) {
            product = new Product2();
        } else {
            product = new Product1();
        }
        return product;
    }
}

interface Product {

}

class Product1 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Product2 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}