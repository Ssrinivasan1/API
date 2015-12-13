/**
 * @author subha srinivasan
 *
 */

package com.qa.automation.pages;


import com.qa.automation.toolbox.AutomationSettings;
import com.qa.automation.toolbox.GeneralMethods;
import com.qa.automation.toolbox.AutoTestCase;
import com.qa.automation.ui.GenericPage;





import com.thoughtworks.selenium.Wait;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert.*;

public class AccountPage extends GenericPage {

	 
	public AccountPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	WebDriverWait wait= new WebDriverWait(this.driver,5);
	//Navigation click on product category and click on iphones and select the link
	
	// Web Elements
    //@FindBy(how = How.CSS, using = "input[class^='main-login-username']") // MGB 6/3/2014
	String text="Continue";
	@FindBy(how = How.CSS, using = "input[type='text'][name='log']")
    public WebElement userName;
	
	@FindBy(how = How.CSS, using = "input[type='password'][name='pwd']")
    public WebElement password;

	@FindBy(how=How.CSS,using="input[type='submit'][value*='Login']") 
	public WebElement login;
	
	@FindBy(how=How.CSS,using="a[href*='your-account'][title='My Account']") 
	public WebElement account;
	@FindBy(how=How.CSS,using="a[href*='edit_profile']") 
	public WebElement accountDeatils;
	@FindBy(how=How.CSS,using="input[title='billinglastname'][placeholder='Last Name']") 
	public WebElement lastName;
	@FindBy(how=How.CSS,using="input[title='billingphone'][placeholder='Phone']") 
	public WebElement phone;
	//@FindBy(how=How.CSS,using="a[href*='logout']") 
	@FindBy(how=How.XPATH,using="//a[@title='Logout']") 
	public WebElement logout;
	@FindBy(how=How.CSS,using="input[type='submit'][value*='Save Profile']") 
	public WebElement saveProfile;
	@FindBy(how=How.CSS,using="div[id='header_cart'] a[href*='checkout'] em[class='count']")
  
	//@FindBy(how=How.CSS,using="a.cart_icon")
	public WebElement checkOutItem;
	@FindBy(how=How.CSS,using="a[href*='checkout'][title='Checkout']") 
	public WebElement checkOut1;
	
    
 
     
	
     // page use cases    
   
    public void setUserName(String user) throws IOException, InterruptedException {
        super.setTextField(userName, "User Name", user);     
    }
    public boolean login() throws IOException, InterruptedException{         	    
        try{
        	System.out.println("Log in to My Account");
        	this.userName.clear();
            this.setUserName("testuser1256");
        	this.password.clear();
        	this.password.sendKeys("jq6mBdOp829g");
            this.login.click();
          
        	
    		
        	return true;
        } catch (Exception e) { System.out.println(e.getMessage());return false; }
    }
    
       public boolean updateAccountDetails() throws IOException,InterruptedException{
    	   try{
    		  System.out.println("Set the field");
    		   this.setTextField(this.lastName,"lastname","Srinivasan");
    		   Thread.sleep(1000);
    		   this.setTextField(this.phone,"phone","5173037890");
    		   this.saveProfile.click();
    		   
    		   return true;
    		   
    	   }
    	   
    	   
    	   
    	   catch (Exception e) {System.out.println(e.getMessage());return false;}
       }
       
       public boolean NavigateAccountDetails() throws IOException,InterruptedException{
    	   try{
    		   System.out.println("Click the Account Details");
    		   this.accountDeatils.click();
    		   return true;
    		   
    	   }
    	   
    	   catch (Exception e) {System.out.println(e.getMessage());return false;}
       }
       public boolean logout() throws IOException,InterruptedException{
    	   try{
    		   System.out.println("Logging out");
    		   this.logout.click();
    		  
    		   return true;
    		   
    	   }
    	   
    	   catch (Exception e) {System.out.println(e.getMessage());return false;}
       }
     public boolean removeCart() throws IOException,InterruptedException{
    	 try{
    		 System.out.println("Remove cart by logging to your account");
    		 this.account.click();
    		 this.login();
    		 GeneralMethods.delay(5);
    		
    		 Thread.sleep(10000);
    		 this.checkOut1.click();
    		
    	
    		
    		// List<WebElement>selected_Items =driver.findElements(By.cssSelector("input[name*='submit'][value='Remove']")); 
    		 Thread.sleep(10000);
    		 List<WebElement>selected_Items1 =driver.findElements(By.xpath("(//input[@value='Remove'])")); 
    		 System.out.println(" the size is "+selected_Items1.size());
    		  for (int i=0; i<selected_Items1.size();i++){
    		    driver.findElement(By.xpath("(//input[@value='Remove'])")).click();
    		    Thread.sleep(5000);
    		  }
    		  return true;
    	 }
    	 catch (Exception e){ System.out.println(e.getMessage());
    	                      return false;}
    	 
     }
       
    public void closeApplication() {       	
    	this.driver.close();    	
    }
    
    
   


    
    	 
    
 }
