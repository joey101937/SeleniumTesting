/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Joseph DeMeis
 */
public class seleniumSandbox {
    
    private static WebDriver driver;
    private static JavascriptExecutor jse;
    
    private static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Joseph DeMeis\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        jse = (JavascriptExecutor)driver;
    }
    
    /**
     * goes to google, searches for "test keys" then scrolls down
     */
    private static void example1() {
        setup();
        driver.get("http://google.com");
        driver.findElement(By.name("q")).click();
        driver.findElement(By.name("q")).sendKeys("test keys");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        int scrollLocation = 0;
        //scroll over time
        for (int i = 0; i < 10; i++) {
            jse.executeScript("scroll(0," + (scrollLocation += 100) + ")");
            System.out.println("scrolling " + scrollLocation);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(seleniumSandbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        driver.quit();
        System.out.println("Success");
    }

    /**
     * goes to google home, then goes to google images via xpath
     */
    public static void example2(){
        setup();
        driver.get("http://google.com");
        driver.findElement(By.xpath("//a[contains(text(),'Images')]")).click();
    }
    
    public static void main(String[] args) {
        example2(); 
    }
}