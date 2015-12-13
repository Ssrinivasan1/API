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


public class UpdateAccount extends AutoTestCase {

	@Test
	public static void test() throws Exception {
		AutoTestCase.testName = "Update Account";	
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
		   
			// Validate the Update of account 
			currStepResult=ap.clickElement(ap.account, "Account")? "Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "Click My Account ", currStepResult);
			currStepResult=ap.login()? "Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "log in to Account ", currStepResult);
			currStepResult=ap.NavigateAccountDetails()? "Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "Navigate to Account Details ", currStepResult);
			currStepResult=ap.updateAccountDetails()? "Pass":"Fail";
			Thread.sleep(10000);			
			Artifact.VerifyWriteToArtifactS(artifact, "updated the details", currStepResult);
			currStepResult=ap.logout()?"Pass":"Fail";
			System.out.println("Current Step result logout"+currStepResult);
			Artifact.VerifyWriteToArtifactS(artifact, "Log out of the application", currStepResult);
			Thread.sleep(5000);
			currStepResult=ap.login()? "Pass":"Fail";
			System.out.println("Current Step result"+currStepResult);
			Artifact.VerifyWriteToArtifactS(artifact, "log in to Account ", currStepResult);
			Thread.sleep(5000);
			currStepResult=ap.NavigateAccountDetails()? "Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "Navigate to Account Details ", currStepResult);
			//assert the values
			//validating the updated fields
			Thread.sleep(5000);
			String lastName =ap.getTextFieldValue(ap.lastName, "lastName");
			String phone= ap.getTextFieldValue(ap.phone, "Phone");
			Assert.assertEquals("Srinivasan", lastName);
			Assert.assertEquals("5173037890", phone);
			currStepResult=ap.logout()?"Pass":"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, "Log out of the application", currStepResult);
			
				
	
		
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}

// TODO: activity board
// TODO:  Patient tracker screen
