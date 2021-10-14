package ru.vyatsu.koscheev.fileConverterService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlToJson {
    private static List<BrandCar> readXml(String inputFile) {
        List<BrandCar> cars = new ArrayList<>();

        try {
            XMLStreamReader xml = XMLInputFactory.newInstance().createXMLStreamReader(inputFile, new FileInputStream(inputFile));

            if (xml.hasNext()) {
                xml.next();
                if (!(xml.isStartElement() && xml.getLocalName().equals("cars")))
                    throw new Exception("have no cars");
            }

            while (xml.hasNext() && !(xml.isEndElement() && xml.getLocalName().equals("cars"))) {
                xml.next();

                if (xml.isStartElement() && xml.getLocalName().equals("car")) {
                    BrandCar car = new BrandCar(null, 0, null);

                    while (xml.hasNext() && !(xml.isEndElement() && xml.getLocalName().equals("car"))) {
                        xml.next();

                        if (xml.isStartElement() && xml.getLocalName().equals("brand")) {
                            xml.next();

                            if (xml.hasText() && xml.getText().trim().length() > 0)
                                car.brand = xml.getText();
                        }
                        else if (xml.isStartElement() && xml.getLocalName().equals("model")) {
                            xml.next();

                            if (xml.hasText() && xml.getText().trim().length() > 0)
                                car.model = xml.getText();
                        }
                        else if (xml.isStartElement() && xml.getLocalName().equals("year")) {
                            xml.next();

                            if (xml.hasText() && xml.getText().trim().length() > 0)
                                car.year = Integer.parseInt(xml.getText());
                        }
                        else if (xml.isStartElement() && xml.getLocalName().equals("engine")) {
                            BrandCar.Engine engine = car.new Engine();

                            while (xml.hasNext() && !(xml.isEndElement() && xml.getLocalName().equals("engine"))) {
                                xml.next();

                                if (xml.isStartElement() && xml.getLocalName().equals("amount")) {
                                    xml.next();

                                    if (xml.hasText() && xml.getText().trim().length() > 0)
                                        engine.amount = Double.parseDouble(xml.getText());
                                }
                                else if (xml.isStartElement() && xml.getLocalName().equals("power")) {
                                    xml.next();

                                    if (xml.hasText() && xml.getText().trim().length() > 0)
                                        engine.power = Integer.parseInt(xml.getText());
                                }
                                else if (xml.isStartElement() && xml.getLocalName().equals("fuel")) {
                                    xml.next();

                                    if (xml.hasText() && xml.getText().trim().length() > 0)
                                        engine.fuel = xml.getText();
                                }
                            }

                            car.engine = engine;
                        }
                    }
                    cars.add(car);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cars;
    }

    private static List<BrandAutopark> getBrandList(List<BrandCar> brandCars) {
        List<BrandAutopark> brands = new ArrayList<>();
        List<String> brandNames = new ArrayList<>();

        for (BrandCar bc : brandCars) {
            if (!brandNames.contains(bc.brand)) {
                brandNames.add(bc.brand);

                brands.add(new BrandAutopark(bc.brand, new ArrayList<>()));
            }
        }

        for (BrandCar bc : brandCars)
            for (BrandAutopark b : brands)
                if (b.name.equals(bc.brand))
                    b.cars.add(new Car(bc.model, bc.year, bc.engine));

        return brands;
    }

    private static void writeJson(List<BrandAutopark> brands, String outputFile) {
        try {
            List<BrandWrapper> brandWrapperList = new ArrayList<>();

            for (BrandAutopark b : brands)
                brandWrapperList.add(new BrandWrapper(b));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(outputFile));

            gson.toJson(new BrandsWrapper(brandWrapperList), writer);

            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convert(String inputFile, String outputFile) {
        try {
            List<BrandCar> cars = readXml(inputFile);
            List<BrandAutopark> brands = getBrandList(cars);
            writeJson(brands, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        XmlToJson.convert(args[0], args[1]);
    }
}
