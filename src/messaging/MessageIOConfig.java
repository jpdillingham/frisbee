package messaging;

import java.util.Map;

public class MessageIOConfig {

	private enum Mode { SYNC,ASYNC };
	
	private Mode mode;
	private String connectionID;
	private Map<String,Object> arguments;
}
