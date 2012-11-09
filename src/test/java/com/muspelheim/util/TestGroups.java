package com.muspelheim.util;

/**
 * Contains TestNG group names 
 * 
 * @author Sonni Nielsen - nielses
 *
 */
public interface TestGroups {
      
  /**
   * Applied to tests where the system under test is tested in complete isolation
   */
  static final String UNIT_TEST = "unittest";

  /**
   * Applied to integration tests 
   */
  static final String INTEGRATION_TEST = "integrationtest";

  /**
   * Applied to tests that are not working
   */
  static final String NOT_WORKING = "notworking";
}
