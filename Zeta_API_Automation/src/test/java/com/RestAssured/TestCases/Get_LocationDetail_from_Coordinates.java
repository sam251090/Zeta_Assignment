package com.RestAssured.TestCases;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.RestAssured.Method.Http_methods;
import com.RestAssured.base.TestBase;

import excelSheet.Excel_File;
import io.restassured.path.json.JsonPath;

public class Get_LocationDetail_from_Coordinates extends TestBase {
	
	public static String user_key, baseURI, basePath, latitude, longitude, entity_Type, entity_ID;
	
	public static Http_methods hh=new Http_methods();
	
	Excel_File excel=new Excel_File();
	Path currentDir = Paths.get(System.getProperty("user.dir"));
	Path Excel_Path = Paths.get(currentDir.toString(), "src", "test", "resources", "Excel", "Excel_Data.xlsx"); 
	
									/*Excel file call for fetching data from excel*/
	
	@BeforeSuite
	public void excel_call() throws Exception{
		Excel_File.setExcelFile(Excel_Path.toString(), "API_Data");
		
	}
	
									/* Http Request */
	
	@BeforeClass
	void getLocationDetailRequest() throws Exception
	{
		logger.info("* * * * * * * * Get Location Detail Start * * * * * * * * *");
		user_key = Excel_File.getCellData(1, 1);
		latitude = Excel_File.getCellData(15, 1);
		longitude = Excel_File.getCellData(16, 1);

		baseURI="https://developers.zomato.com/api/v2.1";
		basePath="/geocode";
		
		Map<String, String> queryMap= new HashMap<String, String>();
		queryMap.put("lat", latitude);
		queryMap.put("lon", longitude);
		
		Map<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Accept", "application/json");
		headerMap.put("user-key", user_key);
		
		response=Http_methods.Get_ethod_with_queryParam(headerMap, baseURI, basePath, queryMap);
		
	}
	
									/* Http Response */
	
	@Test
	void getLocationDetailResponse() throws Exception
	{
		logger.info("* * * * * * * * Checking Request Body * * * * * * * * *");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body is ==>" +responseBody);
		Assert.assertTrue(responseBody != null);
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		entity_Type = jsonPathEvaluator.get("location.entity_type").toString();
		entity_ID = jsonPathEvaluator.get("location.entity_id").toString();
		
		logger.info("* * * * * * * * Entity Type --> " + entity_Type + " * * * * * * * * *");
		logger.info("* * * * * * * * Entity ID --> " + entity_ID + " * * * * * * * * *");
		
		Excel_File.setCellData(17, 1, entity_Type);
		Excel_File.setCellData(18, 1, entity_ID);
		
		Assert.assertTrue(entity_Type != null);
		Assert.assertTrue(entity_ID != null);
		
	}
	
									/* Status of of Response */
	
	@Test
	void checkStatusCode()
	{
		logger.info("* * * * * * * * Checking Status Time * * * * * * * * *");
		
		int statusCode = response.getStatusCode();
		logger.info("Status code is ==>" +statusCode);
		Assert.assertEquals(statusCode, 200);
		
	}
	
									/* Response time */
	
	@Test
	void checkResponseTime()
	{
		logger.info("* * * * * * * * Checking Response Time * * * * * * * * *");
		
		long responseTime=response.getTime();
		logger.info("ResponseTime ==>" +responseTime);
		
		if(responseTime > 2000)
		{
			logger.warn("Response Time is greater than 2000");
			
			Assert.assertTrue(responseTime < 2000);
		}
	}
	
									/* Status Line */
	
	@Test
	void checkStatusLine()
	{
		logger.info("* * * * * * * * Checking Status Line * * * * * * * * *");
		
		String statusLine=response.getStatusLine();
		logger.info("Status Line is ==>" +statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("* * * * * * * * Finished Get List of Categories testcase * * * * * * * * *");
	}
	
									/*  Excel file close  */
	@AfterSuite
	public void excel_close() throws Exception{
		Excel_File.closeExcelFile(Excel_Path.toString());
		
	}
}
