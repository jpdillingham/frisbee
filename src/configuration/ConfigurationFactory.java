package configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
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
import messaging.Message;

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
	 *  The {@link communications.Connector connectors} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<Connector> connectors = new LinkedList<Connector>();
	
	/**
	 *  The {@link messaging.Message messages} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<Message> messages = new LinkedList<Message>();
	
	/**
	 *  The {@link messaging.Message loggers} instances managed by this {@code ConfigurationFactory}
	 */
	private  List<Message> loggers = new LinkedList<Message>();
	
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
	 * Method used to extract the configuration parameters from an xml inputstream
	 * 
	 * @param xmlInputStream the input stream containing the cml frisbee configuration 
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
			
			for(Node frisbeeNode: frisbeeNodes) {

				List<Node> loggerNodes = this.browseChildren(frisbeeNode, "logger");
	
				for(Node l : loggerNodes) {
					List<Node> loggerFields = this.browseChildren(l, "field");
					
					for(Node lf: loggerFields) {
						Object members = getObjectsFromXML(null,lf);
						String id = lf.getAttributes().getNamedItem("id").getTextContent().trim();
						System.out.println(id+":"+members);
					}
					
				}
				
				List<Node> connectorNodes = this.browseChildren(frisbeeNode, "connector");
				
				for(Node c : connectorNodes) {
				}
				
				
				List<Node> messageNodes = this.browseChildren(frisbeeNode, "message");
			
				for(Node m : messageNodes) {
				
				}
				
				
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
