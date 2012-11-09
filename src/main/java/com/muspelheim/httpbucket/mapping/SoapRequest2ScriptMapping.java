package com.muspelheim.httpbucket.mapping;

import javax.xml.namespace.QName;

public class SoapRequest2ScriptMapping {
  private QName qName;
	private String scriptName;
	
	public SoapRequest2ScriptMapping(QName qName, String scriptName) {
		this.qName 			= qName;
		this.scriptName = scriptName;
	}

	public String getScriptName() {
	  return scriptName;
  }
	
	public QName getQName() {
	  return this.qName;
  }
	
	@Override
	public boolean equals(Object mappingToCompare) {
		if(! (mappingToCompare instanceof SoapRequest2ScriptMapping))
			return false;
				
		if(qNamesAreNotEqual((SoapRequest2ScriptMapping)mappingToCompare))
			return false;

		if(scriptsAreNotEqual((SoapRequest2ScriptMapping)mappingToCompare))
			return false;

	  return true;
	}
	
	@Override
	public String toString() {
	  StringBuilder description = new StringBuilder();
	  
	  description.append("Qname: ").append(qName.toString());
	  description.append(" -> Script: ").append(scriptName);
	  
		return description.toString();
	}

//------------------------
// private methods
//------------------------
	
	private Boolean scriptsAreNotEqual(SoapRequest2ScriptMapping mappingToCompare) {
		String scriptToCompare = mappingToCompare.getScriptName();
		
		if(this.scriptName.equals(scriptToCompare))
			return false;
		
		return true;
  }

	private Boolean qNamesAreNotEqual(SoapRequest2ScriptMapping mappingToCompare) {
	  QName qNameToCompare = mappingToCompare.getQName();
		
	  if(this.qName.getNamespaceURI().equals(qNameToCompare.getNamespaceURI()))
	  	return false;

	  if(this.qName.getLocalPart().equals(qNameToCompare.getLocalPart()))
	  	return false;

	  return true;
  }
}
