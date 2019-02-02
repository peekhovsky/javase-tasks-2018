package by.epam.task1.validators;

import by.epam.task1.entities.Plane;
import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Validator class for Sphere and Point entities.
 * It checks that data is valid: objects are not "null", sphere radius > 0,
 * double numbers is not "nan" and "infinite".
 *
 * @author Rostislav Pekhovsky
 * @version 0.1
 * @see Point
 * @see Sphere
 */
public final class FigureValidator {
    /**
     * Logger.
     * */
    private static final Logger LOGGER
            = LogManager.getLogger(FigureValidator.class);

    /**
     * Private no args constructor.
     * Creating an object of the class is not allowed
     * */
    private FigureValidator() {
    }

    /**
     * Validates that sphere is valid (radius > 0, double numbers are valid).
     * @param sphere shpere to check
     * @return "true" if it is valid, otherwise "false".
     * */
    public static boolean isSphereValid(final Sphere sphere) {
        LOGGER.trace("Validating a sphere.");
        LOGGER.trace(sphere);
        return     sphere != null
                && isDoubleValid(sphere.getRadius())
                && sphere.getRadius() > 0.0
                && isPointValid(sphere.getCentrePoint());
    }

    /**
     * Validates that plane is valid (double numbers are valid) and
     * A != 0, B != 0, C != 0 simultaneously.
     * @param plane sphere to check
     * @return "true" if it is valid, otherwise "false".
     * */
    public static boolean isPlaneValid(final Plane plane) {
        LOGGER.trace("Validating a plane.");
        boolean res = plane != null
                && isDoubleValid(plane.getA())
                && isDoubleValid(plane.getB())
                && isDoubleValid(plane.getC())
                && isDoubleValid(plane.getD());

        if (res && plane.getA() == 0
                && plane.getB() == 0
                && plane.getC() == 0) {
            res = false;
        }
        return !res;
    }

    /**
     * Validates that sphere is valid.
     * @param point point to check
     * @return "true" if it is valid, otherwise "false".
     * */
    public static boolean isPointValid(final Point point) {
        LOGGER.trace("Validating a point.");
        return     point != null
                && isDoubleValid(point.getX())
                && isDoubleValid(point.getY())
                && isDoubleValid(point.getZ());
    }

    /**
     * Validates that double is finite and is not "nan".
     * @param number double number to check
     * @return "true" if it is valid, otherwise "false".
     * */
    public static boolean isDoubleValid(final Double number) {
        LOGGER.trace("Validating double number.");
        return      number != null
                &&  Double.isFinite(number)
                && !Double.isNaN(number);
    }
}
