

// Generated by Selenium IDE
package main;
import core.SeleniumSandbox;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.util.HashMap;
import java.util.Map;

public class Firsttest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", SeleniumSandbox.locationOfDriver);
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void firsttest() {
      System.out.println("1");
    driver.get("https://www.google.com/");
      System.out.println("2");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.name("q")).click();
    driver.findElement(By.name("q")).sendKeys("test-content");
    driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
  }
}
