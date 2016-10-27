package frisbee.messaging;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import frisbee.communications.Connection;

/** 
* The {@code MessageBuilder} class is used to identify incoming payload and apply all necessary data transformations  
*
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public final class MessageBuilder implements Observer{
	
	

	public static MessageMapping identify(List<MessageMapping> messageMappings,Map<String,Object> payload) {
	
		return null;
	}
	
	
	public static Message transform(MessageMapping messageMap, Map<String,Object> payload ) {
		return null;
	}


	@Override
	public void update(Observable o, Object arg) {

		if( o instanceof Connection) {
			
			Connection connection = (Connection) o;
			List<MessageMapping> messageMappings = connection.getInputMappings();
			
			//TODO:
			//Identify
			//Transform
			//write to sync ouputs thread
			//write to async outputs threads
			
			
		}
		
	}

}
