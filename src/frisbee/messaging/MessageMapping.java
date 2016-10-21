package frisbee.messaging;
import java.lang.reflect.Field;
import java.util.List;

import frisbee.tools.Tools;

/** 
* The {@code MessageMapping} class is used to define the various messages used by the system
* <p>
* A message contains a single input which can be mapped to multiple outputs  
* <p>

* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class MessageMapping {

	
	
	/**
	 * Message Types
	 * <li>{@link #MESSAGE}</li>
	 * <li>{@link #RETURN_MESSAGE}</li>
	 * <li>{@link #FOLLOW_MESSAGE}</li>
	 * <li>{@link #EOT_MESSAGE}</li>
	 */
	public static enum MessageType {
		/**
		 * The standard message type
		 */
		MESSAGE,
		
		/**
		 *  A message that was passed the return values of the parent transaction as an input
		 */
		RETURN_MESSAGE,
		
		/**
		 *  A message that is executed after the completion of each parent transaction with the initial message values as an input
		 */
		FOLLOW_MESSAGE,
		
		/**
		 *  A message that is executed after all the parent transactions have completed with the initial message values as an input
		 */
		EOT_MESSAGE
		
	}
	
	/**
	 *  The type of message represented 
	 */
	private MessageType messageType;
	
	/**
	 * The input configuration values
	 */
	private MessageIOConfig input;
	
	/**
	 * The synchronous output configuration values in processing order
	 */
	private List<MessageIOConfig> syncOutputs;
	
	/**
	 * The asynchronous output configuration values
	 */
	private List<MessageIOConfig> asyncOutputs;
	
	
	/**
	 * The message field mappings
	 */
	private List<MessageFieldConfig> fields;

	/**
	 * The message ID which is not necessarily unique
	 */
	private String messageID;


	/**
	 *  MessageMapping constructor
	 */
	public MessageMapping(String messageID) {
		this.messageID = messageID;
	}


	/**
	 *  Get message Field configuration
	 *  
	 *  @return the field configurations
	 */
	public List<MessageFieldConfig> getFields() {
		return fields;
	}


	/**
	 *  Set message Field configuration
	 *  
	 *  @param fields the field configurations
	 */
	public void setFields(List<MessageFieldConfig> fields) {
		this.fields = fields;
	}

	/**
	 *  Get message asychronous outputs configurations
	 *  
	 *  @return  the asynchronous output configuration values
	 */
	public List<MessageIOConfig> getAsyncOutputs() {
		return asyncOutputs;
	}

	/**
	 *  Set message asychronous outputs configurations
	 *  
	 *  @param asyncOutputs the asynchronous output configuration values
	 */
	public void setAsyncOutputs(List<MessageIOConfig> asyncOutputs) {
		this.asyncOutputs = asyncOutputs;
	}

	/**
	 *  Get message sychronous outputs configurations
	 *  
	 *  @return the synchronous output configuration values
	 */
	public List<MessageIOConfig> getSyncOutputs() {
		return syncOutputs;
	}


	/**
	 *  Set message sychronous outputs configurations
	 *  
	 *  @param syncOutputs sychronous output configuration values
	 */
	public void setSyncOutputs(List<MessageIOConfig> syncOutputs) {
		this.syncOutputs = syncOutputs;
	}


	/**
	 *  Get message single input configuration
	 *  
	 *  @return the configuration values of the message input
	 */
	public MessageIOConfig getInput() {
		return input;
	}


	/**
	 *  Set message single input configuration
	 *  
	 *  @param input the configuration values of the message input 
	 */
	public void setInput(MessageIOConfig input) {
		this.input = input;
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


	/**
	 * @return the messageType
	 */
	public MessageType getMessageType() {
		return messageType;
	}


	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
}
