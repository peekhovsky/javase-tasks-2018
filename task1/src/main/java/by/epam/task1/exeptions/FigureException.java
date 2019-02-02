package by.epam.task1.exeptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Exception class for figures.
 * Determines that figures are not valid.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * */
public class FigureException extends Exception {
    /**
     * Error codes.
     * */
    public enum ErrorCodes {
        /**
         * Default exception (use if there is an error message.
         * */
        DEFAULT,
        /**
         * Double is NaN or infinity.
         * */
        DOUBLE_IS_NOT_VALID,
        /**
         * Figure is not valid (radius < 0 or double numbers are invalid).
         * */
        FIGURE_IS_NOT_VALID,
        /**
         * Number is Infinity or -Infinity.
         * */
        NUMBER_IS_TOO_BIG,
        /**
         * Sphere is not valid (radius < 0 or double numbers are invalid).
         * */
        SPHERE_IS_NOT_VALID,
        /**
         * Plane is not valid (radius < 0 or double numbers are invalid).
         * */
        PLANE_IS_NOT_VALID
    }

    /**
     * Logger.
     * */
    private static final Logger LOGGER
            = LogManager.getLogger(FigureException.class);
    /**
     * Particular error code.
     * */
    private final ErrorCodes errorCode;

    /**
     * Exception constructor.
     * @param errorMessage message
     * */
    public FigureException(final String errorMessage) {
        super(errorMessage);
        LOGGER.warn("Exception message: " + errorMessage);
        this.errorCode = ErrorCodes.DEFAULT;
    }

    /**
     * Exception constructor.
     * @param newErrorCode error code
     * */
    public FigureException(final ErrorCodes newErrorCode) {
        super();
        LOGGER.warn("Exception error code: " + newErrorCode.name());
        this.errorCode = newErrorCode;
    }

    /**
     * @return error code of exception
     * */
    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
