xmlParser = new XmlParser().parseText(requestPayload)

firstData = xmlParser.data[0]	

def responseToReturnText = firstData.responseToReturn.text()

log.info "Response to return $responseToReturnText"

switch(responseToReturnText){
	case "response.xml": 
		responsePayload = "<response>response</response>" 
		break
	
	case "response2.xml":
		responsePayload = "<response>response2</response>"
		break
}			
