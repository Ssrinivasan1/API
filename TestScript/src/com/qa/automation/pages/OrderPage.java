/**
 * @author subha srinivasan
 *
 */

package com.qa.automation.pages;


import com.qa.automation.toolbox.AutomationSettings;
import com.qa.automation.toolbox.GeneralMethods;
import com.qa.automation.toolbox.AutoTestCase;
import com.qa.automation.ui.GenericPage;



import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert.*;

public class OrderPage extends GenericPage {

	 
	public OrderPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	//Navigation click on product category and click on iphones and select the link
	
	// Web Elements
    //@FindBy(how = How.CSS, using = "input[class^='main-login-username']") // MGB 6/3/2014
	String text="Continue";
	@FindBy(how = How.CSS, using = "a[href*='product-category']")
    public WebElement productLink;
	
	@FindBy(how = How.CSS, using = "a[href*='apple-iphone-4s-16gb-sim-free-black']")
    public WebElement productPhone;
	
	@FindBy(how=How.CSS,using="input[type='text'][value='Search Products']")
	public WebElement search;
	@FindBy(how=How.CSS,using="span input[type='submit'][value*='Add To Cart']") 
	public WebElement addCart;
	@FindBy(how=How.CSS,using="a[href*='checkout'][class*='go_to_checkout']")
	public WebElement checkout;
	@FindBy(how=How.CSS,using="a[href*='#'][class*='step2']")
	public WebElement continue1;
	@FindBy(how=How.XPATH,using=("//*[contains(@span,'"+"Continue"+"')]"))
	public WebElement continue2;
	@FindBy(how=How.CSS,using="a[href*='#'][class*='step2']span")
	public WebElement continue3;
	@FindBy(how=How.CSS,using="input[type='submit'][value*='Calculate']") 
	public WebElement calculate;
	@FindBy(how=How.XPATH,using="//*[@id='checkout_total']/span")
	public WebElement totalPrice;
	@FindBy(how=How.CSS,using="span[class='pricedisplay']") 
	public WebElement totalPrice1;
     
    public boolean launchApplication() throws InterruptedException {  
    	long elapsedTimeB = Calendar.getInstance().getTimeInMillis();
    	int browseDelay = AutomationSettings.getDelay("Login");
    	this.driver.manage().timeouts().implicitlyWait(browseDelay, TimeUnit.SECONDS);
    	this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    	boolean navigated = false;
	    try {
    		String url = GeneralMethods.getDeployment(); 
    		System.out.println(" the value of url"+url);
    		this.driver.navigate().to(url);
    		GeneralMethods.delay(1000);
    		
    	
    		
    	} catch (Exception e) {
    		System.out.println("Exception thrown by launchApplication: " +e.getMessage());
    	}
    	
	   
		
    	return navigated;
    }
    public boolean searchProduct(String product) throws IOException, InterruptedException{         	    
        try{
        	
        	this.search.clear();
        	this.search.sendKeys(product);
        	this.search.sendKeys(Keys.RETURN);
            this.productPhone.click();
           GeneralMethods.delay(20);
            this.addCart.click();
            GeneralMethods.delay(1000);
           
            this.checkout.click();
            GeneralMethods.delay(10000);
        	
          driver.findElement(By.xpath("//div[@id='checkout_page_container']/div/a/span")).click();
          
    		
        	return true;
        } catch (Exception e) { return false; }
    }
    
        public String getPurchasePrice(WebElement element1) throws IOException{
        	try{
        		System.out.println("Inside option");
        		Thread.sleep(5000);
        		//Select listSelect = new Select(driver.findElement(By.xpath("(//select[@id='current_country'])")));
        		//Select listSelect = new Select(driver.findElement(By.xpath("//select[@id='current_country']")));
        		//List<WebElement>selected_Items= listSelect.getOptions();
        		driver.findElement(By.xpath("//select[@id='current_country']/option[@value='US']")).click();
        		
                //listSelect.selectByVisibleText("USA");
                //listSelect.selectByValue("US");
               calculate.click();
               Thread.sleep(5000);
               String totalPriceint= element1.getText().trim();
             
        		return totalPriceint;
        	}
        	catch (Exception e) { System.out.println(e.getMessage());return null; }
        	
     }
    
    public void closeApplication() {       	
    	this.driver.close();    	
    }
    
    
   


    
    	 
    
 }
