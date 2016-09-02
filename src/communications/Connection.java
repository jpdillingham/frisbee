package communications;

import java.util.Observable;

import exceptions.LoggedException;
import messaging.Message;

/** 
 * The {@code Connection} abstract class defines the state and capabilities of a connection. 
 * <p>
 * A {@code Connection}  must belong to a {@link communications.Connector connector} and a {@link communications.Connector connector} may have multiple connections.
 * <p>
 * A {@code Connection}  is used to establish a link to a single data source or destination.
 * <p>
 * When the connection {@link Connection.ConnectionMode mode} is set to {@link Connection.ConnectionMode#READ READ} or {@link Connection.ConnectionMode#READWRITE READWRITE}, 
 * the {@link #read() read}  method may used to retrieve a source {@link messaging.Message message}.
 * <p>
 * When the connection {@link Connection.ConnectionMode mode} is set to {@link Connection.ConnectionMode#WRITE WRITE} or {@link Connection.ConnectionMode#READWRITE READWRITE}, 
 * the {@link #write(Message message) write} method may used to write to a destination {@link messaging.Message message}.
 * <p>
 * 
 * @author adamopan
 * @version 0.1 
 * @since 0.1
 */

public abstract class Connection  extends Observable {
	
	
	/**
	 * Connection states
	 * <li>{@link #OPENING}</li>
	 * <li>{@link #CLOSING}</li>
	 * <li>{@link #OPEN}</li>
	 * <li>{@link #CLOSED}</li>
	 * <li>{@link #PAUSED}</li>
	 * <li>{@link #RESETTING}</li>
	 * <li>{@link #RESET}</li>
	 * <li>{@link #ERROR}</li>
	 */
	private enum ConnectionState {
			/**
			 * The connection is currently being open
			 */
			OPENING,
			
			/**
			 * The connection is currently being closed
			 */
			CLOSING,
			
			/**
			 * The connection is open
			 */
			OPEN,
			
			/**
			 * The connection is closed
			 */
			CLOSED,
			
			/**
			 * The connection is paused
			 */
			PAUSED,
			
			/**
			 * The connection is currently being reset
			 */
			RESETTING,
			
			/**
			 * The connection has been reset
			 */
			RESET,
			
			/**
			 * The connection is in a faulted state
			 */
			ERROR
	};
	
	/**
	 * Connection modes
	 * <li>{@link #READONLY}</li>
	 * <li>{@link #WRITEONLY}</li>
	 * <li>{@link #READWRITE}</li>
	 */
	private enum ConnectionMode {
		/**
		 * The connection only accepts incoming messages
		 */
		READONLY,
		
		/**
		 * The connection only accepts outgoing messages
		 */
		WRITEONLY,
		
		/**
		 * The connection accepts both incoming and outgoing messages
		 */
		READWRITE
	};
	
	
	/**
	 *  The current connection state of a {@code Connection} instance
	 */
	private ConnectionState connectionState = ConnectionState.CLOSED;
	
	/**
	 * The operation mode of the {@code Connection} instance
	 */
	private ConnectionMode connectionMode = ConnectionMode.READWRITE;
	
	/**
	 * Unique ID for this {@code Connection} instance
	 */
	private String connectionID;
	
	/**
	 * The parent #Connector instance to which this connection belongs
	 */
	private Connector parentConnector;
	
	/**
	 * Method used to open a connection
	 * 
	 * @throws LoggedException on failure to establish a connection
	 */
	public abstract void open() throws LoggedException;
	
	/**
	 * Method used to close a connection
	 * 
	 * @throws LoggedException on failure to properly close a connection
	 */
	public abstract void close() throws LoggedException;
	
	/**
	 * Method used to pause a connection
	 * 
	 * @throws LoggedException on failure to pause a connection
	 */
	public abstract void pause() throws LoggedException;
	
	/**
	 * Method used to reset a connection
	 * 
	 * @throws LoggedException on failure to rest a connection
	 */
	public abstract void reset() throws LoggedException;
	
	/**
	 * Wrapper method used verify the connection mode before attempting to retrieve a message
	 * 
	 * @return  the {@link messaging.Message} instance retrieved
	 * @throws LoggedException on failure to retrieve a message
	 */
	public Message read() throws LoggedException{
		if (this.connectionMode == ConnectionMode.READONLY || this.connectionMode == ConnectionMode.READWRITE){
			
			return readMessage();
		}
		else{
			throw new LoggedException(null);
		}
	}
	
	/**
	 * Method used to retrieve a message from a data source
	 * 
	 * @return the {@link messaging.Message} instance retrieved
	 * @throws LoggedException on failure to retrieve a message
	 */
	protected abstract Message readMessage() throws LoggedException;
	
	
	/**
	 * Wrapper method used verify the connection mode before attempting to write a message to a destination
	 * 
	 * @param message the {@link messaging.Message} instance to that is to be output
	 * @throws LoggedException on failure to write message to destination
	 */
	public void write(Message message) throws LoggedException
	{
		if (this.connectionMode == ConnectionMode.WRITEONLY || this.connectionMode == ConnectionMode.READWRITE){
			
			writeMessage(message);
		}
		else{
			throw new LoggedException(null);
		}
	}
	
	/**
	 * Method used to write a message to a destination
	 * 
	 * @throws LoggedException on failure to write message to destination
	 */
	protected abstract void writeMessage(Message message) throws LoggedException;
	
	/**
	 * Returns the current state of the connection
	 * 
	 * @param message the {@link messaging.Message} instance to that is to be output
	 * @return the current connection state
	 * 
	 */
	public ConnectionState getConnectionState() {
		return connectionState;
	}
	
	/**
	 * Returns the connection's operation mode
	 * 
	 * @return the connection's operation mode
	 * 
	 */
	public ConnectionMode getConnectionMode() {
		return connectionMode;
	}
	
	/**
	 * Returns the unique ID for this {@code Connection} instance
	 * 
	 * @return the unique ID for this {@code Connection} instance
	 * 
	 */
	public String getConnectionID() {
		return connectionID;
	}
	
	/**
	 * Returns The parent #Connector instance to which this connection belongs
	 * 
	 * @return the parent #Connector instance to which this connection belongs
	 * 
	 */
	public Connector getParentConnector() {
		return parentConnector;
	}

}
