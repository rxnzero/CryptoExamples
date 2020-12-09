package com.dhlee.example;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLSample {

	public static void main(String[] args) {
		String xmlString = ""
				+ "<Envelope>"
				+ "<HEADER>"
				+ "<apcode>apcode</apcode>"
				+ "<code>code</code>"
				+ "<name>code</name>"
				+ "</HEADER>"
				+ "</Envelope>";
		testAddOrReplaceNode(xmlString, "code", "code12345");
	}
	
	
	public static void testAddOrReplaceNode(String xmlString, String tagName, String tagValue) {
		DocumentBuilder builder = null;
		
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        ByteArrayInputStream bis = new ByteArrayInputStream(xmlString.getBytes());
	        Document doc = builder.parse(bis);
	        Element root = doc.getDocumentElement();
        	
            NodeList lst = root.getChildNodes();
        	Node n = null;
			String nodeName = "";
			for(int i=0;i<lst.getLength();i++) {
				n = lst.item(i);
				nodeName = n.getNodeName();
                if( "HEADER".equals(nodeName) ) {
                	Element findNode = getDirectChild((Element)n, tagName);
                	Element el = doc.createElement(tagName);
                	
                	if(findNode != null) {
                		el.appendChild(doc.createTextNode("REP"+tagValue));
                		n.removeChild(findNode);
                	}
                	else {
                		el.appendChild(doc.createTextNode("NEW"+tagValue));	
                	}
                	n.appendChild(el);
				}
			}
			System.out.println(getStringFromDocument(doc));
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
	}
	
	public static Element getDirectChild(Element parent, String name) {
	    for(Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	        if(child instanceof Element && name.equals(child.getNodeName())) return (Element) child;
	    }
	    return null;
	}
	
	public static String getStringFromDocument(Document doc) {
	    try {
	       DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer = tf.newTransformer();
	       transformer.transform(domSource, result);
	       return writer.toString();
	    } catch(TransformerException ex) {
	       ex.printStackTrace();
	       return null;
	    }
	} 

}
