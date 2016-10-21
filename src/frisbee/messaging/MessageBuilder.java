package frisbee.messaging;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

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
		// TODO Auto-generated method stub
		
	}

}
