package frisbee.messaging;

import frisbee.communications.Connection;
import frisbee.configuration.Keywords;

import java.util.*;
import java.util.Map.Entry;

/**
 * The {@code MessageBuilder} class is used to identify incoming payload and apply all necessary data transformations
 *
 * @author adamopan
 * @version 0.1
 * @since 0.1
 */
public final class MessageBuilder implements Observer {


    /**
     * Examines a payload and identifies the best matching {@link frisbee.messaging.MessageMapping message} definition
     *
     * @param messageMappings the message definitions to match on
     * @param inputPayload  the payload to examine
     * @return the best matching message definition
     */
    public static MessageMapping identify(List<MessageMapping> messageMappings, Map<String, Object> inputPayload) {

        MessageMapping result = null;

        int highestMatchCount = 0;


        //go through all message definition attached to this input
        for (MessageMapping messageMap : messageMappings) {

            MessageIOConfig messageInputConfig = messageMap.getInput();
            Map<String, Object> messageInputArgs = messageInputConfig.getArguments();

            String matchStyle = messageInputArgs.get(Keywords.MATCHSTYLE).toString();

            List<MessageFieldConfig> fields = messageMap.getFields();

            int msgMatchCount = 0;
            boolean skipToNextMessage = false;


            //TODO: partial match styles
            /*
			switch(matchStyle) {
			
			case Keyword.MATCHSTYLE_UNDER:
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
            for (MessageFieldConfig field : fields) {

                Object matchOnValue = field.getInputArguments().get(Keywords.MATCHONVALUE);
                //matchOnValue is defined in this field
                if (matchOnValue != null) {

                    Object matchKey = field.getInputArguments().get(Keywords.FIELD_KEY);
                    Object payloadKey = inputPayload.get(matchKey.toString());

                    //field exists in payload
                    if (payloadKey != null) {

                        //we have a match
                        if (payloadKey.equals(matchOnValue)) {

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
                if (!skipToNextMessage) {

                    //is it the best match so far?
                    if (msgMatchCount > highestMatchCount) {

                        highestMatchCount = msgMatchCount;
                        result = messageMap;
                    }

                }

            }


        }

        return result;
    }


    public static Message transform(MessageMapping messageMap, Map<String, Object> inputPayload, Long receivedTimestamp) {

        Message result = null;

        List<MessageFieldConfig> fields = messageMap.getFields();

        for (MessageFieldConfig field : fields) {

            LinkedHashMap<String, Map<String, Object>> outputArgs = field.getOutputArguments();


            // lookup every keyword and apply field  transformation to output payload.

            for (Entry<String, Map<String, Object>> output : outputArgs.entrySet()) {

                String connectionID = output.getKey();

                Map<String, Object> outputFieldConfig = output.getValue();

                Object forceVal = outputFieldConfig.get(Keywords.FORCEVALUE);
                Integer fldLen = (Integer) outputFieldConfig.get(Keywords.FLD_LENGTH);
                Object trunk = outputFieldConfig.get(Keywords.FLD_TRUNK);
                Object lpad = outputFieldConfig.get(Keywords.FLD_L_PAD);
                Object rpad = outputFieldConfig.get(Keywords.FLD_R_PAD);
                Object header = outputFieldConfig.get(Keywords.FLD_HEADER);
                Object footer = outputFieldConfig.get(Keywords.FLD_FOOTER);
                Object fldType = outputFieldConfig.get(Keywords.FLD_TYPE);

                Map<String, Object> outputPayload = null;

                Object matchKey = field.getInputArguments().get(Keywords.FIELD_KEY);
                Object value = inputPayload.get(matchKey.toString());

                if (forceVal != null)
                    value = forceVal;


                if (fldLen != null) {

                    if (value.toString().length() < fldLen) {

                        if (trunk != null) {

                            if (trunk.equals(Keywords.FLD_TRUNK_LEFT)) {
                                //TODO: truncate left to fit
                            } else if (trunk.equals(Keywords.FLD_TRUNK_RIGHT)) {
                                //TODO: truncate right to fit
                            }
                        }

                    } else if (value.toString().length() > fldLen) {

                        if (rpad != null && lpad != null) {
                            //TODO: pad half half, with preference for lpad to be larger
                        } else if (rpad != null) {
                            //TODO: pad only right  and trunk padding rightmost if needed
                        } else if (lpad != null) {
                            //TODO: pad only left, and trunk padding leftmost if needed
                        } else {
                            //TODO: left pad with spaces
                        }

                    }

                }


                if (header != null) {
                    value = header.toString() + value.toString();
                }

                if (footer != null) {
                    value = value.toString() + footer.toString();
                }


                if (fldType != null) {

                    if (!value.getClass().getSimpleName().equals(fldType)) {
                        //TODO: datatype conversions
                    }

                }


            }


        }


        //message transformation
		/*
				Keywords.MSG_LENGTH
				Keywords.MSG_TRUNK
				Keywords.MSG_L_PAD
				Keywords.MSG_R_PAD
				Keywords.MSG_HEADER
				Keywords.MSG_FOOTER
		 */

        return result;
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof Connection) {

            Long receivedTimestamp = System.currentTimeMillis();
            Connection connection = (Connection) o;
            //noinspection unchecked
            Map<String, Object> payload = (Map<String, Object>) arg;
            List<MessageMapping> messageMappings = connection.getInputMappings();

            //TODO:
            //Identify
            //Transform
            //write to sync ouputs thread
            //write to async outputs threads


        }

    }

}
