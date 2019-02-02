package by.epam.task2.readers;

import by.epam.task2.exceptions.TaskException;
import by.epam.task2.trains.Train;
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
 * This class reads Train object values from file and makes a list with them.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Train
 */
@SuppressWarnings("WeakerAccess")
public class Reader {

    /**
     * Left direction string in file.
     * */
    private static final String LEFT_DIR_LITERAL = "LEFT";
    /**
     * File path.
     */
    private String filePath;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(Reader.class);

    /**
     * Reader constructor.
     *
     * @param newFilePath path where file is located
     */
    public Reader(final String newFilePath) {
        this.filePath = newFilePath;
    }

    /**
     * This method makes a list with trains from file.
     *
     * @return parsed train list
     * @throws TaskException if cannot read file
     */
    public List<Train> readTrains() throws TaskException {
        LOGGER.debug("Opening a file...");
        try (Stream<String> streamFromFile = Files.lines(Paths.get(filePath))) {
            return streamFromFile
                    .map(this::readLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error: cannot read file.");
            throw new TaskException("Error: cannot read file.");
        }
    }

    /**
     * @param line line with data to make train.
     * @return train
     */
    private Train readLine(final String line) {
        LOGGER.debug("Reading line: " + line);
        try (Scanner scanner = new Scanner(line)) {
            Train train = new Train();
            train.setName(scanner.next());
            train.setLeavingTimeInSeconds(scanner.nextInt());
            String directionString = scanner.next();
            if (directionString.equalsIgnoreCase(LEFT_DIR_LITERAL)) {
                train.setDirection(Train.Directions.LEFT);
            } else {
                train.setDirection(Train.Directions.RIGHT);
            }
            return train;
        }
    }
}
