package by.epam.task3.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/***
 * Runtime exception for current project.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public class TaskRuntimeException extends RuntimeException {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(TaskRuntimeException.class);

    /***
     * Constructor.
     * @param message message to display
     */
    public TaskRuntimeException(final String message) {
        super(message);
        LOGGER.error("Error message: " + message);
    }
}
