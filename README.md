## Mission statement: make it easier to create web-service stubs 

Http-bucket is based on a simple request 2 script mapping which lets you specify scripts to handle specific requests. 
 
The following illustration shows the main concepts in http-bucket

![alt text](https://raw.github.com/wiki/skrymer/http-bucket/images/concept.png "Concept")

http-bucket can run as a standalone or maven-plugin

### http-bucket as a maven-plugin 

For this example wi'll create a soap-ws-stub with a script that get's invoked for any payloads with a QName off: 
```xml
<doc xmlns:x="http://example.com/ns/foo"> 
```

First step is to create a mapping file. 

The format for the mapping file is CSV. It's possible to create your own mapper, if for some reason you don't like CSV - see section on create your own mapper

Here is our csv file

```csv
'http://example.com/ns/foo','doc','script.groovy'
```

The file contains three entries; the first two parts defines the qname and the third the name of the script file - for now only groovy scripts are supported, but look at the "create you own response generator" section for info on how to create your own way of returning responses

The next step is to create a script that are to return a response.

```groovy
xmlParser = new XmlParser().parseText(requestPayload) [1]

firstData = xmlParser.data[0]	

def responseToReturnText = firstData.responseToReturn.text()

log.info "Response to return $responseToReturnText" 

switch(responseToReturnText){
	case "response.xml": 
		responsePayload = "<response>response</response>" [2] 
		break
	
	case "response2.xml":
		responsePayload = "<response>response2</response>" [2]
		break
}
```
1. A XmlParser is used for parsing the incomming request which are avaliable in the requestPayload variable
2. Dependent on the data in the request a response is stored in the responsePayload variable which are then returned from the stub

The last step is to sconfigure the plugin in your pom.xml:

```xml
...
  <plugins>
    ...	
    <plugin>
      <groupId>com.googlecode.http-bucket</groupId>
      <artifactId>http-bucket</artifactId>
      <version>0.1-SNAPSHOT</version>
      <executions>
	<execution>
	  <id>start-http-bucket</id>
	  <phase>pre-integration-test</phase> [1]
	  <goals>
	    <goal>start</goal> 
	  </goals>
	  <configuration>
	    <keepAlive>true</keepAlive>
	    <request2scriptMappingFile>path/to/request2scriptmappings.csv</request2scriptMappingFile> [2]
	    <resourceDir>path/to/resourceDir</resourceDir> [3]
	  </configuration>
	</execution>
	<execution>
	  <id>stop-http-bucket</id>
	  <phase>post-integration-test</phase> [4]
	  <goals>
	    <goal>stop</goal> 
	  </goals>
	</execution>
      </executions>
    </plugin>
    ...
  <plugins>
...	
```
1. Define in which phase you want the plugin to start - we are using the pre-integration-test to start the bucket before our integration tests are executed
2. Tell http-bucket were to find the mapping file
3. Tell http-bucket were to find resource directory that should contain all scripts and any xml/json fragments
4. Define in which phase you want the plugin to stop - we are using the post-integration-test to stop the bucket after our integration tests have been executed

TODO: script vaiables

Script variables are varibles that are passed to the script when it get's executed.

Log: can be used by the script to log statements 

requestPayload: contains the incomming request payload as a String

responsePayload: when a response has been generated it should be put in the responsePayload

TODO: create your own mapper

TODO: create your own ResponseGenerator


