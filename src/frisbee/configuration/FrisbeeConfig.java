package frisbee.configuration;

import java.util.LinkedList;
import java.util.List;

import frisbee.communications.Connector;
import frisbee.messaging.MessageMapping;
import frisbee.tools.Tools;


/** 
* The {@code FrisbeeConfig} class is used to contain all the configuration values of a grisbee instance
* @since 0.1
*/
public class FrisbeeConfig {
	
	/**
	 *  Optional identifier for the frisbee instance
	 */
	private String frisbeeID;
	
	/**
	 *  The {@link frisbee.communications.Connector connectors} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<Connector> connectors = new LinkedList<Connector>();
	
	/**
	 *  The {@link frisbee.messaging.MessageMapping messagesMappings} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<MessageMapping> messageMappings = new LinkedList<MessageMapping>();
	
	/**
	 *  The {@link frisbee.messaging.MessageMapping loggers} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<MessageMapping> loggers = new LinkedList<MessageMapping>();

	/**
	 *  Get the loggers configuration values for a frisbee instance
	 *  
	 *  @return the loggers configuration
	 */
	public List<MessageMapping> getLoggers() {
		return loggers;
	}

	/**
	 *  Sets the logger configuration values for a frisbee instance
	 *  
	 *  @param loggers the loggerconfigurations
	 */
	public void setLoggers(List<MessageMapping> loggers) {
		this.loggers = loggers;
	}

	/**
	 *  Get the message mappings  for a frisbee instance
	 *  
	 *  @return the message mappings
	 */
	public List<MessageMapping> getMessageMappings() {
		return messageMappings;
	}

	/**
	 *  Sets the message mappings for a frisbee instance
	 *  
	 *  @param messageMappings the message mappings
	 */
	public void setMessageMappings(List<MessageMapping> messageMappings) {
		this.messageMappings = messageMappings;
	}

	/**
	 *  Get the connector configurations for a frisbee instance
	 *  
	 *  @return  the connector configuration values
	 */
	public List<Connector> getConnectors() {
		return connectors;
	}

	/**
	 *  Adds a connector configurations for a frisbee instance
	 *  
	 *  @param connector the connector configuration values
	 */
	public void addConnector(Connector connector) {
		this.connectors.add(connector);
	}

	
	public FrisbeeConfig(String id){
		
		this.frisbeeID = id;
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