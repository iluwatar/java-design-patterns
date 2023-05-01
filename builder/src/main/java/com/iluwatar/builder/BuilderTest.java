package com.iluwatar.builder;

public class BuilderTest {
    // Builder Pattern 실습 !!!!


    public static void main(String[] args) {

        // Java 10 이후 부터 var 키워드 사용이 가능
        var animalTest = new Animal
                .AnimalBuilder("test")
                .setHairType(AnimalHairType.CURLY)
                .buildAnimal();

        System.out.println("BUILDER ANIMAL OBJECT 1 : " + animalTest);

        Animal builderAnimalTest = new Animal
                .AnimalBuilder("ANIMAL NAME MOT MUST NULL !!!")
                .setHairType(AnimalHairType.CURLY)
                .buildAnimal();

        System.out.println("BUILDER ANIMAL OBJECT 2 : " + builderAnimalTest);

    }

}

enum AnimalHairType {
    CURLY("COOL"),
    STRAIGHT("HOT");

    private String explainHair;

    AnimalHairType(String hairExplain) {
        this.explainHair = hairExplain;
    }

    @Override
    public String toString() {
        return this.explainHair;
    }
}

class Animal {
    String name;
    AnimalHairType animalHairType;

    private Animal(AnimalBuilder builder) {
        this.name = builder.name;
        this.animalHairType = builder.animalHairType;
    }

    public static class AnimalBuilder {
        String name;
        AnimalHairType animalHairType;

        AnimalBuilder(String name) {
            if(name.isEmpty()) {
                throw new IllegalArgumentException("Animal Must Have Name !!");
            }
            this.name = name;
        }

        AnimalBuilder setHairType(AnimalHairType hairT) {
            this.animalHairType = hairT;
            return this;
        }

        // Builder 패턴 마지막 해당 생성 된 객체를 Retrun 함
        Animal buildAnimal() {
            return new Animal(this);
        }

    }
}


