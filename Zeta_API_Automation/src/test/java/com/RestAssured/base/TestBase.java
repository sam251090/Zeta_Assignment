package com.RestAssured.base;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
   
	public static RequestSpecification httpRequest;
	public static Response response;
	Path currentDir = Paths.get(System.getProperty("user.dir"));
	Path Log4j_Path = Paths.get(currentDir.toString(), "src", "test", "resources", "Log4j", "Log4j.properties"); 
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		
		logger=Logger.getLogger("Learning");
		BasicConfigurator.configure();
		PropertyConfigurator.configure(Log4j_Path.toString());
		logger.setLevel(Level.DEBUG);		
	}
}
