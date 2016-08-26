package exceptions;

import messaging.Message;

public class LoggedException extends Exception{

	private static final long serialVersionUID = 2082278091559813300L;
	
	private enum ErrorCategory
	{
		GeneralError
	}
	
	private Message sourceMessage;
	/*
	private String source;
	private String destination;
	private String messageID;
	private String transactionID;
	private Long messageTimestamp;
	*/
	
	private String errorClass;
	private String errorMethod;
	
	private ErrorCategory errorCategory;
	private String errorID;
	private Long errorTimestamp;
	
	private Exception innerException;
	
	LoggedException(Exception innerException,Message sourceMessage,String errorClass,String errorMethod,ErrorCategory errorCategory,String errorID, Long errorTimestamp)
	{
		
	}
	

}
