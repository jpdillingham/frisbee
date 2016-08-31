package messaging;

import java.util.Map;

/** 
* The {@code MessageIOConfig} class is used to define input and output global configuration for a given {@link messaging.Message message}
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class MessageIOConfig {

	/**
	 *  Operation Modes
	 * <li>{@link #SYNC}</li>
	 * <li>{@link #ASYNC}</li>
	 */
	private enum Mode { 
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
	 *  Message configuration variable for a given {@link messaging.Message message} message input or output 
	 */
	private Map<String,Object> arguments;
}
