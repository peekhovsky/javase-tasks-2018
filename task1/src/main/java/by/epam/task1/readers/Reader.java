package by.epam.task1.readers;

import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import by.epam.task1.validators.ReaderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class reads sphere object values from file and makes a list with them.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * @see Sphere
 */
@SuppressWarnings("WeakerAccess")
public class Reader {
    /**
     * File path.
     * */
    private String filePath;
    /**
     * Logger.
     * */
    private static final  Logger LOGGER = LogManager.getLogger(Reader.class);

    /**
     * Reader constructor.
     * @param newFilePath path where file is located
     * */
    public Reader(final String newFilePath) {
        this.filePath = newFilePath;
    }

    /**
     * This method makes a list with spheres from file.
     * @return parsed spheres
     * @throws FigureException if cannot read a file
     * */
    public List<Sphere> readSpheres() throws FigureException {
        LOGGER.debug("Opening a file...");
        try (Stream<String> streamFromFile = Files.lines(Paths.get(filePath))) {
            return streamFromFile
                    .filter(ReaderValidator::isSphereValid)
                    .map(this::readLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error: cannot read file.");
            throw new FigureException("Cannot read file!");
        }
    }

    /**
     * @param line line with data to make sphere.
     * @return sphere
     * */
    private Sphere readLine(final String line) {
        LOGGER.debug("Reading line: " + line);
        try (Scanner scanner = new Scanner(line)) {
            Sphere sphere = new Sphere();
            Point point = new Point();

            point.setX(scanner.nextDouble());
            point.setY(scanner.nextDouble());
            point.setZ(scanner.nextDouble());
            sphere.setCentrePoint(point);
            sphere.setRadius(scanner.nextDouble());

            return sphere;
        }
    }
}
