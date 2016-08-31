package communications;

import java.util.Map;
import java.util.Observable;

import exceptions.LoggedException;
import messaging.Message;

/** 
* The {@code Connector} abstract class defines the state and capabilities of a connector. 
* <p>
* A {@code Connector} defines to the general connection properties between one or many sources and destinations.
* <p>
* A {@code Connector} may have a single or multiple {@link communications.Connection connections} attributed to it when supported.
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
	 *  The {@link communications.Connection connections} managed by this {@code Connector}
	 */
	private Map<String,Connection> connections;
	
	/**
	 * The state of the {@code Connector} instance
	 */
	private ConnectorState connectorState = ConnectorState.DISCONNECTED;

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
	 * Method used to obtain all {@link communications.Connection connections} managed by this {@code Connector}
	 * 
	 * @return the {@link communications.Connection connections} managed by this {@code Connector}
	 */
	public Map<String,Connection> getConnections() {
		return connections;
	}

	/**
	 * Method used to add a {@link communications.Connection connections} instance to the {@code Connector}
	 * 
	 * @param connection {@link communications.Connection connections} instance to be added
	 * 
	 * @throws LoggedException if connection already exists
	 */
	public void addConnection(Connection connection) throws LoggedException {
	
		if(!this.connections.containsKey(connection.getConnectionID()))
			this.connections.put(connection.getConnectionID(), connection);
		else
			throw new LoggedException();
	}
	
	/**
	 * Method used to remove a {@link communications.Connection connections} instance to the {@code Connector}
	 * 
	 * @return true on successful removal, false on failure
	 * 
	 */
	public boolean removeConnection(Connection connection) 
	{
		connection.close();
		return this.connections.remove(connection.getConnectionID(), connection);
	}
	
	/**
	 * Method used to obtain the connector's state
	 * 
	 * @return the connector's state
	 */
	public ConnectorState getConnectorState() {
		return connectorState;
	}

	
}
