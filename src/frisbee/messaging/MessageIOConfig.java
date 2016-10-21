package frisbee.messaging;

import java.util.List;
import java.util.Map;

import frisbee.tools.Tools;

/** 
* The {@code MessageIOConfig} class is used to define input and output global configuration for a given {@link frisbee.messaging.MessageMapping message}
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class MessageIOConfig  {

	/**
	 *  Operation Modes
	 * <li>{@link #SYNC}</li>
	 * <li>{@link #ASYNC}</li>
	 */
	public static enum Mode { 
		/**
		 * In synchronous operation outputs and inputs will be processed only when the previous input or output has finished
		 */
		SYNC,
		/**
		 * In asynchronous operation  outputs and inputs will be processed independently 
		 */
		ASYNC 
	};
	
	/**
	 * The operation mode for an output
	 */
	private Mode mode;
	
	/**
	 *  Unique ID used by an input or output 
	 */
	private String connectionID;
	
	/**
	 *  Message configuration variable for a given {@link frisbee.messaging.MessageMapping message} message input or output 
	 */
	private Map<String,Object> arguments;
	
	
	/**
	 *  List of return messages to be executed using output's return values
	 */
	private List<MessageMapping> returnMessages;
	

	/**
	 *  List of follow up message messages to be executed after every single message output
	 */
	private List<MessageMapping> followMessages;
	
	/**
	 *  List of follow up end of transaction messages to be executed after all retrieved message outputs have been completed
	 */
	private List<MessageMapping> eotMessages;
	
	/**
	 *  The message IO configuration constructor
	 */
	public MessageIOConfig(String connectionID, Mode mode, Map<String,Object> arguments) {
		
		this.connectionID = connectionID;
		if(mode == null )
			this.setMode(Mode.ASYNC);
		this.setMode(mode);
		
		this.setArguments(arguments);
	}


	/**
	 *  Returns the configuration options for this Message IO
	 */
	public Map<String,Object> getArguments() {
		return arguments;
	}


	/**
	 *  sets the configuration options for this Message IO
	 * 
	 *  @param arguments the configuration
	 */
	public void setArguments(Map<String,Object> arguments) {
		this.arguments = arguments;
	}

	/**
	 *  Returns the connectionID
	 */
	public String getConnectionID() {
		return connectionID;
	}

	/**
	 *  Get the message mapping configurations for post processing of the output return values
	 * 
	 *  @return the message mapping configuration
	 */
	public List<MessageMapping> getReturnMessages() {
		return returnMessages;
	}

	/**
	 *  Set the message mapping configurations for post processing of the output return values
	 * 
	 *  @param  returnMessages the message mapping configuration values
	 */
	public void setReturnMessages(List<MessageMapping> returnMessages) {
		this.returnMessages = returnMessages;
	}


	/**
	 *  Get the message mapping configurations for post processing per message transmission attempt
	 * 
	 *  @return the message mapping configuration
	 */
	public List<MessageMapping> getFollowMessages() {
		return followMessages;
	}


	/**
	 *  Set the message mapping configurations for post processing per message transmission attempt
	 * 
	 *  @param followMessages the message mapping configuration values
	 */
	public void setFollowMessages(List<MessageMapping> followMessages) {
		this.followMessages = followMessages;
	}


	/**
	 *  Get the message mapping configurations for post processing after complete end of transmission
	 * 
	 *  @return the message mapping configuration
	 */
	public List<MessageMapping> getEotMessages() {
		return eotMessages;
	}

	/**
	 *  Set the message mapping configurations for post processing after complete end of transmission
	 * 
	 *  @param eotMessages the message mapping configuration values
	 */
	public void setEotMessages(List<MessageMapping> eotMessages) {
		this.eotMessages = eotMessages;
	}

	/**
	 *  Get the operation mode
	 * 
	 *  @return the operation mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 *  Set the operation mode
	 * 
	 *  @return the operation mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
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
