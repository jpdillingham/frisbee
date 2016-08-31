package messaging;
import java.util.List;
import java.util.Map;

/** 
* The {@code Message} class is used to define the various messages used by the system's {@link communications.Connector connectors}
* <p>
* A message contains a single input which can be mapped to multiple outputs  
* <p>

* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class Message {

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

	/*
	private String source;
	private String destination;
	private String messageID;
	private String transactionID;
	private Long messageTimestamp;
	*/

}
