package frisbee.messaging;

import java.util.Map;

import frisbee.communications.Connection;
import frisbee.tools.Tools;

/** 
* The {@code Message} class is a the live instance of a message as it is used by a {@link frisbee.communications.Connection connection}
* <p>
* A message contains a single input which can be mapped to multiple outputs  
* <p>

* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class Message {
	
	
	/**
	 *  The message source connection
	 */
	private Connection source;
	
	/**
	 *  The message destination connection
	 */
	private Connection destination;
	
	/**
	 *  The message type id 
	 */
	private MessageMapping messageMapping;

	/**
	 *  An optional transaction identified
	 */
	private String transactionID;
	

	/**
	 *  message reception timestamp
	 */
	private Long receivedTimestamp;
	
	/**
	 *  message sent timestamp
	 */
	private Long sentTimestamp;
	
	/**
	 *  the payload received
	 */
	private Map<String,Object> inputPayload;
	
	
	/**
	 *  the payload sent
	 */
	private Map<String,Object> outputPayload;
	

	/**
	 *  Message Constructor
	 */
	public Message(String transactionID, Connection source, Connection destination,MessageMapping messageMapping,Long receivedTimestamp, Long sentTimestamp,Map<String,Object> inputPayload,Map<String,Object> outputPayload ) {
	
		this.transactionID = transactionID;
		this.source = source;
		this.destination = destination;
		this.messageMapping = messageMapping;
		this.receivedTimestamp = receivedTimestamp;
		this.sentTimestamp = sentTimestamp;
		
	}
	
	/**
	 *  Method returning a string representation
	 *  
	 * @return string containing class name and member values
	 */
	@Override
	public String toString() {
		
		return Tools.toString(this);
	}
	
}
