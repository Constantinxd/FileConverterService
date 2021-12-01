package ru.vyatsu.koscheev.parsing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestXmlToJson {
    @TempDir
    static Path tempDir;
    private static XmlToJson xmlToJson;

    @BeforeAll
    public static void init() {
        xmlToJson = new XmlToJson();
    }

    @Test
    public void CorrectXmlConvertToJson1() throws Exception {
        Path fileXml = Files.createFile(tempDir.resolve("cars1.xml"));
        Path fileJson = Files.createFile(tempDir.resolve("cars1.json"));

        Files.write(fileXml, Collections.singletonList("""
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
                </cars>"""));

        xmlToJson.convert(fileXml.toString(), fileJson.toString());

        assertEquals(
                """
                        {\r
                          "brands" : [ {\r
                            "brand" : {\r
                              "name" : "Volkswagen",\r
                              "cars" : [ {\r
                                "model" : "Tiguan I",\r
                                "year" : 2008,\r
                                "engine" : {\r
                                  "amount" : 1.4,\r
                                  "power" : 150,\r
                                  "fuel" : "Бензин"\r
                                }\r
                              } ]\r
                            }\r
                          } ]\r
                        }""",
                Files.readString(fileJson).trim());
    }

    @Test
    public void CorrectXmlConvertToJson2() throws Exception {
        Path fileXml = Files.createFile(tempDir.resolve("cars2.xml"));
        Path fileJson = Files.createFile(tempDir.resolve("cars2.json"));

        Files.write(fileXml, Collections.singletonList("""
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
                </cars>"""));

        xmlToJson.convert(fileXml.toString(), fileJson.toString());

        assertEquals(
                """
                        {\r
                          "brands" : [ {\r
                            "brand" : {\r
                              "name" : "Volkswagen",\r
                              "cars" : [ {\r
                                "model" : "Tiguan I",\r
                                "year" : 2008,\r
                                "engine" : {\r
                                  "amount" : 1.4,\r
                                  "power" : 150,\r
                                  "fuel" : "Бензин"\r
                                }\r
                              }, {\r
                                "model" : "Tiguan II",\r
                                "year" : 2017,\r
                                "engine" : {\r
                                  "amount" : 2.0,\r
                                  "power" : 180,\r
                                  "fuel" : "Бензин"\r
                                }\r
                              } ]\r
                            }\r
                          }, {\r
                            "brand" : {\r
                              "name" : "LADA",\r
                              "cars" : [ {\r
                                "model" : "Granta I",\r
                                "year" : 2012,\r
                                "engine" : {\r
                                  "amount" : 1.6,\r
                                  "power" : 87,\r
                                  "fuel" : "Бензин"\r
                                }\r
                              } ]\r
                            }\r
                          } ]\r
                        }""",
                Files.readString(fileJson).trim());
    }

    @Test
    public void XmlWithEmptyObjectConvertToJson() throws Exception {
        Path fileXml = Files.createFile(tempDir.resolve("cars3.xml"));
        Path fileJson = Files.createFile(tempDir.resolve("cars3.json"));

        Files.write(fileXml, Collections.singletonList("""
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <cars>
                    <car>
                    </car>
                </cars>
                """));

        xmlToJson.convert(fileXml.toString(), fileJson.toString());

        assertEquals(
                """
                        {\r
                          "brands" : [ {\r
                            "brand" : {\r
                              "name" : "undefined",\r
                              "cars" : [ {\r
                                "model" : "undefined",\r
                                "year" : 0,\r
                                "engine" : {\r
                                  "amount" : 0.0,\r
                                  "power" : 0,\r
                                  "fuel" : "undefined"\r
                                }\r
                              } ]\r
                            }\r
                          } ]\r
                        }""",
                Files.readString(fileJson).trim());
    }

    @Test
    public void XmlWithEmptyModelTagConvertToJson() throws Exception {
        Path fileXml = Files.createFile(tempDir.resolve("cars4.xml"));
        Path fileJson = Files.createFile(tempDir.resolve("cars4.json"));

        Files.write(fileXml, Collections.singletonList("""
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <cars>
                    <car>
                        <brand>Volkswagen</brand>
                        <model></model>
                        <year>2008</year>
                        <engine>
                            <amount>1.4</amount>
                            <power>150</power>
                            <fuel>Бензин</fuel>
                        </engine>
                    </car>
                </cars>
                """));

        xmlToJson.convert(fileXml.toString(), fileJson.toString());

        assertEquals(
                """
                        {\r
                          "brands" : [ {\r
                            "brand" : {\r
                              "name" : "Volkswagen",\r
                              "cars" : [ {\r
                                "model" : "undefined",\r
                                "year" : 2008,\r
                                "engine" : {\r
                                  "amount" : 1.4,\r
                                  "power" : 150,\r
                                  "fuel" : "Бензин"\r
                                }\r
                              } ]\r
                            }\r
                          } ]\r
                        }""",
                Files.readString(fileJson).trim());
    }

    @Test
    public void XmlWithEmptyEngineTagConvertToJson() throws Exception {
        Path fileXml = Files.createFile(tempDir.resolve("cars5.xml"));
        Path fileJson = Files.createFile(tempDir.resolve("cars5.json"));

        Files.write(fileXml, Collections.singletonList("""
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <cars>
                    <car>
                        <brand>Volkswagen</brand>
                        <model>Tiguan I</model>
                        <year>2008</year>
                    </car>
                </cars>
                """));

        xmlToJson.convert(fileXml.toString(), fileJson.toString());

        assertEquals(
                """
                        {\r
                          "brands" : [ {\r
                            "brand" : {\r
                              "name" : "Volkswagen",\r
                              "cars" : [ {\r
                                "model" : "Tiguan I",\r
                                "year" : 2008,\r
                                "engine" : {\r
                                  "amount" : 0.0,\r
                                  "power" : 0,\r
                                  "fuel" : "undefined"\r
                                }\r
                              } ]\r
                            }\r
                          } ]\r
                        }""",
                Files.readString(fileJson).trim());
    }
}
