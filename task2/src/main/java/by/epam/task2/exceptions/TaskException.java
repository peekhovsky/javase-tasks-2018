package by.epam.task2.exceptions;

/**
 * Exception class for this app.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public class TaskException extends Exception {

    /**
     * Constructor.
     * @param message message to set
     * */
    public TaskException(final String message) {
        super(message);
    }
    /**
     * Constructor.
     * @param message message to set
     * @param cause cause of exception
     * */
    public TaskException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
