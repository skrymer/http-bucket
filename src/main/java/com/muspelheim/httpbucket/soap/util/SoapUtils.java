package com.muspelheim.httpbucket.soap.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.w3c.dom.Document;

import com.mycila.xmltool.XMLDoc;


/**
 * @author Sonni Nielsen - nielses
 */
public class SoapUtils {
	
	/**
	 * @return
	 * @throws Exception
	 */
	public SoapMessage createEmptySoapMessage() {
		SaajSoapMessageFactory messageFactory = null;
    try {
      messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
    } 
    catch (Exception e) {
      throw new RuntimeException(e);
    }    
		SaajSoapMessage message = messageFactory.createWebServiceMessage();

		return message;
	}
  
	/**
	 * @param payload
   * @return - a new SaajSoapMessage with the given xml payload injected into the SoapBody
   * @throws Exception
  */
	public SoapMessage createSoapMessage(String payload) {
		//Create document that contains the payload
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();  
		builderFactory.setNamespaceAware(true);  
		InputStream stream  = new ByteArrayInputStream(payload.getBytes());  
		
		SOAPMessage message = null;
		MessageFactory messageFactory = null;
		SaajSoapMessageFactory saajMessageFactory = null;
		try{
  		Document doc = builderFactory.newDocumentBuilder().parse(stream);   
  
  		//Create SOAPMessage
  		messageFactory = MessageFactory.newInstance();  
  		message                   = messageFactory.createMessage();  
  		SOAPBody soapBody         = message.getSOAPBody();  
  
  		soapBody.addDocument(doc);  

      saajMessageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());    
		}
		catch(Exception e){
		  throw new RuntimeException(e);
		}
		
		SaajSoapMessage saajMessage = saajMessageFactory.createWebServiceMessage();
		saajMessage.setSaajMessage(message);
	  
		return saajMessage;
	}
  
	/**
	 * 
	 * @param message
	 * @return - a string representation of the payload contained in the SoapMessage's Body
	 * @throws Exception
	 */
	public String getPayloadAsString(SoapMessage message){
		return getSourceAsString(message.getSoapBody().getPayloadSource());
	}
	
	/**
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public String getSourceAsString(Source source) {
	  StringWriter writer = new StringWriter();
    Result result = new StreamResult(writer);
    
    transform(source, result);
    
		return writer.toString();
	}
	
	/**
	 * @param message
	 * @param name
	 * @return
	 */
	public SoapHeaderElement getSoapHeader(SaajSoapMessage message, QName name) {
		return message.getSoapHeader().examineHeaderElements(name).next();
	}  
	
	 /**
   * 
   * @param header to add the XML to
   * @param xml to add
   * @throws Exception
   */
  public void addXmlToSoapHeader(SoapHeader header, Source xml) {
    transform(xml, header.getResult());
  }

  /**
  * 
  * @param header to add the XML to
  * @param xml to add
  * @throws Exception
  */
  public void setPayloadXml(SoapBody header, Source xml) {
  	transform(xml, header.getPayloadResult());
  }

  /**
   * @param soapHeader
   * @param qName
   * @return
   */
  public String getSoapHeaderElementText(SoapHeader soapHeader, String xpath) {
    String text;
    
    try{
      String header = getSourceAsString(soapHeader.getSource());  
      text = XMLDoc.from(header, true).getText(xpath);
    }
    catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    
    return text;
  } 

	public QName getPayloadQName(SoapMessage soapMessage) { 
  	DOMResult dom = new DOMResult();
    transform(soapMessage.getPayloadSource(), dom);
    
    String namespaceURI = dom.getNode().getFirstChild().getNamespaceURI();
    String localName = dom.getNode().getFirstChild().getLocalName();
		
		return new QName(namespaceURI, localName);
	}
	
//----------------------
// private methods
//---------------------
	
	private void transform(Source source, Result result){
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
