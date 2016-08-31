package configuration;

import java.util.HashMap;

/** 
* The {@code ConfigurationObject} class contains the parameters needed for the instantiation of the {@link communications.Connection connection} 
* and {@link message.Message message} classes used by a frisbee service instance. 
* <p>
* The {@code ConfigurationObject} facilitates the instantiation of object within the {@link configuration.ConfigurationFactory ConfigurationFactory}
* <p>
* @author adamopan
* @version 0.1 
* @since 0.1
*/

public class ConfigurationObject {

	/**
	 * The instance variables to be attached during the construction of a new frisbee service instance
	 */
	private HashMap<String,Object> members;
	
	/**
	 * The instance variables related to operation modes and object identification
	 * 
	 */
	private HashMap<String,String> instanceConfiguration;
	private String objectType = null;
	
	
	/**
	 * Class Constructor specifying the object type to be instantiated
	 * 
	 */
	public ConfigurationObject(String objectType) {
		this.objectType = objectType;
		this.setMembers(new HashMap<String,Object>());
		this.setInstanceConfiguration(new HashMap<String,String>());
	}

	/**
	 * Method to obtain the class instance variables
	 * 
	 *  @return  the instance variables
	 */
	public HashMap<String,Object> getMembers() {
		return members;
	}

	/**
	 * Method to set the class instance variables
	 * 
	 *  @param  the instance variables
	 */
	public void setMembers(HashMap<String,Object> members) {
		this.members = members;
	}

	/**
	 * Method to set a single instance variables
	 * 
	 *  @param key the instance variable's name
	 *  @param value the instance variable's value
	 */
	public void setMember(String key, Object value) {
		this.members.put(key,value);
	}
	
	/**
	 * Method to obtain the instance variables related to operation modes and object identification
	 * 
	 *  @return  the instance variables
	 */
	public HashMap<String,String> getInstanceConfiguration() {
		return instanceConfiguration;
	}

	/**
	 * Method to set the instance variables related to operation modes and object identification
	 * 
	 *  @param  the instance variables
	 */
	public void setInstanceConfiguration(HashMap<String,String> instanceConfiguration) {
		this.instanceConfiguration = instanceConfiguration;
	}
	
	/**
	 * Method to set a single instance variables which is related to operation modes and object identification
	 * 
	 *  @param key the instance variable's name
	 *  @param value the instance variable's value
	 */
	public void setConfigurationItem(String key, String value) {
		this.instanceConfiguration.put(key, value);
	}
		
	
}
