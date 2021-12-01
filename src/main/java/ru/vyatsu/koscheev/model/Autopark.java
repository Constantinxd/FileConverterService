package ru.vyatsu.koscheev.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("brand")
public class Autopark {
    public String name;
    public List<Car> cars;

    public Autopark() {}

    public Autopark(String name, List<Car> cars) {
        this.name = name;
        this.cars = cars;
    }
}
