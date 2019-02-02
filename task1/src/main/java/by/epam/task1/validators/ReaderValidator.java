package by.epam.task1.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Validator class for string line with data to make sphere object.
 * It checks that data is valid.
 *
 * @author Rostislav Pekhovsky
 * @version 0.1
 * @see by.epam.task1.entities.Sphere
 */
public final class ReaderValidator {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ReaderValidator.class);

    /**
     * Private no args constructor.
     * Creating an object of the class is not allowed
     */
    private ReaderValidator() {
    }

    /**
     * This method validates string line by the pattern below.
     * <p>
     * [x] [y] [z] [r]
     * <p/>
     * where x, y, z, r - is not infinite doubles; r > 0
     *
     * @param line line to check
     * @return "true" if it is valid, otherwise "false".
     * *
     */
    public static boolean isSphereValid(final String line) {
        LOGGER.debug("Validating a line: " + line);
        final int minNumberQuantityInLine = 4;
        final int radiusIndex = 3;
        Scanner scanner = new Scanner(line);
        List<Double> doubles = new ArrayList<>();

        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        scanner.close();

        boolean res =  (doubles.size() >= minNumberQuantityInLine)
                && (doubles.get(radiusIndex) >= 0);
        LOGGER.debug("Result: " + res);
        return res;
    }
}
