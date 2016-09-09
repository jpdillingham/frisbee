package frisbee;

import java.util.LinkedList;
import java.util.List;

import configuration.FrisbeeConfig;

public class Frisbee {
	
	
	/**
	 *  The {@link configuration.FrisbeeConfig frisbees} instances used by the frisbee service
	 */
	private static List<Frisbee> frisbees = new LinkedList<Frisbee>();

	
	/**
	 *  Constructor for a frisbee 
	 */
	public Frisbee(FrisbeeConfig config) {
		
	}

	/**
	 *  Add a frisbee instance to the frisbee service
	 */
	public static boolean addFrisbee(Frisbee frisbee)
	{
		return Frisbee.frisbees.add(frisbee);
	}
}
