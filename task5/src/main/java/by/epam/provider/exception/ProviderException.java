package by.epam.provider.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.security.PrivilegedActionException;
import java.util.function.Supplier;

/**
 * Exception class for provider classes.
 *
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@Log4j2
public class ProviderException extends Exception {

    @Getter
    @Setter
    private int errorCode;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ProviderException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ProviderException(String message) {
        super(message);
        log.error(message);
    }

    public ProviderException(String message, int errorCodeNew) {
        super(message);
        this.errorCode = errorCodeNew;
        log.error(message);
    }

    public ProviderException(Exception e, int errorCodeNew) {
        super(e);
        log.error(e.getMessage());
        this.errorCode = errorCodeNew;
    }

    public ProviderException(int errorCodeNew) {
        this.errorCode = errorCodeNew;
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ProviderException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public ProviderException(Throwable cause) {
        super(cause);
        log.error(cause.getMessage());
    }

    /**
     * Constructs a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent
     *                           or unknown.)
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     * @since 1.7
     */
    protected ProviderException(final String message, final Throwable cause,
                                final boolean enableSuppression,
                                final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(message);
    }
}