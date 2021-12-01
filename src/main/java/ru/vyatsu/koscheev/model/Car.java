package ru.vyatsu.koscheev.model;

public class Car {
    public String brand;
    public String model;
    public int year;
    public Engine engine;

    public Car() {}

    public Car(String model, int year, Car.Engine engine) {
        this.model = (model == null) ? "undefined" : model;
        this.year = year;
        this.engine = (engine == null) ? new Car.Engine(0, 0, "undefined") : engine;

        if (this.engine.fuel == null)
            this.engine.fuel = "undefined";
    }

    public Car(String brand, String model, int year, Engine engine) {
        this(model, year, engine);
        this.brand = (brand == null) ? "undefined" : brand;
    }

    public static class Engine {
        public double amount;
        public int power;
        public String fuel;

        public Engine() {}

        public Engine(double amount, int power, String fuel) {
            this.amount = amount;
            this.power = power;
            this.fuel = fuel;
        }
    }
}
