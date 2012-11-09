package com.muspelheim.util.matcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class XMLMatcher {
  static {
    XMLUnit.setIgnoreWhitespace(true);
  }
  
  public static Matcher<String> isSimilarTo(final String expectedXML){
    return new TypeSafeMatcher<String>() {
      private DetailedDiff diff;
      private Exception thrownException;
      
      public void describeTo(Description desc) {
        desc.appendText(expectedXML);
      }

      @Override
      protected void describeMismatchSafely(String actualXML, Description mismatchDescription) {
        try {
          appendMismatchDescription(actualXML, diff, thrownException, mismatchDescription);
        }  
        catch (Exception e) {
          e.printStackTrace();
        } 
      }

      @Override
      public boolean matchesSafely(String actualXML) {
        diff = null;
        
        try {
          diff = new DetailedDiff(new Diff(expectedXML, actualXML));
        } 
        catch (Exception exception) {
          thrownException = exception;
          return false;
        } 

        return diff.similar();
      }  
    };
  }
  
  public static Matcher<File> isSimilarTo(final File expectedXMLFile){
    return new TypeSafeMatcher<File>() {
      private DetailedDiff diff;
      private Exception thrownException;

      public void describeTo(Description desc) {
        try {
          desc.appendText(getFileAsString(expectedXMLFile));
        } 
        catch (Exception e) {
          e.printStackTrace();
        } 
      }
      
      @Override
      protected void describeMismatchSafely(File actualXMLFile, Description mismatchDescription) {
        try {
          appendMismatchDescription(getFileAsString(actualXMLFile), diff, thrownException, mismatchDescription);
        }  
        catch (Exception e) {
          e.printStackTrace();
        } 
      }

      @Override
      public boolean matchesSafely(File actualXML) {
        diff = null;
        
        try {
          diff = new DetailedDiff(new Diff(new FileReader(expectedXMLFile), new FileReader(actualXML)));
        } 
        catch (Exception exception) {
          thrownException = exception;
          return false;
        } 
        
        return diff.similar();
      }      
    };
  }  
  
//--------------------
// Helper methods
//--------------------

  private static void appendMismatchDescription(String actualXML, DetailedDiff diff, Exception thrownException, Description mismatchDescription) {
    mismatchDescription.appendText(actualXML);
    
    if(thrownException != null){
      appendException(thrownException, mismatchDescription);
    }
    
    appendDifferences(diff, mismatchDescription);          
  }

  private static void appendDifferences(DetailedDiff diff, Description mismatchDescription) {
    if(diff != null){
      mismatchDescription.appendText("\nDiff: " + diff.toString());
      
      List<Difference> differences = diff.getAllDifferences();
      for (Difference difference : differences) {
        mismatchDescription.appendText("\nDiff: " + difference.getDescription());            
      }  
    }        
  }
  
  private static void appendException(Exception thrownException, Description mismatchDescription) {
    mismatchDescription.appendText("\nException: " + thrownException.getMessage());
  }
  
  private static String getFileAsString(File file) throws FileNotFoundException, IOException{
    Scanner scanner = new Scanner(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    
    while(scanner.hasNext())
       stringBuilder.append(scanner.nextLine());
  
    return stringBuilder.toString();
  }  
}