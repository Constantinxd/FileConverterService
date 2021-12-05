package ru.vyatsu.koscheev.parsing;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJsonToXml {
    private static JsonToXml jsonToXml;

    @BeforeAll
    public static void init() {
        jsonToXml = new JsonToXml();
    }

    @Test
    public void jsonConvertToXml1(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWith1Object.xml";
        final String jsonFilePath = "json/jsonWith1Object.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.json"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.xml"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.xml"));

            FileUtils.copyInputStreamToFile(inputStreamJson, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamXml, expectedFilePath.toFile());

            jsonToXml.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath).trim());
        }
    }

    @Test
    public void jsonConvertToXml2(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWith3Object.xml";
        final String jsonFilePath = "json/jsonWith3Object.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.json"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.xml"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.xml"));

            FileUtils.copyInputStreamToFile(inputStreamJson, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamXml, expectedFilePath.toFile());

            jsonToXml.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath).trim());
        }
    }

    @Test
    public void jsonWithEmptyModelTagConvertToXml(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWithUndefinedModelTag.xml";
        final String jsonFilePath = "json/jsonWithEmptyModelTag.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.json"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.xml"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.xml"));

            FileUtils.copyInputStreamToFile(inputStreamJson, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamXml, expectedFilePath.toFile());

            jsonToXml.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath).trim());
        }
    }

    @Test
    public void jsonWithEmptyEngineTagConvertToXml(@TempDir Path tempDir) throws Exception {
        final String xmlFilePath = "xml/xmlWithUndefinedEngineTag.xml";
        final String jsonFilePath = "json/jsonWithEmptyEngineTag.json";

        try (InputStream inputStreamXml = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
             InputStream inputStreamJson = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {

            Path inputFilePath = Files.createFile(tempDir.resolve("inputFile.json"));
            Path expectedFilePath = Files.createFile(tempDir.resolve("expectedFile.xml"));
            Path actualFilePath = Files.createFile(tempDir.resolve("actualFile.xml"));

            FileUtils.copyInputStreamToFile(inputStreamJson, inputFilePath.toFile());
            FileUtils.copyInputStreamToFile(inputStreamXml, expectedFilePath.toFile());

            jsonToXml.convert(inputFilePath.toString(), actualFilePath.toString());

            assertEquals(Files.readString(expectedFilePath), Files.readString(actualFilePath).trim());
        }
    }
}
