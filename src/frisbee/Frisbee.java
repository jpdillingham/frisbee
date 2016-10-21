package frisbee;

import java.util.LinkedList;
import java.util.List;

import frisbee.configuration.FrisbeeConfig;
import frisbee.messaging.MessageBuilder;


public class Frisbee implements Runnable{
	
	
	/**
	 *  The {@link frisbee.configuration.FrisbeeConfig frisbees} instances used by the frisbee service
	 */
	private static List<Frisbee> frisbees = new LinkedList<Frisbee>();

	/**
	 *  Configuration for a frisbee
	 */
	private FrisbeeConfig frisbeeConfig = null;
	
	/**
	 *  Used by frisbee instance to identify and transform input message before routing them to their destination
	 */
	private MessageBuilder messageBuilder = null;
	
	/**
	 *  Constructor for a frisbee 
	 */
	public Frisbee(FrisbeeConfig config) {
		this.setFrisbeeConfig(config);
	}

	
	/**
	 *  Add a frisbee instance to the frisbee service
	 */
	public static boolean addFrisbee(Frisbee frisbee)
	{
		return Frisbee.frisbees.add(frisbee);
	}
	
	
	/**
	 *  Method returning a string representation
	 *  
	 * @return string containing class name and member values
	 */
	public String toString() {
		
		String out = new String();
		
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
	}
	
	/**
	 * @return All the system's frisbee instances
	 */
	public static List<Frisbee> getFrisbees(){
		return Frisbee.frisbees;
	}


	/**
	 *  Starts the frisbee instance
	 */
	@Override
	public void run() {
		//TODO:
		//go through all the message mappings and make the message builder observer
		//start the thread for all the inputs
		
	}
	
}
