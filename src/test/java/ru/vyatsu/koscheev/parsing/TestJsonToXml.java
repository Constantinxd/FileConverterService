package ru.vyatsu.koscheev.parsing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJsonToXml {
    @TempDir
    static Path tempDir;
    private static JsonToXml jsonToXml;

    @BeforeAll
    public static void init() {
        jsonToXml = new JsonToXml();
    }

    @Test
    public void CorrectJsonConvertToXml1() throws Exception {
        Path fileJson = Files.createFile(tempDir.resolve("cars1.json"));
        Path fileXml = Files.createFile(tempDir.resolve("cars1.xml"));

        Files.write(fileJson, Collections.singletonList("""
                {
                  "brands" : [ {
                    "brand" : {
                      "name" : "Volkswagen",
                      "cars" : [ {
                        "model" : "Tiguan I",
                        "year" : 2008,
                        "engine" : {
                          "amount" : 1.4,
                          "power" : 150,
                          "fuel" : "Бензин"
                        }
                      } ]
                    }
                  } ]
                }"""));

        jsonToXml.convert(fileJson.toString(), fileXml.toString());

        assertEquals(
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <cars>
                            <car>
                                <brand>Volkswagen</brand>
                                <model>Tiguan I</model>
                                <year>2008</year>
                                <engine>
                                    <amount>1.4</amount>
                                    <power>150</power>
                                    <fuel>Бензин</fuel>
                                </engine>
                            </car>
                        </cars>""",
                Files.readString(fileXml).trim());
    }

    @Test
    public void CorrectJsonConvertToXml2() throws Exception {
        Path fileJson = Files.createFile(tempDir.resolve("cars2.json"));
        Path fileXml = Files.createFile(tempDir.resolve("cars2.xml"));

        Files.write(fileJson, Collections.singletonList("""
                {
                  "brands" : [ {
                    "brand" : {
                      "name" : "Volkswagen",
                      "cars" : [ {
                        "model" : "Tiguan I",
                        "year" : 2008,
                        "engine" : {
                          "amount" : 1.4,
                          "power" : 150,
                          "fuel" : "Бензин"
                        }
                      }, {
                        "model" : "Tiguan II",
                        "year" : 2017,
                        "engine" : {
                          "amount" : 2.0,
                          "power" : 180,
                          "fuel" : "Бензин"
                        }
                      } ]
                    }
                  }, {
                    "brand" : {
                      "name" : "LADA",
                      "cars" : [ {
                        "model" : "Granta I",
                        "year" : 2012,
                        "engine" : {
                          "amount" : 1.6,
                          "power" : 87,
                          "fuel" : "Бензин"
                        }
                      } ]
                    }
                  } ]
                }"""));

        jsonToXml.convert(fileJson.toString(), fileXml.toString());

        assertEquals(
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <cars>
                            <car>
                                <brand>Volkswagen</brand>
                                <model>Tiguan I</model>
                                <year>2008</year>
                                <engine>
                                    <amount>1.4</amount>
                                    <power>150</power>
                                    <fuel>Бензин</fuel>
                                </engine>
                            </car>
                            <car>
                                <brand>Volkswagen</brand>
                                <model>Tiguan II</model>
                                <year>2017</year>
                                <engine>
                                    <amount>2.0</amount>
                                    <power>180</power>
                                    <fuel>Бензин</fuel>
                                </engine>
                            </car>
                            <car>
                                <brand>LADA</brand>
                                <model>Granta I</model>
                                <year>2012</year>
                                <engine>
                                    <amount>1.6</amount>
                                    <power>87</power>
                                    <fuel>Бензин</fuel>
                                </engine>
                            </car>
                        </cars>""",
                Files.readString(fileXml).trim());
    }

    @Test
    public void JsonWithEmptyModelTagConvertToXml() throws Exception {
        Path fileJson = Files.createFile(tempDir.resolve("cars3.json"));
        Path fileXml = Files.createFile(tempDir.resolve("cars3.xml"));

        Files.write(fileJson, Collections.singletonList("""
                {
                  "brands" : [ {
                    "brand" : {
                      "name" : "Volkswagen",
                      "cars" : [ {
                        "year" : 2008,
                        "engine" : {
                          "amount" : 1.4,
                          "power" : 150,
                          "fuel" : "Бензин"
                        }
                      } ]
                    }
                  } ]
                }"""));

        jsonToXml.convert(fileJson.toString(), fileXml.toString());

        assertEquals(
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <cars>
                            <car>
                                <brand>Volkswagen</brand>
                                <model>undefined</model>
                                <year>2008</year>
                                <engine>
                                    <amount>1.4</amount>
                                    <power>150</power>
                                    <fuel>Бензин</fuel>
                                </engine>
                            </car>
                        </cars>""",
                Files.readString(fileXml).trim());
    }

    @Test
    public void JsonWithEmptyEngineTagConvertToXml() throws Exception {
        Path fileJson = Files.createFile(tempDir.resolve("cars4.json"));
        Path fileXml = Files.createFile(tempDir.resolve("cars4.xml"));

        Files.write(fileJson, Collections.singletonList("""
                {
                  "brands" : [ {
                    "brand" : {
                      "name" : "Volkswagen",
                      "cars" : [ {
                        "model" : "Tiguan I",
                        "year" : 2008
                      } ]
                    }
                  } ]
                }"""));

        jsonToXml.convert(fileJson.toString(), fileXml.toString());

        assertEquals(
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <cars>
                            <car>
                                <brand>Volkswagen</brand>
                                <model>Tiguan I</model>
                                <year>2008</year>
                                <engine>
                                    <amount>0.0</amount>
                                    <power>0</power>
                                    <fuel>undefined</fuel>
                                </engine>
                            </car>
                        </cars>""",
                Files.readString(fileXml).trim());
    }
}
