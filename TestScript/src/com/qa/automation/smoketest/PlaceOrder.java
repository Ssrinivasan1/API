package com.qa.automation.smoketest;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import org.testng.Assert;

import com.qa.automation.smoketest.*;
import com.qa.automation.toolbox.*;
import com.qa.automation.ui.*;
import com.qa.automation.pages.*;
import com.thoughtworks.selenium.Wait;


public class PlaceOrder extends AutoTestCase {

	@Test
	public static void test() throws Exception {
		AutoTestCase.testName = "PlaceOrder";	
		AutoTestCase.tester = "Subha Srinivasan";

		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		OrderPage lp = new OrderPage(driver, "orderpage");
		AccountPage ap = new AccountPage(driver, "accountpage");
		
		
		
		Actions actions = new Actions(driver);
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		AutoTestCase.testData = "site="+deployment+"  browser="+AutomationSettings.getTestDataItem("ChromeVersion");	
		lp.launchApplication();
	    
	
		 //  System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		   //Validate the purchase price
		   
		    currStepResult=lp.searchProduct("Apple iphone 4s") ? "Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "Check the product link is displayed ", currStepResult);
			String totalpurchaseprice=lp.getPurchasePrice( lp.totalPrice);
			System.out.println(" the total purchase Price"+totalpurchaseprice);
			Assert.assertEquals("$282.00", totalpurchaseprice);
			Artifact.VerifyWriteToArtifactS(artifact, "Validate the purchase price ", currStepResult);
			
			
				
	
		
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}

// TODO: activity board
// TODO:  Patient tracker screen
