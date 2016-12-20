package com.kuharski.parallel;

import java.net.MalformedURLException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestFullAjaxLoad {
	
	
  @Test(threadPoolSize = 1, invocationCount = 1)     // (1) <-- CHANGE TO THE NUMBER PARALLEL EXECUTIONS YOU WANT.  e.g threadPoolSize = 3, invocationCount = 3  >> RUNS 3 times in PARALLEL
  public void googleTest() throws MalformedURLException {
      WebDriver driver = new HtmlUnitDriver(true);//FirefoxDriver();
	  
	  // NOTE: change to you own host 
//	  WebDriver driver = new RemoteWebDriver(
//              new URL("http://192.168.99.100:4444/wd/hub"),   // (2) <-- CHANGE TO OUT OWN HOST new URL("http://<myHost>:4444/wd/hub")
//			  DesiredCapabilities.firefox());


      // And now use this to visit Google
      driver.get("https://www.leg.state.mn.us/calendarday?jday=04/04/2016");

/*      // Wait for the page to load, timeout after 10 seconds
      WebElement element = (new WebDriverWait(driver, 10))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

      // Enter something to search for
      element.sendKeys("Cheese!");

      // Now submit the form. WebDriver will find the form for us from the element
      element.submit();
      
      // Google's search is rendered dynamically with JavaScript.
      // Wait for the page to load, timeout after 10 seconds
      (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
              return d.getTitle().toLowerCase().startsWith("cheese!");
          }
      });*/

      
      boolean b = waitForJSandJQueryToLoad(driver);
      assert b == true;
      
      // Should see: "cheese! - Google Search"
      System.out.println("Page title is: " + driver.getTitle());
      String p = driver.getPageSource();
      System.out.println("Page title is: " + p);
      
     // WebElement results = driver.findElement(By.id("resultStats"));
      
      assert p != null;

      //Close the browser
      driver.quit();
  }
  
  
  public boolean waitForJSandJQueryToLoad(WebDriver driver) {

	    WebDriverWait wait = new WebDriverWait(driver, 30);

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)driver).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
}
