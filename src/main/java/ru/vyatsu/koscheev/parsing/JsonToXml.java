package ru.vyatsu.koscheev.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vyatsu.koscheev.model.*;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonToXml {
    private AutoparkWrapper readJson(final File inputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AutoparkWrapper autoparkWrapper = mapper.readValue(inputFile, AutoparkWrapper.class);

        return autoparkWrapper;
    }

    private CarWrapper transform(final AutoparkWrapper autoparkWrapper) {
        List<Car> cars = new ArrayList<>();

        for (Autopark autopark : autoparkWrapper.autoparks) {
            if (autopark.cars != null) {
                for (Car car : autopark.cars)
                    cars.add(new Car(autopark.name, car.model, car.year, car.engine));
            }
        }

        return new CarWrapper(cars);
    }

    private void writeXml(final CarWrapper brands, final File outputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CarWrapper.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(brands, outputFile);
    }

    private void checkInputFile(final File inputFile) throws IllegalArgumentException  {
        if (!inputFile.isFile())
            throw new IllegalArgumentException("Input file is not file");

        FileFilter filter = new FileNameExtensionFilter("Document file", "json");
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

        FileFilter filter = new FileNameExtensionFilter("Document file", "xml");
        if (!filter.accept(outputFile))
            throw new IllegalArgumentException("Wrong output file extension");

        if (outputFile.isHidden())
            throw new IllegalArgumentException("Output file is hidden");

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

            AutoparkWrapper autoparkWrapper = readJson(inputFile);
            CarWrapper carWrapper = transform(autoparkWrapper);
            writeXml(carWrapper, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("File converter service is finished");
        }
    }
}