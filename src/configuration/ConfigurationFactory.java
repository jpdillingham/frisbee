package configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
* The {@code ConfigurationFactory} class instantiates all the configuration items needed for startup of a frisbee service instance
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
		NodeList nodeList = node.getChildNodes();
		LinkedList<Node> nodes = new LinkedList<Node>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node innerNode = nodeList.item(i);
			innerNode.normalize();
			if(innerNode.getNodeName().trim().toLowerCase().equals(matchItem)) {
				nodes.add(innerNode);
			}
		}
		
		return nodes;
		
	}
	
	/**
	 * Method used to extract the configuration parameters needed to instantiate the various classes needed by the frisbee service
	 * 
	 * @param type the argument type
	 * @param value the parent node to explore
	 * 
	 * @return the configuration object containing the various parameters needed for instantiation
	 * @throws LoggedException when retrieving configuration failed
	 */
	private Object nodeToObject(String type,Node value) throws LoggedException{
		if (value == null)
			return null;
		
		try {
			
	
			String argValue = null;
			
			if(type != null){
				argValue = value.getNodeValue();
			}
			
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
					
					List<ConfigurationObject> configurationObjects = new LinkedList<ConfigurationObject>();

					//Go through all the class members
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node innerNode = nodeList.item(i);
						NamedNodeMap innerNodeAttributes = innerNode.getAttributes();
						ConfigurationObject cobj = new ConfigurationObject(innerNode.getNodeName());
						
						//grab the configuration data needed for instantiation of the class
						for(int j = 0; j < innerNodeAttributes.getLength(); j++) {
							Object arg = null;
							String nodeAttribute = innerNodeAttributes.item(j).getNodeName().trim().toLowerCase();
							String nodeAttributeValue = innerNodeAttributes.item(j).getNodeValue();	
							
							if(nodeAttribute.equals("class")) {
								
								arg = nodeToObject(nodeAttributeValue,innerNode);
								cobj.setMember(innerNode.getNodeName().trim().toLowerCase(), arg);
								
							}
							else{
								cobj.setConfigurationItem(nodeAttribute,innerNodeAttributes.item(j).getNodeValue().trim().toLowerCase());
							}

						}
						
						configurationObjects.add(cobj);
					}
					
					return configurationObjects;
			}
					
		} catch (Exception e) {
			
			throw new LoggedException();
		}
		
		return null;
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
			throw new LoggedException();
		}
		
		return false;
		
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

			if(frisbeeNodes.size() != 1) {
				throw new LoggedException();
			}
			
			Node frisbeeNode = frisbeeNodes.get(0);
			
			List<Node> loggerNodes = this.browseChildren(frisbeeNode, "logger");

			for(Node l : loggerNodes) {
				this.nodeToObject(null, l);
			}
			
			List<Node> connectorNodes = this.browseChildren(frisbeeNode, "connector");
			
			for(Node c : connectorNodes) {
				this.nodeToObject(null, c);

			}
			
			
			List<Node> messageNodes = this.browseChildren(frisbeeNode, "message");
		
			for(Node m : messageNodes) {
				this.nodeToObject(null, m);
			}
			
			
		} catch(LoggedException e) {
			throw e;
		} catch(Exception e) {
			throw new LoggedException();
			
		}
		
		
		return false;
	}

}
