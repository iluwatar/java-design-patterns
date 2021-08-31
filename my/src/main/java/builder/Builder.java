package builder;

import java.awt.*;

class Test {
    public static void main(String[] args) {
        Car car = new Car.Builder()
                .name("宝马")
                .color(Color.BLACK)
                .engine(Engine.Engine1)
                .tire(Tire.Tire1)
                .build();
        System.out.println(car);
    }
}

class Car {
    private String name;
    private Color color;
    private Engine engine;
    private Tire tire;

    public Car(Builder builder) {
        this.name = builder.name;
        this.color = builder.color;
        this.engine = builder.engine;
        this.tire = builder.tire;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", engine=" + engine +
                ", tire=" + tire +
                '}';
    }

    static class Builder {
        private String name;
        private Color color;
        private Engine engine;
        private Tire tire;

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder color(Color color) {
            this.color = color;
            return this;
        }

        Builder engine(Engine engine) {
            this.engine = engine;
            return this;
        }

        Builder tire(Tire tire) {
            this.tire = tire;
            return this;
        }

        Car build() {
            return new Car(this);
        }
    }
}

enum Engine {
    Engine1, Engine2
}

enum Tire {
    Tire1, Tire2
}