package ru.vyatsu.koscheev.fileConverterService;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class JsonToXml {
    private static BrandsWrapper readJson(String fileName) throws Exception {
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type type = new TypeToken<BrandsWrapper>(){}.getType();

            return gson.fromJson(reader, type);
        }
        catch (Exception e) {
            throw new Exception("An error occurred while reading the file" + e);
        }
    }

    private static void println(XMLStreamWriter writer, int recursion) throws XMLStreamException {
        writer.writeCharacters("\n" + "\t".repeat(recursion));
    }

    private static void writeXml(BrandsWrapper brandsWrapper, String outputFile) {
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(outputFile));
            int count = 0;

            writer.writeStartDocument("utf-8","1.0");
            println(writer, 0);
            writer.writeStartElement("cars");

            for (BrandWrapper bw : brandsWrapper.brands) {
                for (Car c : bw.brand.cars) {
                    println(writer, 1);
                    writer.writeStartElement("car");
                    writer.writeAttribute("id", "" + count++);
                    println(writer, 2);

                    writer.writeStartElement("brand");
                    writer.writeCharacters(bw.brand.name);
                    writer.writeEndElement();
                    println(writer, 2);

                    writer.writeStartElement("model");
                    writer.writeCharacters(c.model);
                    writer.writeEndElement();
                    println(writer, 2);

                    writer.writeStartElement("year");
                    writer.writeCharacters("" + c.year);
                    writer.writeEndElement();
                    println(writer, 2);

                    writer.writeStartElement("engine");
                    println(writer, 3);

                    writer.writeStartElement("amount");
                    writer.writeCharacters("" + c.engine.amount);
                    writer.writeEndElement();
                    println(writer, 3);

                    writer.writeStartElement("power");
                    writer.writeCharacters("" + c.engine.power);
                    writer.writeEndElement();
                    println(writer, 3);

                    writer.writeStartElement("fuel");
                    writer.writeCharacters("" + c.engine.fuel);
                    writer.writeEndElement();
                    println(writer, 2);

                    writer.writeEndElement();
                    println(writer, 1);
                    writer.writeEndElement();
                }
            }

            println(writer, 0);
            writer.writeEndElement();

            writer.writeEndDocument();

            writer.flush();
            writer.close();
        } catch (IOException | XMLStreamException ex) {
            ex.printStackTrace();
        }
    }

    public static void convert(String inputFile, String outputFile) {
        try {
            writeXml(readJson(inputFile), outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       JsonToXml.convert(args[0], args[1]);
    }
}
