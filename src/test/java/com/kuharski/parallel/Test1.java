package com.kuharski.parallel;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Test1 {
	
	
  @Test(threadPoolSize = 1, invocationCount = 1)     // (1) <-- CHANGE TO THE NUMBER PARALLEL EXECUTIONS YOU WANT.  e.g threadPoolSize = 3, invocationCount = 3  >> RUNS 3 times in PARALLEL
  public void googleTest() throws MalformedURLException {
      //WebDriver driver = new FirefoxDriver();
	  
	  // NOTE: change to you own host 
	  WebDriver driver = new RemoteWebDriver(
              new URL("http://192.168.99.100:4444/wd/hub"),   // (2) <-- CHANGE TO OUT OWN HOST new URL("http://<myHost>:4444/wd/hub")
              DesiredCapabilities.chrome());

      driver.get("http://www.google.com");

      // And now use this to visit Google
      driver.get("http://www.google.com");

      // Wait for the page to load, timeout after 10 seconds
      WebElement element = (new WebDriverWait(driver, 10))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

      // Enter something to search for
      element.sendKeys("Cheese!");

      // Now submit the form. WebDriver will find the form for us from the element
      element.submit();

      // Check the title of the page
      System.out.println("Page title is: " + driver.getTitle());
      
      // Google's search is rendered dynamically with JavaScript.
      // Wait for the page to load, timeout after 10 seconds
      (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
              return d.getTitle().toLowerCase().startsWith("cheese!");
          }
      });

      // Should see: "cheese! - Google Search"
      System.out.println("Page title is: " + driver.getTitle());
      
      WebElement results = driver.findElement(By.id("resultStats"));
      
      assert results != null;

      //Close the browser
      driver.quit();
  }
}
