package by.epam.task4.exception;

/**
 * Medicine entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public class TaskRuntimeException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public TaskRuntimeException(final String message) {
        super(message);
    }
}
