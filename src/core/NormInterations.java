/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author guydu
 */
public class NormInterations {
    private static String homeURL = "http://localhost:8084/Assignment1/";

    private static WebDriver driver;
    private static JavascriptExecutor jse;
    public static String locationOfDriver = System.getProperty("user.dir") + "/lib/chromedriver.exe"; //file path to driver file relative to this project
    private static volatile boolean isSetup = false;
    
    private static synchronized void setup() {
        if(isSetup)return;
        System.setProperty("webdriver.chrome.driver", locationOfDriver);
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        jse = (JavascriptExecutor) driver;
        isSetup = true;
    }
    
    private static synchronized void closeDriver(){
        driver.close();
        isSetup=false;
    }
    
    public static void main(String[] args) {
        login();
    }
    
    private static WebElement findSignInButton(){
        return driver.findElement(By.xpath("//a[@href='signin.jsp']"));
    }
    
    
    public static void login(){
        setup();
        String username = "exampleUser";
        String password = "password";
        driver.get(homeURL);
        WebElement signinButton = findSignInButton();
        signinButton.click();
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='id']"));
        usernameField.sendKeys(username);
        WebElement pwField = driver.findElement(By.xpath("//input[@name='pw']"));
        pwField.sendKeys(password);
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();
        
        WebElement viewProfButton = driver.findElement(By.id("firstHor"));
        if(viewProfButton.getText().equals("View Profile")){
            System.out.println("successfully logged in as " + username);
        }
    }
    
    
}
