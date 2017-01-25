package frisbee.exceptions;

import frisbee.messaging.MessageMapping;

/**
 * The {@code LoggedException} class used to wrap exception for logging purposes.
 * <p>
 * Once an exception is trapped by the {@code LoggedException}, it can be output as {@link frisbee.messaging.MessageMapping message}, using a valid {@link frisbee.communications.Connection connection}
 * <p>
 *
 * @author adamopan
 * @version 0.1
 * @since 0.1
 */
public class LoggedException extends Exception {

    private static final long serialVersionUID = 2082278091559813300L;
    /**
     * The source {@link frisbee.messaging.MessageMapping message} where the exception occurred
     */
    private MessageMapping sourceMessage;
    /**
     * The Java exception that was thrown
     */
    private String errorClass;
    /**
     * The method signature where the exception occurred
     */
    private String errorMethod;
    /**
     * The category of the error
     */
    private ErrorCategory errorCategory;
    /**
     * Specific ID relating to the error
     */
    private String errorID;
    /**
     * Timestamp when the error occurred
     */
    private Long errorTimestamp;
    /**
     * The Java exception that was thrown
     */
    private Exception innerException;

    /**
     * Temporary Class Constructor, to be removed as development advances
     */
    public LoggedException(Exception e) {
        super(e);
    }

    /**
     * Class Constructor
     *
     * @param innerException the Java exception that was thrown,
     * @param sourceMessage  the source {@link frisbee.messaging.MessageMapping message} where the exception occurred
     * @param errorClass     the class signature where the exception occurred
     * @param errorMethod    the method signature where the exception occurred
     * @param errorCategory the category of the error
     * @param errorID        the  specific ID relating to the error
     * @param errorTimestamp the timestamp when the error occurred
     */
    public LoggedException(Exception innerException, MessageMapping sourceMessage, String errorClass, String errorMethod, ErrorCategory errorCategory, String errorID, Long errorTimestamp) {

    }

    /**
     * Error Categories
     * <li>{@link #GENERAL_ERROR}</li>
     */
    public enum ErrorCategory {
        /**
         * An error that does not fit any other criteria
         */
        GENERAL_ERROR
    }


}
