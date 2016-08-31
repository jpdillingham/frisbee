package exceptions;

import messaging.Message;

/** 
* The {@code LoggedException} class used to wrap exception for logging purposes.
* <p>
* Once an exception is trapped by the {@code LoggedException}, it can be output as {@link messaging.Message message}, using a valid {@link communications.Connection connection}  
* <p>

* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class LoggedException extends Exception{

	private static final long serialVersionUID = 2082278091559813300L;
	
	/**
	 * Error Categories
	 * <li>{@link #GENERAL_ERROR}</li>
	 */
	public static enum ErrorCategory {
		/**
		 * An error that does not fit any other criteria
		 */
		GENERAL_ERROR
	}
	
	/**
	 * The source {@link messaging.Message message} where the exception occurred
	 */
	private Message sourceMessage;

	/**
	 *  The Java exception that was thrown
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
	 * Timestamp when the error occured
	 */
	private Long errorTimestamp;
	
	/**
	 * The Java exception that was thrown
	 */
	private Exception innerException;
	
	
	/**
	 * Class Constructor  
	 *  
	 * @param innerException the Java exception that was thrown, 
	 * @param sourceMessage the source {@link messaging.Message message} where the exception occurred
	 * @param errorClass the class signature where the exception occurred
	 * @param errorMethod the method signature where the exception occurred
	 * @param errorCategorty  the category of the error
	 * @param errorID the  specific ID relating to the error
	 * @param errorTimestamp  the timestamp when the error occured
	 * 
	 */
	LoggedException(Exception innerException,Message sourceMessage,String errorClass,String errorMethod,ErrorCategory errorCategory,String errorID, Long errorTimestamp) {
		
	}
	

}
