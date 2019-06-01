/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Tests Nroms Seed Emporium Website running on localhost without logging in
 * @author guydu
 */
public class NormNoLoginTest {

    private String homeURL = "http://localhost:8084/Assignment1/";
    private WebDriver driver;
    private JavascriptExecutor jse;
    public String locationOfDriver = System.getProperty("user.dir") + "/lib/chromedriver.exe"; //file path to driver file relative to this project
    private volatile boolean isSetup = false;
    
    @Before
    private synchronized void setup() {
        if (isSetup) {
            return;
        }
        System.setProperty("webdriver.chrome.driver", locationOfDriver);
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        jse = (JavascriptExecutor) driver;
        driver.get(homeURL);
        isSetup = true;
    }
    
    @After
    private synchronized void closeDriver() {
        driver.close();
        isSetup = false;
    }
    
    private void goToCatagories(){
        WebElement categoriesButton = driver.findElement(By.xpath("//a[@href='catagoriesController']"));
    }
    
}
