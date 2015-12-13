package com.qa.automation.testsuites;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Hashtable;

import org.junit.After;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.qa.automation.toolbox.GeneralMethods;
import com.qa.automation.toolbox.AutoTestCase;
import com.qa.automation.smoketest.*;
import com.qa.automation.toolbox.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AutoTest{
	
public static void main(String arg[]) throws Exception   
	{   
		
		
		// Process command line variables
		String deployment = "";
		String environment = "";
		String set = "";
		String orgSelect = "";
		String cmdLineError = "* * * Command Line Error: Must specify command line arguments:" + "\n"
		+"     arg[0] = custom environment string in region url [ qa ...]  (required)" + "\n"
		;
		
		try{
			environment = arg[0];
			environment = environment.toLowerCase();
			
			deployment = AutomationSettings.getTestDataItem("build");
			deployment = deployment.replace("environment", environment);

			if (environment.equals(""))
			{
				System.out.println(cmdLineError);
				return;
			}
			
			
			
		} catch (Exception e){
			System.out.println(cmdLineError);
			return;
		}
		
		
		
		
		AutoTestCase.set = set;
	
	
		AutoTestCase.artifactPath = AutomationSettings.getArtifactPath();
		AutoTestCase.artifact = AutoTestCase.artifactPath + "SmokeTestRunTranscript_"+environment;
		
		AutoTestCase.build = environment;
		String timeStamp = GeneralMethods.GenerateTimeStamp();
		AutoTestCase.timeStamp = timeStamp;
	
		FileWriter fstream, ts;
	
		try{
			
			

			   // Clear the artifacts 
			   fstream = new FileWriter(AutoTestCase.artifact+".csv", false);
			   fstream.close();
			
			
			ts = new FileWriter(AutoTestCase.artifactPath+"ts_"+environment+".txt", false);
			//ts.write(timeStamp);
			ts.close();
						
		} catch (Exception e){
			System.out.println("Exception thrown when attempting to create a new log file");
			return;
		}
		////
		
		// Run the suite
		Artifact.CreateArtifact(AutoTestCase.artifact);
		String test = "";
		int exitcode = 0;
		try {
		 
			
		  test = "Place Order";
		  PlaceOrder.test(); // run the test
		 test="Update Account";
		  UpdateAccount.test();
		  test="Remove Cart";
		  RemoveCart.test();
			
		
		} catch (Exception e) {
			System.out.println("\nAUTO SMOKE TEST SUITE: Exception thrown when running test " + AutoTestCase.testName + "\n" + e.getMessage());
			exitcode = 1;
		}
	
		
		Artifact.renderFlags();
		GeneralMethods.stopWebdriver(AutoTestCase.driver);
		System.exit(exitcode);
	}   
}

