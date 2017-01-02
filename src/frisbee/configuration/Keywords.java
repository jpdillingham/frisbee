package frisbee.configuration;

/** 
* The {@code Keywords} class contains constants reflecting keywords found in the frisbee configuration file
*
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public final class Keywords {
	
	//tag containing the match style
	public static final String MATCHSTYLE  = "matchStyle";
	
	//all fields are present in payload and all values match
	public static final String MATCHSTYLE_EXACT  = "exact"; 
	
	//payload is smaller than the message because it's missing fields at the end
	public static final String MATCHSTYLE_UNDER  = "under"; 
	
	//payload is bigger than the message and has extra fields at the end, to be disregarded
	public static final String MATCHSTYLE_OVER = "over";
	
	//a combination of both under and over
	public static final String MATCHSTYLE_ANY = "any";
	
	//tag containing the value to match on
	public static final String MATCHONVALUE =  "matchOnValue";
	
	//tag containing the value to force on output
	public static final String FORCEVALUE =  "forceOuputValue";
	
	//tag containing the field identifier
	public static final String FIELD_KEY =  "key";

}
