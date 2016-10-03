package frisbee.messaging;

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
	
	/* TODO
	private String source;
	private String destination;

	private String transactionID;
	private Long messageTimestamp;
	private Object value;
	*/
	
	/**
	 *  Method returning a string representation
	 *  
	 * @return string containing class name and member values
	 */
	public String toString() {
		
		return Tools.toString(this);
	}
	
}
