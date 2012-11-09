package com.muspelheim.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.javacrumbs.smock.common.client.ParametrizableRequestMatcher;
import net.javacrumbs.smock.common.client.ParametrizableResponseCreator;
import net.javacrumbs.smock.springws.client.AbstractSmockClientTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.IHookCallBack;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * @author Sonni Nielsen - nielses
 * Based on @see AbstractTestNGSpringContextTests
 *
 */
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-ws-servlet.xml")
public abstract class AbstractSpringContextSoapClientTest extends AbstractSmockClientTest  {
  protected final Log logger = LogFactory.getLog(getClass());

  protected ApplicationContext applicationContext;

  private final TestContextManager testContextManager;

  private Throwable testException;

  public AbstractSpringContextSoapClientTest() {
    this.testContextManager = new TestContextManager(getClass());
  } 
  
  protected abstract String getTestXmlDirectoryPath();
  
  public ParametrizableResponseCreator withXML(String xmlFile){
    return withMessage(getTestXmlDirectoryPath() + xmlFile);
  }
	  
  public ParametrizableRequestMatcher xml(String xmlFile){
    return message(getTestXmlDirectoryPath() + xmlFile);
  }
  
  public  File getTestXML(String xmlFileName) throws IOException{
    return new ClassPathResource(getTestXmlDirectoryPath() + xmlFileName).getFile();
  }
  
//-----------------------------------------------------
// Methods taken from AbstractTestNGSpringContextTests
//-----------------------------------------------------
  
  public final void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @BeforeClass(alwaysRun = true)
  protected void springTestContextBeforeTestClass() throws Exception {
    this.testContextManager.beforeTestClass();
  }

  @BeforeClass(alwaysRun = true, dependsOnMethods = "springTestContextBeforeTestClass")
  protected void springTestContextPrepareTestInstance() throws Exception {
    this.testContextManager.prepareTestInstance(this);
  }

  @BeforeMethod(alwaysRun = true)
  protected void springTestContextBeforeTestMethod(Method testMethod) throws Exception {
    this.testContextManager.beforeTestMethod(this, testMethod);
  }

  public void run(IHookCallBack callBack, ITestResult testResult) {
    callBack.runTestMethod(testResult);

    Throwable testResultException = testResult.getThrowable();
    if (testResultException instanceof InvocationTargetException) {
      testResultException = ((InvocationTargetException) testResultException).getCause();
    }
    this.testException = testResultException;
  }

  @AfterMethod(alwaysRun = true)
  protected void springTestContextAfterTestMethod(Method testMethod) throws Exception {
    try {
      this.testContextManager.afterTestMethod(this, testMethod, this.testException);
    }
    finally {
      this.testException = null;
    }
  }

  @AfterClass(alwaysRun = true)
  protected void springTestContextAfterTestClass() throws Exception {
    this.testContextManager.afterTestClass();
  }
}
