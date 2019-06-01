/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * tests Norms Seed Emporium with logged-in user options
 * @author guydu
 */
public class NormLoginTest {
    
    private String homeURL = "http://localhost:8084/Assignment1/";
    private WebDriver driver;
    private JavascriptExecutor jse;
    public String locationOfDriver = System.getProperty("user.dir") + "/lib/chromedriver.exe"; //file path to driver file relative to this project
    private volatile boolean isSetup = false;
    
    String username = "exampleUser";
    String password = "password";
    String name = "Joseph De Meis";
    String address = "1900 Example Eve., Charlotte,USA";
    String email = "example@example.com";
    
    @Before
    public synchronized void setup() {
        if (isSetup) {
            return;
        }
        System.setProperty("webdriver.chrome.driver", locationOfDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        jse = (JavascriptExecutor) driver;
        driver.get(homeURL);
        isSetup = true;
    }
    @After
    public synchronized void closeDriver() {
        driver.close();
        isSetup = false;
    }
  
    /*                  ELEMENT FINDERS                                    */
    //HOME PAGE
    private WebElement findSignInButton() {
        try {
            return driver.findElement(By.xpath("//a[@href='signin.jsp']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private WebElement findViewProfileButton() {
        try {
            return driver.findElement(By.xpath("//a[@href='profile.jsp']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    //LOGIN PAGE
    private WebElement findUsernameInput() {
        try {
            return driver.findElement(By.xpath("//input[@name='id']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private WebElement findPasswordInput() {
        try {
            return driver.findElement(By.xpath("//input[@name='pw']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private WebElement findLoginSubmitButton() {
        try {
            return driver.findElement(By.xpath("//input[@type='submit']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    //VIEW PROFILE PAGE
    /**
     * find the element who's text contains the username of the logged in user
     * in view prof screen
     *
     */
    private WebElement findVPUsername() {
        //return driver.findElement(By.cssSelector("h2"));
        try {
            return driver.findElement(By.xpath("//h2[contains(text(),'s Profile')]"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * find the element who's text contains the username of the logged in user
     * in view profile screen
     */
    private WebElement findVPInfo() {
        try {
           ArrayList<WebElement> Ps = (ArrayList)driver.findElements(By.cssSelector("p"));
           for(WebElement ele : Ps){
               if(ele.getText().toLowerCase().contains("name:")){
                   return ele;
               }
           }
           return null;
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    
    //starts at homepage and navigates to login page, then signs in and verifies
    //successfull login by checking if the view profile button is available
    @Test
    public void login() {
        driver.get(homeURL);
        WebElement signinButton = findSignInButton();
        if(signinButton==null){
            if(findViewProfileButton()!=null){
                fail("Unable to log-in: Sign-in button not found (view profile button was found) on " + driver.getCurrentUrl());
            }
            fail("Unable to log-in: Sign-in button not found on " + driver.getCurrentUrl());
        }
        signinButton.click();
        WebElement usernameField = findUsernameInput();
        if(usernameField == null){
            fail("Unable to log-in: username input field not found on " + driver.getCurrentUrl());
        }
        usernameField.sendKeys(username);
        WebElement pwField = findPasswordInput();
        if (pwField == null) {
            fail("Unable to log-in: password input field not found on " + driver.getCurrentUrl());
        }
        pwField.sendKeys(password);
        WebElement submitButton = findLoginSubmitButton();
        if (submitButton == null) {
            fail("Unable to log-in: submit button not found on " + driver.getCurrentUrl());
        }
        submitButton.click();
        WebElement viewProfButton = findViewProfileButton();
        if (viewProfButton==null) {
            fail("After sign-in submition: View Profile Button not found on " + driver.getCurrentUrl());
        }
    }
    
    @Test
    public void viewProfilePage(){
        login();
        WebElement vpb = findViewProfileButton();
        if(vpb == null){
            fail("Unable to view profile: View Profile Button not found on " +driver.getCurrentUrl());
        }
        vpb.click();
        WebElement subtitle = this.findVPUsername();
        if(subtitle==null){
            fail("Unable to find ViewProfile page's subtitle (with 'exampleUser's Profile' text) on " + driver.getCurrentUrl());
        }
        if(!subtitle.getText().contains(username)){
            fail("Username not found on view profile page subtitle: " + subtitle.getText() + " on " + driver.getCurrentUrl());
        }
        WebElement infoElement = this.findVPInfo();
        if(infoElement==null){
            fail("Unable to find profile info element on " + driver.getCurrentUrl());
        }
        String info = infoElement.getText();
        if(!info.toLowerCase().contains(name.toLowerCase())){
            fail("User's name {" + name + ") not found in pofile info text (" + infoElement.getText() +")");
        }
        if(!info.toLowerCase().contains(address.toLowerCase())){
            fail("User's address {" + address + ") not found in pofile info text (" + infoElement.getText() +")");
        }
        if(!info.toLowerCase().contains(email.toLowerCase())){
            fail("User's address {" + email + ") not found in pofile info text (" + infoElement.getText() +")");
        }
    }
}
