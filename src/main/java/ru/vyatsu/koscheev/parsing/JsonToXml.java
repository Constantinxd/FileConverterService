package ru.vyatsu.koscheev.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vyatsu.koscheev.model.*;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonToXml {
    private AutoparkWrapper readJson(File inputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AutoparkWrapper autoparkWrapper = mapper.readValue(inputFile, AutoparkWrapper.class);

        return autoparkWrapper;
    }

    private CarWrapper transform(AutoparkWrapper autoparkWrapper) {
        List<Car> cars = new ArrayList<>();

        for (Autopark autopark : autoparkWrapper.autoparks)
            for (Car car : autopark.cars)
                cars.add(new Car(autopark.name, car.model, car.year, car.engine));

        return new CarWrapper(cars);
    }

    private void writeXml(CarWrapper brands, File outputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CarWrapper.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(brands, outputFile);
    }

    private void checkInputFile(File inputFile) throws Exception {
        if (!inputFile.isFile())
            throw new Exception("Input file is not file");

        FileFilter filter = new FileNameExtensionFilter("Document file", "json");
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

        FileFilter filter = new FileNameExtensionFilter("Document file", "xml");
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

            AutoparkWrapper autoparkWrapper = readJson(inputFile);
            CarWrapper carWrapper = transform(autoparkWrapper);
            writeXml(carWrapper, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("File converter service is finished");
        }
    }
}