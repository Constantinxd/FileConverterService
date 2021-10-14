package ru.vyatsu.koscheev.fileConverterService;

import java.util.List;

public class Car {
    String model;
    int year;
    Engine engine;

    Car(String model, int year, Engine engine) {
        this.model = model;
        this.year = year;
        this.engine = engine;
    }

    class Engine{
        double amount;
        int power;
        String fuel;
    }
}

class BrandCar extends Car {
    String brand;

    public BrandCar(String model, int year, Engine engine) {
        super(model, year, engine);
    }
}

class BrandAutopark {
    String name;
    List<Car> cars;

    public BrandAutopark (String name, List<Car> cars){
        this.name = name;
        this.cars = cars;
    }
}

class BrandWrapper {
    BrandAutopark brand;

    public BrandWrapper(BrandAutopark brand) {
        this.brand = brand;
    }
}

class BrandsWrapper {
    List<BrandWrapper> brands;

    public BrandsWrapper(List<BrandWrapper> brands) {
        this.brands = brands;
    }
}
