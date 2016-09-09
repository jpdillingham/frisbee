package configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import communications.Connector;
import exceptions.LoggedException;
import frisbee.Frisbee;
import messaging.MessageFieldConfig;
import messaging.MessageIOConfig;
import messaging.MessageIOConfig.Mode;
import messaging.MessageMapping;

/** 
* The {@code ConfigurationFactory} class instantiates all the configuration items needed for the startup of a frisbee service instance
* <p>
* The {@code ConfigurationFactory} contains all the instantiated {@link communications.Connector connectors} and {@link communications.Connector messages} for a specific frisbee service instance
* <p>
* 
* @author adamopan
* @version 0.1 
* @since 0.1
*/

public class ConfigurationFactory {
	
	/**
	 * Method used to retrieve a matching node from the xml
	 * 
	 * @parm node the parent node to search
	 * @param matchItem the node name to match on
	 * 
	 * @return the list of nodes which passed the match criteria
	 */
	private List<Node> browseChildren(Node node,String matchItem) {
		matchItem = matchItem.trim().toLowerCase();
		NodeList nodeList = node.getChildNodes();
		LinkedList<Node> nodes = new LinkedList<Node>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node innerNode = nodeList.item(i);
			
			if(innerNode.getNodeName().trim().toLowerCase().equals(matchItem)) {
				nodes.add(innerNode);
			}
		}
		
		return nodes;
		
	}
	
	/**
	 * Method used to extract class members from XML
	 * 
	 * @param type the argument type
	 * @param value the parent node to explore
	 * 
	 * @return the instantiated memeber
	 * @throws LoggedException when retrieving configuration failed
	 */
	private Object getObjectsFromXML(String type,Node value) throws LoggedException{
		if (value == null)
			return null;
		
		try {
			
	
			String argValue = null;
			
			if(type != null){
				argValue = value.getTextContent();
			}
			else
				type = "null";
			
			switch(type) {
				case "String":
					return String.valueOf(argValue);
				case "Integer":
					return Integer.valueOf(argValue);
				case "Double":
					return Double.valueOf(argValue);
				case "Boolean":
					return Boolean.valueOf(argValue);
				default:
					NodeList nodeList = value.getChildNodes();
					
					Map<String,Object> members = new HashMap<String,Object>();

					//Go through all the class members
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node innerNode = nodeList.item(i);
						if(innerNode.getNodeType() == Node.ELEMENT_NODE) {
						
							NamedNodeMap innerNodeAttributes = innerNode.getAttributes();
							
							
							String classType = null;
							//grab the configuration data needed for instantiation of the class
							for(int j = 0; j < innerNodeAttributes.getLength(); j++) {
				
								String nodeAttribute = innerNodeAttributes.item(j).getNodeName().trim().toLowerCase();
								String nodeAttributeValue = innerNodeAttributes.item(j).getTextContent();	
								
		
								if(nodeAttribute.equals("class")) {
									classType = nodeAttributeValue;
									break;
								}

							}
							
							if(classType != null)
								members.put(innerNode.getNodeName().trim().toLowerCase(),getObjectsFromXML(classType,innerNode));
						}
					}
					
					
					return members;
			}
					
		} catch (Exception e) {
			
			throw new LoggedException(e);
		}
		
		
	}

	/**
	 * Method used to extract the configuration parameters from an xml filed
	 * 
	 * @param filePath the file path of the frisbee configuration xml 
	 * 
	 * @return  true when configuration loading succeeded 
	 * 
	 * @throws LoggedException when retrieving configuration failed
	 */
	public boolean getConfiguration(String filePath) throws LoggedException {
		try {
			File xmlConfigFile = new File(filePath);
			InputStream xmlInputStream = new FileInputStream(xmlConfigFile);
			boolean xmlHasPassed = this.getConfiguration(xmlInputStream);		
			xmlInputStream.close();
			return xmlHasPassed;
			
		} catch(Exception e)
		{
			throw new LoggedException(e);
		}
		
		
		
	}
	
	/**
	 * Method used to extract text content of xml tag attributes
	 * 
	 * @param node the node attributes to examine
	 * @param match the node attribute from which to extract it's text content 
	 * 
	 * @return  trimmed text content of xml tag attributes 
	 * 
	 */
	private String getTagAttributes(Node node,String match) {
		Node attributeNode = node.getAttributes().getNamedItem(match);
		String textContent = null;
		if( attributeNode != null )
			textContent = attributeNode.getTextContent().trim();
		
		return textContent;
	}
	
	/**
	 * Method used to extract Message Mapping  configuration
	 * 
	 * @param node the node attributes to examine 
	 * @parm matchMessage tag containing the messahe
	 * 
	 * @return  the list of message Mapping configurations
	 * @throws LoggedException 
	 * 
	 */
	private List<MessageMapping> getMessageMapping(Node node,String matchMessage) throws LoggedException {
		
		 List<MessageMapping> messageMappings = new  LinkedList<MessageMapping>();
		 
		//Grab all the Message mapping configurations
		List<Node> messageNodes = this.browseChildren(node, matchMessage);			
		for(Node m : messageNodes) {
			
			String id = this.getTagAttributes(m, "id");
			
			//Get all the message input configuration
			List<MessageIOConfig> messageInputsConfigs = this.getMessageIOConfig(m, "input");
			
		
			//Get all the message output configuration
			List<MessageIOConfig> messageOutputConfigs = this.getMessageIOConfig(m, "output");
			
			//Get all the message field mappings
			for(MessageIOConfig input: messageInputsConfigs) {	
				
				List<MessageFieldConfig> MessageFieldConfig = this.getMessageFieldConfig(m,input.getConnectionID());
				
				MessageMapping messageMap = new MessageMapping(id);
				messageMap.setInput(input);
				messageMap.setFields(MessageFieldConfig);
				
				List<MessageIOConfig> syncOutputs = new LinkedList<MessageIOConfig>();
				List<MessageIOConfig> asyncOutputs = new LinkedList<MessageIOConfig>();
				
				for(MessageIOConfig outputConfig : messageOutputConfigs) {
					switch(outputConfig.getMode()) {
						case ASYNC:
							asyncOutputs.add(outputConfig);
							break;
						case SYNC:
							syncOutputs.add(outputConfig);
							break;
					}
					
				}	
				messageMap.setSyncOutputs(syncOutputs);
				messageMap.setAsyncOutputs(asyncOutputs);
				
				messageMappings.add(messageMap);
				
				
			}
		}
		
		return messageMappings;
	}
	
	/**
	 * Method used to extract Message IO configuration
	 * 
	 * @param node the node attributes to examine 
	 * @param match the node attribute from which to extract the Message IO configuration
	 * 
	 * @return  the list of message IO configurations
	 * 
	 */
	@SuppressWarnings("unchecked")
	private List<MessageIOConfig> getMessageIOConfig(Node node, String match) throws LoggedException {
		
		List<MessageIOConfig> messageIOConfigs = new LinkedList<MessageIOConfig>(); 
		
		List<Node> ios = this.browseChildren(node, match);
		for(Node io: ios) {
			
			Object members = getObjectsFromXML(null,io);

			String id = this.getTagAttributes(io, "id");
			String s_mode = this.getTagAttributes(io, "mode");
			
			Mode mode = MessageIOConfig.Mode.ASYNC;
			
			if ( s_mode != null && s_mode.trim().toLowerCase().equals("sync"))
				mode =  MessageIOConfig.Mode.SYNC;

			Map<String,Object> membersList = null;
			
			if(members instanceof Map<?,?>)
				membersList = (Map<String,Object>) members;
			
			if(id != null)
				id = id.trim();
			
			MessageIOConfig messageIOconfig = new MessageIOConfig(id,mode,membersList);
			
			String [] postProcessOutputMsgs = {"returnMessage","followMessage","eotMessage"};
			
			for(String ppom : postProcessOutputMsgs) {

				List<MessageMapping> messages = this.getMessageMapping(io,ppom);

				switch(ppom) {
					case "returnMessage":
						messageIOconfig.setReturnMessages(messages);
						break;
					case "followMessage":
						messageIOconfig.setFollowMessages(messages);
						break;
					case "eotMessage":
						messageIOconfig.setFollowMessages(messages);
						break;
				}
	
			}
			
			messageIOConfigs.add(messageIOconfig);	
		}
		
		return messageIOConfigs;
	}
	
	/**
	 * Method used to extract Message IO configuration
	 * 
	 * @param node field node to examin
	 * @param inputMatch input node to match on an extract all of it's outputs
	 * 
	 * @return  the list of message field configurations
	 * 
	 */
	@SuppressWarnings("unchecked")
	private List<MessageFieldConfig> getMessageFieldConfig(Node node,String inputMatch) throws LoggedException{
		
		
		List<MessageFieldConfig> messageFieldConfigs = new LinkedList <MessageFieldConfig>();
		List<Node> messageFields = this.browseChildren(node, "field");
		
		for(Node mf: messageFields) {
			
			String fieldID = this.getTagAttributes(mf, "id");
			
			//For every input, set all outputs configurations
			List<Node> messageFieldsInputs = this.browseChildren(mf, "input");
			for(Node mi : messageFieldsInputs) {
				String fieldInputId = this.getTagAttributes(mi, "id");
				if (inputMatch == null || inputMatch.equals(fieldInputId)) {
					
					Object inputs = getObjectsFromXML(null,mi);
					
					//set outputs for this input
					LinkedHashMap<String, Map<String,Object>> outputArguments = new LinkedHashMap<String, Map<String,Object>>(); 
					List<Node> messageFieldsOutputs = this.browseChildren(mf, "output");
					for(Node mo : messageFieldsOutputs) {
						Object outputs = getObjectsFromXML(null,mo);
						String fieldOutputId = this.getTagAttributes(mo, "id");
				
						Map<String,Object> outputsList = null;
						if(outputs instanceof Map<?,?>)
							outputsList = (Map<String,Object>) outputs;
						outputArguments.put(fieldOutputId, outputsList);
					}
					
					Map<String,Object> inputsList = null;
					if(inputs instanceof Map<?,?>)
						inputsList = (Map<String,Object>) inputs;
					
					MessageFieldConfig messageFieldConfig = new MessageFieldConfig(fieldID,fieldInputId, inputsList,outputArguments);
					
					messageFieldConfigs.add(messageFieldConfig);
				}
			}	
		}

		return messageFieldConfigs;
	}
	
	/**
	 * Method used to extract the configuration parameters from an xml inputstream
	 * 
	 * @param xmlInputStream the input stream containing the xml frisbee configuration 
	 * 
	 * @return  true when configuration loading succeeded 
	 * 
	 * @throws LoggedException when retrieving configuration failed
	 */
	public boolean getConfiguration(InputStream xmlInputStream) throws LoggedException {
		
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlInputStream);
			
			doc.getDocumentElement().normalize();
				
			
			List<Node> frisbeeNodes = this.browseChildren(doc, "frisbee");
			
			//Go through all the frisbee configurations
			for(Node frisbeeNode: frisbeeNodes) {
				
				FrisbeeConfig frisbeeConfig = new FrisbeeConfig(this.getTagAttributes(frisbeeNode, "id"));
				
				//Grab all the logger configurations
				frisbeeConfig.setLoggers(this.getMessageMapping(frisbeeNode,"logger"));
				
				//Grab all the Message mapping configurations
				frisbeeConfig.setMessageMappings(this.getMessageMapping(frisbeeNode,"message"));
	
				
				//TODO: Grab all the connector configurations
				List<Node> connectorNodes = this.browseChildren(frisbeeNode, "connector");
				for(Node c : connectorNodes) {
					String classType = this.getTagAttributes(c, "class");
					String id = this.getTagAttributes(c, "id");
					
					System.out.println(classType+","+id);
					
					
					//Grab all the connection configurations
					List<Node> connectioNodes = this.browseChildren(c, "connection");
					for(Node cn : connectioNodes) {
						String connectionClassType = this.getTagAttributes(cn, "class");
						String connectionId = this.getTagAttributes(cn, "id");
						
						System.out.println(connectionClassType+","+connectionId);
					}
					
					Object members = getObjectsFromXML(null,c);
					System.out.println(members);
					
				}
				
				boolean isAdded = Frisbee.addFrisbee(new Frisbee(frisbeeConfig));
			
			}
			
			
		} catch(LoggedException e) {
			throw e;
		} catch(Exception e) {
			throw new LoggedException(e);
			
		}
		
		
		return false;
	}

	public static void main(String[] args) {
		
		ConfigurationFactory cf = new ConfigurationFactory();
		try {
			cf.getConfiguration("./examples/config/frisbeeConfig.xml");
		} catch (LoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
