package com.muspelheim.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Sonni Nielsen - nielses
 *
 */
public class RestClientTestUtils {
  private static final String TEST_XML_PATH = "testXML/rest/";

  public static File getTestXML(String xmlFileName) throws IOException{
    return new ClassPathResource(TEST_XML_PATH + xmlFileName).getFile();
  }
  
  public static String getTestXmlAsString(String xmlFileName) throws FileNotFoundException, IOException {
    Scanner scanner = new Scanner(getTestXML(xmlFileName));
    StringBuilder stringBuilder = new StringBuilder();
        
    while(scanner.hasNext())
       stringBuilder.append(scanner.nextLine());
      
    return stringBuilder.toString();
  }
}
