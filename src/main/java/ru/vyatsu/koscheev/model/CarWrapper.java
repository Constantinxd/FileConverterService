package ru.vyatsu.koscheev.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="cars")
public class CarWrapper {
    @XmlElement(name = "car")
    public List<Car> cars;

    public CarWrapper() {}

    public CarWrapper(List<Car> cars) {
        this.cars = cars;
    }
}
