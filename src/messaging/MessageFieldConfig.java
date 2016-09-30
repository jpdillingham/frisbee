package messaging;

import java.util.LinkedHashMap;
import java.util.Map;

import tools.Tools;

/** 
* The {@code MessageFieldConfig} class is used to define field mappings found within a {@link messaging.MessageMapping message}
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public class MessageFieldConfig {

	/**
	 *  Unique Identifier for the field
	 */
	private String fieldID;
	

	/**
	 * The input connectionID
	 */
	private String inputID;
	
	/**
	 * The input configuration parameters for a field
	 */
	private Map<String,Object> inputArguments;
	
	/**
	 * The output configuration parameters for every output in order of precedence
	 */
	private LinkedHashMap<String, Map<String,Object>> outputArguments; 
	
	/**
	 *  Message Field Configuration Constructor
	 */
	public MessageFieldConfig(String fieldID,String inputID, Map<String,Object> inputArguments, LinkedHashMap<String, Map<String,Object> > outputArguments) {
		this.fieldID = fieldID;
		this.inputID = inputID;
		this.setInputArguments(inputArguments);
		this.setOutputArguments(outputArguments);
	}

	/**
	 *  Returns the output configuration options for this Message Field
	 */
	public  LinkedHashMap<String, Map<String,Object> > getOutputArguments() {
		return outputArguments;
	}

	/**
	 *  sets the output configuration options for this Message Field
	 * 
	 *  @param outputArguments the configuration
	 */
	public void setOutputArguments( LinkedHashMap<String, Map<String,Object> > outputArguments) {
		this.outputArguments = outputArguments;
	}

	/**
	 *  Returns the input configuration options for this Message Field
	 */
	public Map<String,Object> getInputArguments() {
		return inputArguments;
	}

	/**
	 *  sets the input configuration options for this Message Field
	 * 
	 *  @param inputArguments the configuration
	 */
	public void setInputArguments(Map<String,Object> inputArguments) {
		this.inputArguments = inputArguments;
	}
	
	/**
	 *  Method returning a string representation
	 *  
	 * @return string containing class name and member values
	 */
	public String toString() {
		
		return Tools.toString(this);
	}
	
}
