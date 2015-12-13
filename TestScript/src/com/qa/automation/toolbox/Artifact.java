package com.qa.automation.toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;



public class Artifact {
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Opens the BufferedWriter 'writer' managing artifact file named 'fileName' at specified location ("C:\Temp").  
	 * Adds a header and timestamp to the file.  
	 * @param fileName
	 * @param deployment
	 * @param append
	 * @return writer
	 * Added screenshot 
	 */
	
	public static BufferedWriter OpenArtifact(String fileName, String testName, String timeStamp){
		try {
			
			AutoTestCase.failCount= 0;
			AutoTestCase.passCount = 0;
			AutoTestCase.blockCount = 0;
			AutoTestCase.issuesStr = "";
			AutoTestCase.WIPCount = 0;
			AutoTestCase.warningCount = 0;
			AutoTestCase.timer = Calendar.getInstance().getTimeInMillis();
			AutoTestCase.testStepTranscript = "";
			
			FileWriter fstream;
			//fstream = new FileWriter("C:\\"+fileName+".csv", true);	// true = append; false = overwrite
			fstream = new FileWriter(fileName+".csv", true);	// true = append; false = overwrite
			BufferedWriter writer = new BufferedWriter(fstream);
			AutoTestCase.artifactObj = writer;
			return writer;			
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		    return null;
		}
	}

	public static void CreateArtifact(String fileName){
		try {
			FileWriter fstream;
			BufferedWriter writer = null;
			String timeStamp = GeneralMethods.GenerateTimeStamp();
			if ((timeStamp.contains("_0:") || GeneralMethods.CheckNLTHour(4)) && (fileName.equals("SmokeTestRunTranscript_DEV")) ||
					(fileName.equals("SmokeTestRunTranscript_trunk1")) ||
					(fileName.equals("SmokeTestRunTranscript_trunk2")) ||
					(fileName.equals("SmokeTestRunTranscript_releaseBranch")) ||
					(fileName.equals("SmokeTestRunTranscript_alpha")) ||
					(fileName.equals("SmokeTestRunTranscript_stable")) ||
					(fileName.equals("SmokeTestRunTranscript_exp"))) 
				{
				//fstream = new FileWriter("C:\\"+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				fstream = new FileWriter(AutoTestCase.artifactPath+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				writer = new BufferedWriter(fstream);
				//Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Step Result,Comment,,,Timer,,Summary\n");
				Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Data,Test Step Result,Comment,,,Timer,,Summary\n"); // MGB 1/6/2015 added testData
                Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Result,Passed test step count,Failed test step count,Blocked test step count,WIP test step count, Warning test setp count,Issues,SUMMARY,Tester\n");
                Artifact.CloseArtifact(writer);
			}
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void CreaterunParametersFile(String fileName){
		try {
			FileWriter fstream;
			BufferedWriter writer = null;
			String timeStamp = GeneralMethods.GenerateTimeStamp();
			if (timeStamp.contains("_1:")) {
				//fstream = new FileWriter("C:\\"+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				fstream = new FileWriter(AutoTestCase.artifactPath+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				writer = new BufferedWriter(fstream);
				//Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Step Result,Comment,Bug,User Stories/Requirements,Summary,Tester\n");
				Artifact.WriteToArtifact(writer, "Time Stamp,Build Number\n");
                Artifact.CloseArtifact(writer);
			}
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Closes the artifact file managed by BufferedWriter 'writer'. 
	 * @param writer
	 */
	public static void CloseArtifact(BufferedWriter writer){
		try {
			//Report summary
			int countPass = AutoTestCase.passCount;
			int countFail = AutoTestCase.failCount;
			int countBlock = AutoTestCase.blockCount;
			int countWIP = AutoTestCase.WIPCount;
			int countWarning = AutoTestCase.warningCount;
			
			String result = "";
			if (AutoTestCase.failCount > 0) result = "FAIL";
			else if (AutoTestCase.blockCount > 0) result = "BLOCKED";
			else result = "PASS";
			Artifact.WriteToArtifact(writer, AutoTestCase.timeStamp +"," +AutoTestCase.testName +"," +result +"," +countPass +"," +countFail +"," +countBlock +"," +countWIP +"," +countWarning +"," +AutoTestCase.issuesStr +",SUMMARY" +"," +AutoTestCase.tester +",\n");
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CloseParametersFile(BufferedWriter writer){
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Writes a string to the artifact file managed by BufferedWriter 'writer'.
	 * @param writer
	 * @param string
	 */
	public static void WriteToArtifact(BufferedWriter writer, String message){
		try {
			writer.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not write to artifact file.  Make sure file is closed before running test.");
			e.printStackTrace();
		}
	}

	public static void WriteToParametersFile(BufferedWriter writer, String message){
		try {
			writer.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not write to run parameters file.  Make sure file is closed before running test.");
			e.printStackTrace();
		}
	}

	/**
	
	 * ARTIFACT CREATION AND MAINTENANCE: Writes a formatted verification description and result to the artifact file managed by BufferedWriter 'writer'.
	 * @param writer
	 * @param string
	 * @param verificationResult
	 */
	public static void VerifyWriteToArtifact(BufferedWriter writer, String message, Boolean verificationResult){
		String result;
		if (verificationResult == true) result = "Pass"; else result = "Fail";
		WriteToArtifact(writer, message+","+result+"\n");
	}

	public static void VerifyWriteToArtifactS(BufferedWriter writer, String message, String verificationResult){
		message = message.replace(",", "");
		AutoTestCase.testStepTranscript += message+" | ";
		
		message = message.length() > 115 ? message.substring(0, 115) : message;  // MGB 1/21/2015 concatenate message to 115 chars
		message = AutoTestCase.timeStamp +"," +AutoTestCase.testName +"," +message;
		
		// Update P/F/B/WIP counters
		if (verificationResult.contains(AutoTestCase.WIP) == true) AutoTestCase.WIPCount++;
		
		if (verificationResult.contains(AutoTestCase.compliance1) == false && 
			verificationResult.contains(AutoTestCase.WIP) == false){ // MGB 4/8/2014
			if (verificationResult.contains("Pass")) AutoTestCase.passCount++;
			else if (verificationResult.contains("Fail")) AutoTestCase.failCount++;
			else if (verificationResult.contains("Block")) AutoTestCase.blockCount++;
			else if (verificationResult.contains("Warning")) AutoTestCase.warningCount++;
		
			if (verificationResult.contains(AutoTestCase.knownIssue)){
				String[] knownIssues = verificationResult.split(AutoTestCase.knownIssue); // MGB 3/12/2014
				String[] issues = knownIssues[1].split("\\s+"); // MGB 3/12/2014
				for (String issue : issues) AutoTestCase.issuesStr = AutoTestCase.issuesStr.contains(issue)==false ? AutoTestCase.issuesStr +" " +issue +" " : AutoTestCase.issuesStr;  
				AutoTestCase.issuesStr.replace(","," ");	// MGB 3/12/2014			
			}
		}
		////

		String trace = "";
		if (verificationResult.contains("Fail") || verificationResult.contains("Warning"))
			trace = ",failureTrace :" + AutoTestCase.testStepTranscript;
		
		// MGB 10/23/2014: Calculate the test step elapsed time
		long elapsedTime = Calendar.getInstance().getTimeInMillis() - AutoTestCase.timer;
		AutoTestCase.timer = Calendar.getInstance().getTimeInMillis();
		////
		
		// MGB 10/23/2014: Count commas in the verificationResult string so that the elapsed time lines up in the artifact
		String commaStr = ",,,,"; 
		int commaCount = StringUtils.countMatches(verificationResult, ",");
		for (int x = 0; x < commaCount; x++)
			commaStr = commaStr.replaceFirst(",", "");
		
		// MGB 3/6/2015: added NG7TestCase.testComment variable handling so that we can separate data from comments, since they will both share the same cell in the Jira container
		AutoTestCase.testData = AutoTestCase.testData.replace(","," ");
		if (! AutoTestCase.testData.equals("")) AutoTestCase.testData = "TEST STEP DATA: " + AutoTestCase.testData;
		if (! AutoTestCase.testComment.equals("")) AutoTestCase.testComment = AutoTestCase.testComment + "; ";
		AutoTestCase.testComment = AutoTestCase.testComment.replace(","," ") + AutoTestCase.testData;
		WriteToArtifact(writer, message+","+AutoTestCase.testComment+","+verificationResult+commaStr+elapsedTime+trace+"\n"); // MGB 1/6/2015 added testData
		AutoTestCase.testData = ""; // MGB 1/16/2015 clear testData
		AutoTestCase.testComment = ""; // MGB 1/16/2015 clear testData
		
	}
	
	public static void ReportCatastrophicFail(String currStepResult, String message, String Filename){
		
		getScreenShot(AutoTestCase.artifactPath+Filename);
	if (! currStepResult.equals("Pass")){
	    	AutoTestCase.catastrophicCheck |= true;
	    	AutoTestCase.catastrophicMsg.add("Catastrophic: "+message+","+currStepResult);
	    }
	}
	
	public static void ReportDoNotDeliverFail(String currStepResult, String message){
	if (! currStepResult.equals("Pass")){
	    	AutoTestCase.doNotDeliverCheck |= true;
	    	AutoTestCase.doNotDeliverMsg.add("doNotDeliver: "+message+","+currStepResult);
	    }
	}	
	
	public static void renderFlags(){
		AutoTestCase.testName = "CatastrophicChecks"+"_"+AutoTestCase.build;
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), AutoTestCase.testName,AutoTestCase.timeStamp);
		String [] resultArray;
		String result = "";
		
		
		if (AutoTestCase.catastrophicCheck) {
			System.out.println("\n\n* * * * * Catastrophic checks:\n");
		    for (int i=0; i < AutoTestCase.catastrophicMsg.size(); i++){
		    	result = AutoTestCase.catastrophicMsg.get(i);
		   	    System.out.println(result);
		   	    resultArray = result.split(",");
		   	    if (resultArray[1].equals("Warning")){
		   	    	resultArray[0] = resultArray[1].toUpperCase()+"  "+resultArray[0];
		   	    	AutoTestCase.failCount++;
		   	    }	
		        Artifact.VerifyWriteToArtifactS(artifact, resultArray[0], resultArray[1]);
		    }
		}
		
		if (AutoTestCase.doNotDeliverCheck) {
			System.out.println("\n\n* * * * * doNotDeliver checks:\n");
		    for (int i=0; i < AutoTestCase.doNotDeliverMsg.size(); i++){
		    	result = AutoTestCase.doNotDeliverMsg.get(i);
		   	    System.out.println(result);
		   	    resultArray = result.split(",");
		   	    if (resultArray[1].equals("Warning")){
		   	    	resultArray[0] = resultArray[1].toUpperCase()+"  "+resultArray[0];
		   	    	AutoTestCase.failCount++;
		   	    }
		        Artifact.VerifyWriteToArtifactS(artifact, resultArray[0], resultArray[1]);
		    }
		}

		Artifact.CloseArtifact(artifact);
		
		try {
			artifact.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public static void getScreenShot(String filename)  {
		 
		
		    
	  try {
		  File filename1 = new File(filename);
		  File screenshotFile = ((TakesScreenshot)AutoTestCase.driver).getScreenshotAs(OutputType.FILE);
		  try {
			FileUtils.copyFile(screenshotFile, filename1);
		  		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		 
		  }
		 catch (  WebDriverException  e1) {
		    e1.printStackTrace();
		  }
		 
		}
	
	
}

