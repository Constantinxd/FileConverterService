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
    private CarWrapper readXml(final File inputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CarWrapper.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (CarWrapper) jaxbUnmarshaller.unmarshal(inputFile);
    }

    private AutoparkWrapper transform(final CarWrapper carWrapper) {
        List<Autopark> autoparks = new ArrayList<>();
        HashMap<String, Autopark> autoparksMap = new HashMap<>();

        for (Car car : carWrapper.cars) {
            String brand = (car.brand == null || "".equals(car.brand)) ? "undefined" : car.brand;
            Car newCar = new Car(car.model, car.year, car.engine);

            if (autoparksMap.containsKey(car.brand)) {
                autoparksMap.get(brand).cars.add(newCar);
            } else {
                List<Car> cars = new ArrayList<>();
                cars.add(newCar);
                Autopark autopark = new Autopark(brand, cars);

                autoparks.add(autopark);
                autoparksMap.put(brand, autopark);
            }
        }

        return new AutoparkWrapper(autoparks);
    }

    private void writeJson(final AutoparkWrapper brands, final File outputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, brands);
    }

    private void checkInputFile(final File inputFile) throws IllegalArgumentException {
        if (!inputFile.isFile())
            throw new IllegalArgumentException("Input file is not file");

        FileFilter filter = new FileNameExtensionFilter("Document file", "xml");
        if (!filter.accept(inputFile))
            throw new IllegalArgumentException("Wrong input file extension");

        if (!Files.isReadable(Paths.get(inputFile.getPath())))
            throw new IllegalArgumentException("Can`t read input file");
    }

    private void checkOutputFile(final File outputFile) throws IOException, IllegalArgumentException {
        if (!outputFile.exists() && canCreateFile(outputFile))
            outputFile.createNewFile();

        if (!outputFile.isFile())
            throw new IllegalArgumentException("Output file is not file");

        FileFilter filter = new FileNameExtensionFilter("Document file", "json");
        if (!filter.accept(outputFile))
            throw new IllegalArgumentException("Wrong output file extension");

        if (outputFile.isHidden())
            throw new FileNotFoundException("Output file is hidden");

        if (!Files.isWritable(Paths.get(outputFile.getPath())))
            throw new IllegalArgumentException("Can`t write output file");
    }

    private boolean canCreateFile(final File file) throws IOException {
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

    public void convert(final String inputFilePath, final String outputFilePath) {
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
        } finally {
            System.out.println("File converter service is finished");
        }
    }
}
