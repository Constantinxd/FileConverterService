package ru.vyatsu.koscheev.parsing;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TestXmlToJson {
    private static XmlToJson xmlToJson;

    @BeforeAll
    public static void init() {
        xmlToJson = new XmlToJson();
    }

    @Test
    public void xmlConvertToJson1(@TempDir Path tempDir) throws IOException {
        final String xmlFilePath = "xml/xmlWith1Object.xml";
        final String jsonFilePath = "json/jsonWith1Object.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.xml"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.json"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.json"));

            FileUtils.copyInputStreamToFile(inputStreamXml, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamJson, expectedFilePath.toFile());

            xmlToJson.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath));
        }
    }

    @Test
    public void xmlConvertToJson2(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWith3Object.xml";
        final String jsonFilePath = "json/jsonWith3Object.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.xml"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.json"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.json"));

            FileUtils.copyInputStreamToFile(inputStreamXml, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamJson, expectedFilePath.toFile());

            xmlToJson.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath));
        }
    }

    @Test
    public void xmlWithEmptyObjectConvertToJson(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWithEmptyObject.xml";
        final String jsonFilePath = "json/jsonWithEmptyObject.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.xml"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.json"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.json"));

            FileUtils.copyInputStreamToFile(inputStreamXml, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamJson, expectedFilePath.toFile());

            xmlToJson.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath));
        }
    }

    @Test
    public void xmlWithEmptyModelTagConvertToJson(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWithEmptyModelTag.xml";
        final String jsonFilePath = "json/jsonWithEmptyModelTag.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.xml"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.json"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.json"));

            FileUtils.copyInputStreamToFile(inputStreamXml, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamJson, expectedFilePath.toFile());

            xmlToJson.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath));
        }
    }

    @Test
    public void xmlWithEmptyEngineTagConvertToJson(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWithEmptyEngineTag.xml";
        final String jsonFilePath = "json/jsonWithEmptyEngineTag.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.xml"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.json"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.json"));

            FileUtils.copyInputStreamToFile(inputStreamXml, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamJson, expectedFilePath.toFile());

            xmlToJson.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath));
        }
    }
}
