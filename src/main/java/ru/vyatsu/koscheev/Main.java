package ru.vyatsu.koscheev;

import ru.vyatsu.koscheev.fileConverterService.XmlToJson;

public class Main {
    public static void main(String[] args) {
        XmlToJson.convert("inputFiles\\cars.xml", "inputFiles\\user.json");
    }
}
