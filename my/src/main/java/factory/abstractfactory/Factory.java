package factory.abstractfactory;

class Test {
    public static void main(String[] args) {
        Factory factory1 = FactoryManger.createFactory(1);
        Factory factory2 = FactoryManger.createFactory(2);
        System.out.println(factory1.createProduct1(1));
        System.out.println(factory1.createProduct2(1));
        System.out.println(factory1.createProduct1(2));
        System.out.println(factory1.createProduct2(2));

        System.out.println();
        System.out.println(factory2.createProduct1(1));
        System.out.println(factory2.createProduct2(1));
        System.out.println(factory2.createProduct1(2));
        System.out.println(factory2.createProduct2(2));
    }
}

interface Factory {
    Product1 createProduct1(int type);

    Product2 createProduct2(int type);
}

class Factory1 implements Factory {

    @Override
    public Product1 createProduct1(int type) {
        Product1 product;
        if (type == 1) {
            product = new Factory1Product1Imp1();
        } else if (type == 2) {
            product = new Factory1Product1Imp2();
        } else {
            product = new Factory1Product1Imp1();
        }
        return product;
    }

    @Override
    public Product2 createProduct2(int type) {
        Product2 product;
        if (type == 1) {
            product = new Factory1Product2Imp1();
        } else if (type == 2) {
            product = new Factory1Product2Imp2();
        } else {
            product = new Factory1Product2Imp1();
        }
        return product;
    }
}

class Factory2 implements Factory {

    @Override
    public Product1 createProduct1(int type) {
        Product1 product;
        if (type == 1) {
            product = new Factory2Product1Imp1();
        } else if (type == 2) {
            product = new Factory2Product1Imp2();
        } else {
            product = new Factory2Product1Imp1();
        }
        return product;
    }

    @Override
    public Product2 createProduct2(int type) {
        Product2 product;
        if (type == 1) {
            product = new Factory2Product2Imp1();
        } else if (type == 2) {
            product = new Factory2Product2Imp2();
        } else {
            product = new Factory2Product2Imp1();
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

interface Product1 {

}

interface Product2 {

}

class Factory1Product1Imp1 implements Product1 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory1Product1Imp2 implements Product1 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory1Product2Imp1 implements Product2 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory1Product2Imp2 implements Product2 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}


class Factory2Product1Imp1 implements Product1 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory2Product1Imp2 implements Product1 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory2Product2Imp1 implements Product2 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Factory2Product2Imp2 implements Product2 {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}