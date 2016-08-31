package messaging;

import java.util.List;
import java.util.Map;

/** 
* The {@code MessageFieldConfig} class is used to define field mappings found within a {@link messaging.Message message}
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class MessageFieldConfig {

	/**
	 * The input configuration parameters for a field
	 */
	private Map<String,Object> inputArguments;
	
	/**
	 * The ouput configuration parameters for every output in order of precedence
	 */
	private List<Map<String,Object>> outputArguments; 
	
	
}
