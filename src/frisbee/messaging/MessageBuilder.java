package frisbee.messaging;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.TreeMap;

import frisbee.communications.Connection;
import frisbee.configuration.Keywords;

/** 
* The {@code MessageBuilder} class is used to identify incoming payload and apply all necessary data transformations  
*
* @author adamopan
* @version 0.1 
* @since 0.1
*/
public final class MessageBuilder implements Observer{
	
	
	/**
	 *  Examines a payload and identifies the best matching {@link frisbee.messaging.MessageMapping message} definition
	 * 
	 *  @param messageMappings the message definitions to match on
	 *  @param payload the payload to examine
	 *  @return the best matching message definition
	 */
	public static MessageMapping identify(List<MessageMapping> messageMappings, Map<String,Object> payload) {
	
		MessageMapping result = null;
		
		int highestMatchCount = 0;
		
		
		//go through all message definition attached to this input 
		for(MessageMapping messageMap : messageMappings) {
			
			MessageIOConfig messageInputConfig = messageMap.getInput();
			Map<String, Object> messageInputArgs = messageInputConfig.getArguments();	
			
			String matchStyle =  messageInputArgs.get(Keywords.MATCHSTYLE).toString();						
		
			List<MessageFieldConfig> fields = messageMap.getFields();
				
			int msgMatchCount = 0;
			boolean skipToNextMessage = false;
				

			//TODOL partial match styles
			/*
			switch(matchStyle) {
			
			case Keywords.MATCHSTYLE_UNDER:
				break;
				
			case Keywords.MATCHSTYLE_OVER:
				break;
				 
			case Keywords.MATCHSTYLE_ANY:
				break;	
				
			case Keywords.MATCHSTYLE_EXACT:
			default:
				break;
			
			}
			*/
			
			
			//Go through all the  fields of this message definition.
			for(MessageFieldConfig field : fields) {
				
				Object matchOnValue  = field.getInputArguments().get(Keywords.MATCHONVALUE);
				//matchOnValue is defined in this field
				if(matchOnValue != null) {
				
					Object matchKey  = field.getInputArguments().get(Keywords.FIELD_KEY);
					Object payloadKey = payload.get(matchKey.toString());
					
					//field exists in payload
					if(payloadKey != null) {
						
						//we have a match
						if(payloadKey.equals(matchOnValue)) {
						
							msgMatchCount++;
							
						}
						//no match
						else {
							skipToNextMessage = true;
							break;
						}
							
						
					}
				
					
				}
				
				//did we find a valid message?
				if(!skipToNextMessage) {
					
					//is it the best match so far?
					if(msgMatchCount > highestMatchCount) {
						
						highestMatchCount = msgMatchCount;
						result = messageMap;
					}
					
				}
				
			}
				
		
		}
		
		return result;
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
