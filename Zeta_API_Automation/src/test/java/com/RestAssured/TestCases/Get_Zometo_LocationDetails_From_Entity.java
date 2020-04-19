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

public class Get_Zometo_LocationDetails_From_Entity extends TestBase
{

	public static String user_key, baseURI, basePath, entity_Type, entity_ID, restaurant_ID;
	
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
	void getLocationDetail_From_Entity_Request() throws Exception
	{
		logger.info("* * * * * * * * Get Location Details using entity_ID and entity_Type Start * * * * * * * * *");
		user_key = Excel_File.getCellData(1, 1);
		entity_Type = Excel_File.getCellData(17, 1);
		entity_ID = Excel_File.getCellData(18, 1);

		baseURI="https://developers.zomato.com/api/v2.1";
		basePath="/location_details";
		
		Map<String, String> queryMap= new HashMap<String, String>();
		queryMap.put("entity_type", entity_Type);
		queryMap.put("entity_id", entity_ID);
		
		
		Map<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Accept", "application/json");
		headerMap.put("user-key", user_key);
		
		response=Http_methods.Get_ethod_with_queryParam(headerMap, baseURI, basePath, queryMap);
		
	}
	
									/* Http Response */
	
	@Test
	void getLocationDetail_From_Entity_Response() throws Exception
	{
		logger.info("* * * * * * * * Checking Request Body * * * * * * * * *");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body is ==>" +responseBody);
		Assert.assertTrue(responseBody != null);
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		restaurant_ID = jsonPathEvaluator.get("best_rated_restaurant.restaurant[0].R.res_id").toString();
		entity_ID = jsonPathEvaluator.get("location.entity_id").toString();
		
		logger.info("* * * * * * * * Restaurant ID --> " + restaurant_ID  + " * * * * * * * * *");
		
		Excel_File.setCellData(22, 1, restaurant_ID);
		
		Assert.assertTrue(restaurant_ID != null);
		
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

