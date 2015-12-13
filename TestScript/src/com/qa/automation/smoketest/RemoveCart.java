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


public class RemoveCart extends AutoTestCase {

	@Test
	public static void test() throws Exception {
		AutoTestCase.testName = "Remove Cart";	
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
	    
	
		   //System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		
			ap.removeCart();
		   
		   String noofitemCart=ap.checkOutItem.getText();
		   System.out.println( "the value of "+noofitemCart);
		   Assert.assertEquals("0", noofitemCart);
			
				
	
		
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}

// TODO: activity board
// TODO:  Patient tracker screen
