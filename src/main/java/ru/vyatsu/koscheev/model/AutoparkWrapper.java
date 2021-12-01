package ru.vyatsu.koscheev.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("brands")
public class AutoparkWrapper {
    @JsonProperty("brands")
    public List<Autopark> autoparks;

    public AutoparkWrapper() {}

    public AutoparkWrapper(List<Autopark> autoparks) {
        this.autoparks = autoparks;
    }
}
