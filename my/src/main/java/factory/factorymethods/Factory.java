package factory.factorymethods;

class Test {
    public static void main(String[] args) {
        Factory factory1 = FactoryManger.createFactory(1);
        Factory factory2 = FactoryManger.createFactory(2);
        System.out.println(factory1.create(1));
        System.out.println(factory1.create(2));
        System.out.println(factory2.create(1));
        System.out.println(factory2.create(2));
    }
}

interface Factory {
    Product create(int type);
}

class Factory1 implements Factory {
    @Override
    public Product create(int type) {
        Product product;
        if (type == 1) {
            product = new Factory1Product1();
        } else if (type == 2) {
            product = new Factory1Product2();
        } else {
            product = new Factory1Product1();
        }
        return product;
    }
}

class Factory2 implements Factory {
    @Override
    public Product create(int type) {
        Product product;
        if (type == 1) {
            product = new Factory2Product1();
        } else if (type == 2) {
            product = new Factory2Product2();
        } else {
            product = new Factory2Product1();
        }
        return product;
    }
}

class FactoryManger {
    public static Factory createFactory(int type) {
        Factory factory;
        if (type == 1) {
            factory = new Factory1();
        } else if (type == 2) {
            factory = new Factory2();
        } else {
            factory = new Factory1();
        }
        return factory;
    }
}

interface Product {

}

class Factory1Product1 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory1Product2 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory2Product1 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory2Product2 implements Product {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}