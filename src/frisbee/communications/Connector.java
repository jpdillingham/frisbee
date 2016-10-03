package frisbee.communications;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

import frisbee.exceptions.LoggedException;
import frisbee.tools.Tools;

/** 
* The {@code Connector} abstract class defines the state and capabilities of a connector. 
* <p>
* A {@code Connector} defines to the general connection properties between one or many sources and destinations.
* <p>
* A {@code Connector} may have a single or multiple {@link frisbee.communications.Connection connections} attributed to it when supported.
* <p>
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/


public abstract class Connector extends Observable {
	
	/**
	 * ConnectorState states
	 * <li>{@link #CONNECTING}</li>
	 * <li>{@link #CONNECTED}</li>
	 * <li>{@link #DISCONNECTED}</li>
	 */
	private enum ConnectorState {
		/**
		 * The Connector is establishing a link
		 */
		CONNECTING,
		
		/**
		 * The Connector has established a link;
		 */
		CONNECTED,
		
		/**
		 * The Connector's link has been broken
		 */
		DISCONNECTED
	};
	
	/**
	 * Unique ID for this {@code Connector} instance
	 */
	private String connectorID;
	
	/**
	 *  The {@link frisbee.communications.Connection connections} managed by this {@code Connector}
	 */
	private Map<String,Connection> connections;
	
	/**
	 * The state of the {@code Connector} instance
	 */
	private ConnectorState connectorState = ConnectorState.DISCONNECTED;
	
	
	/**
	 *  Connector configuration
	 */
	private Map<String,Object> connectorParameters;
	

	/**
	 *  The connector constructor
	 */
	public Connector(){
		this.connections = new LinkedHashMap<String,Connection>();
	}
	
	/**
	 * Method used to establish a link
	 * 
	 * @throws LoggedException on failure to rest a connection
	 */
	public abstract void connect() throws LoggedException;
	
	/**
	 * Method used to break a link
	 * 
	 * @throws LoggedException on failure to rest a connection
	 */
	public abstract void disconnect() throws LoggedException;
	
	/**
	 * Method used to obtain all {@link frisbee.communications.Connection connections} managed by this {@code Connector}
	 * 
	 * @return the {@link frisbee.communications.Connection connections} managed by this {@code Connector}
	 */
	public Map<String,Connection> getConnections() {
		return connections;
	}

	/**
	 * Method used to add a {@link frisbee.communications.Connection connections} instance to the {@code Connector}
	 * 
	 * @param connection {@link frisbee.communications.Connection connections} instance to be added
	 * 
	 * @throws LoggedException if connection already exists
	 */
	public void addConnection(Connection connection) throws LoggedException {
	
		if(!this.connections.containsKey(connection.getConnectionID()))
			this.connections.put(connection.getConnectionID(), connection);
		else
			throw new LoggedException(null);
	}
	
	/**
	 * Method used to remove a {@link frisbee.communications.Connection connections} instance to the {@code Connector}
	 * 
	 * @return true on successful removal, false on failure
	 * @throws LoggedException when the closing a connection fails
	 * 
	 */
	public boolean removeConnection(Connection connection) throws LoggedException 
	{
		boolean removed = this.connections.remove(connection.getConnectionID(), connection);
		connection.close();
		return removed;
	}
	
	/**
	 * Method used to obtain the connector's state
	 * 
	 * @return the connector's state
	 */
	public ConnectorState getConnectorState() {
		return connectorState;
	}

	/**
	 * Method used to obtain the connector's ID
	 * 
	 * @return the connector's ID
	 */
	public String getConnectorID() {
		return connectorID;
	}

	
	/**
	 * Method used to set the connector's ID
	 * 
	 * @param the connector's ID
	 */
	public void setConnectorID(String connectorID) {
		this.connectorID = connectorID;
	}

	/**
	 * @return the connectorParameters
	 */
	public Map<String,Object> getConnectorParameters() {
		return connectorParameters;
	}

	/**
	 * @param connectorParameters the connectorParameters to set
	 */
	public void setConnectorParameters(Map<String,Object> connectorParameters) {
		this.connectorParameters = connectorParameters;
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
