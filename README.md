### Mission statement: make it easier to create web-service stubs 

Http-bucket is based on a simple request 2 script mapping which lets you specify scripts to handle specific requests. 
 
The following illustration shows the main concepts in http-bucket

[[/images/concept.png|width=1000px]]

http-bucket can run as a standalone or maven-plugin

## http-bucket as a maven-plugin 

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


