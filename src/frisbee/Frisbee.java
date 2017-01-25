package frisbee;

import frisbee.communications.Connection;
import frisbee.communications.Connector;
import frisbee.configuration.FrisbeeConfig;
import frisbee.messaging.MessageBuilder;
import frisbee.messaging.MessageIOConfig;
import frisbee.messaging.MessageMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Frisbee implements Runnable {

    /**
     * The {@link frisbee.configuration.FrisbeeConfig frisbees} instances used by the frisbee service
     */
    private static List<Frisbee> frisbees = new LinkedList<>();

    /**
     * Configuration for a frisbee
     */
    private FrisbeeConfig frisbeeConfig = null;

    /**
     * Used by frisbee instance to identify and transform input message before routing them to their destination
     */
    private MessageBuilder messageBuilder = null;

    /**
     * Used by frisbee instance to identify the running connection listener threads
     */
    private Map<String, Thread> listenerThreads = null;

    /**
     * Constructor for a frisbee
     */
    public Frisbee(FrisbeeConfig config) {
        this.setFrisbeeConfig(config);
        this.messageBuilder = new MessageBuilder();
        this.listenerThreads = new HashMap<>();
    }


    /**
     * Add a frisbee instance to the frisbee service
     *
     * @return boolean success or failure
     */
    public static boolean addFrisbee(Frisbee frisbee) {
        return Frisbee.frisbees.add(frisbee);
    }

    /**
     * @return All the system's frisbee instances
     */
    public static List<Frisbee> getFrisbees() {
        return Frisbee.frisbees;
    }

    /**
     * Method returning a string representation
     *
     * @return string containing class name and member values
     */
    public String toString() {

        String out = "";

        for (Frisbee f : frisbees) {
            out += f.getFrisbeeConfig();
        }

        return out;
    }

    /**
     * @return the frisbeeConfig
     */
    public FrisbeeConfig getFrisbeeConfig() {
        return frisbeeConfig;
    }

    /**
     * @param frisbeeConfig the frisbeeConfig to set
     */
    public void setFrisbeeConfig(FrisbeeConfig frisbeeConfig) {
        this.frisbeeConfig = frisbeeConfig;
        this.addConnectionObservers();
    }

    /**
     * Adds the frisbee instance's MessageBuilder as an observer to all the input connections
     */
    private void addConnectionObservers() {

        for (MessageMapping m : this.frisbeeConfig.getMessageMappings()) {
            MessageIOConfig input = m.getInput();
            for (Connector connector : this.frisbeeConfig.getConnectors()) {
                Connection connection = connector.getConnections().get(input.getConnectionID());
                if (connection != null) {
                    connection.addObserver(this.messageBuilder);
                }
            }

        }
    }

    /**
     * Deletes the frisbee instance's MessageBuilder from being a connection observer
     */
    private void deleteConnectionObservers() {

        for (MessageMapping m : this.frisbeeConfig.getMessageMappings()) {
            MessageIOConfig input = m.getInput();
            for (Connector connector : this.frisbeeConfig.getConnectors()) {
                Connection connection = connector.getConnections().get(input.getConnectionID());
                if (connection != null) {
                    connection.deleteObserver(this.messageBuilder);
                }
            }

        }
    }


    /**
     * Creates and starts all the input connection listeners
     */
    @Override
    public void run() {

        for (MessageMapping m : this.frisbeeConfig.getMessageMappings()) {
            MessageIOConfig input = m.getInput();
            for (Connector connector : this.frisbeeConfig.getConnectors()) {
                String connectionID = input.getConnectionID();
                Connection connection = connector.getConnections().get(connectionID);
                if (connection != null) {
                    if (!this.listenerThreads.containsKey(connectionID)) {
                        Thread listenerThread = new Thread(connection);
                        this.listenerThreads.put(connectionID, listenerThread);
                        listenerThread.start();
                    }
                }
            }

        }

    }

}
