package communications;

import java.util.Map;
import java.util.Observable;

import exceptions.LoggedException;

public abstract class Connector extends Observable {
	
	private enum ConnectorState {CONNECTING,CONNECTED,DISCONNECTED};
	
	private Map<String,Connection> connections;
	private ConnectorState connectorState = ConnectorState.DISCONNECTED;

	public abstract void connect() throws Exception;
	public abstract void disconnect() throws Exception;
	
	public Map<String,Connection> getConnections() {
		return connections;
	}

	public void addConnection(Connection connection) throws LoggedException {
	
		if(!this.connections.containsKey(connection.getConnectionID()))
			this.connections.put(connection.getConnectionID(), connection);
		else
			//TODO
			throw new Exception();
	}
	
	public boolean removeConnection(Connection connection) throws LoggedException 
	{
		return this.connections.remove(connection.getConnectionID(), connection);
	}
	
	public ConnectorState getConnectorState() {
		return connectorState;
	}

	
}
