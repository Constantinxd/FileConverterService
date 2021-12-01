package ru.vyatsu.koscheev.parsing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vyatsu.koscheev.model.*;
import ru.vyatsu.koscheev.model.Car;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlToJson {
    private CarWrapper readXml(File inputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CarWrapper.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (CarWrapper) jaxbUnmarshaller.unmarshal(inputFile);
    }

    private AutoparkWrapper transform(CarWrapper carWrapper) {
        List<Autopark> autoparks = new ArrayList<>();
        HashMap<String, Autopark> autoparksMap = new HashMap<>();

        for (Car car : carWrapper.cars) {
            String brand = (car.brand == null) ? "undefined" : car.brand;
            Car newCar = new Car(car.model, car.year, car.engine);

            if (autoparksMap.containsKey(car.brand)) {
                autoparksMap.get(brand).cars.add(newCar);
            }
            else {
                List<Car> cars = new ArrayList<>();
                cars.add(newCar);
                Autopark autopark = new Autopark(brand, cars);

                autoparks.add(autopark);
                autoparksMap.put(brand, autopark);
            }
        }

        return new AutoparkWrapper(autoparks);
    }

    private void writeJson(AutoparkWrapper brands, File outputFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, brands);
    }

    private void checkInputFile(File inputFile) throws Exception {
        if (!inputFile.isFile())
            throw new Exception("Input file is not file");

        javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("Document file", "xml");
        if (!filter.accept(inputFile))
            throw new Exception("Wrong input file extension");

        if (!Files.isReadable(Paths.get(inputFile.getPath())))
            throw new Exception("Can`t read input file");
    }

    private void checkOutputFile(File outputFile) throws Exception {
        if (!outputFile.exists() && canCreateFile(outputFile))
            outputFile.createNewFile();

        if (!outputFile.isFile())
            throw new Exception("Output file is not file");

        FileFilter filter = new FileNameExtensionFilter("Document file", "json");
        if (!filter.accept(outputFile))
            throw new Exception("Wrong output file extension");

        if (outputFile.isHidden())
            throw new Exception("Output file is hidden");

        if (!Files.isWritable(Paths.get(outputFile.getPath())))
            throw new Exception("Can`t write output file");
    }

    private boolean canCreateFile(File file) throws IOException {
        try {
            return file.createNewFile();
        }
        catch (IOException ioException) {
            throw new IOException("Can`t create file");
        }
        finally {
            if (file.exists())
                file.delete();
        }
    }

    public void convert(String inputFilePath, String outputFilePath) {
        try {
            File inputFile = new File(inputFilePath);
            checkInputFile(inputFile);

            File outputFile = new File(outputFilePath);
            checkOutputFile(outputFile);

            CarWrapper brands = readXml(inputFile);
            AutoparkWrapper autoparkWrapper = transform(brands);
            writeJson(autoparkWrapper, outputFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            System.out.println("File converter service is finished");
        }
    }
}
