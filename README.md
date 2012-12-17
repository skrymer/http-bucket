## Mission statement: make it easier to create web-service stubs 

Http-bucket is based on a simple request 2 script mapping which lets you specify scripts to handle specific requests. 
 
The following illustration shows the main concepts in http-bucket

[[/images/concept.png|width=1000px]]

http-bucket can run as a standalone or maven-plugin

### http-bucket as a maven-plugin 

For this example wi'll create a soap-ws-stub with a script that get's invoked for any payloads with a QName off <doc xmlns:x="http://example.com/ns/foo"> 

First step is to create a mapping file. 

The format for the mapping file is CSV - it is possible to create your own mapper if, for some reason, you don't like CSV, see section on create your own mapper

Here is our csv file

```csv
'http://example.com/ns/foo','doc','script.groovy'
```

The file contains three entries; the first two parts are the qname followed by the name of the script file - for now, only groovy scripts are supported, but look at the create you own response generator if you want to add a new way of returning responses

The next step is to create the script that are to return a given response

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

Add the following to your pom.xml

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
          <phase>compile</phase>
          <goals>
            <goal>start</goal> [1]
          </goals>
          <configuration>
            <keepAlive>true</keepAlive>
            <request2scriptMappingFile>path/to/request2scriptmappings.csv</request2scriptMappingFile> [2]
            <resourceDir>path/to/resourceDir</resourceDir> [3]
          </configuration>
        </execution>
      </executions>
    </plugin>
    ...
  <plugins>
...	
```

Now configure the plugin:

* 



TODO: create your own mapper

TODO: create your own ResponseGenerator


