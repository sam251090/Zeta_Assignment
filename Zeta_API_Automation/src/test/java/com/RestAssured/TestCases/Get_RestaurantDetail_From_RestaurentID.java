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

public class Get_RestaurantDetail_From_RestaurentID extends TestBase{

public static String user_key, baseURI, basePath, restaurant_ID;
	
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
	void getRestaurantDetail_From_RestaurantsID_Request() throws Exception
	{
		logger.info("* * * * * * * * Get Restaurant Detail using Restaurant's ID Start * * * * * * * * *");
		user_key = Excel_File.getCellData(1, 1);
		restaurant_ID = Excel_File.getCellData(22, 1);

		baseURI="https://developers.zomato.com/api/v2.1";
		basePath="/restaurant";
		
		Map<String, String> queryMap= new HashMap<String, String>();
		queryMap.put("res_id", restaurant_ID);
		
		
		Map<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Accept", "application/json");
		headerMap.put("user-key", user_key);
		
		response=Http_methods.Get_ethod_with_queryParam(headerMap, baseURI, basePath, queryMap);
		
	}
	
									/* Http Response */
	
	@Test
	void getRestaurantDetail_From_RestaurantID_Response() throws Exception
	{
		logger.info("* * * * * * * * Checking Request Body * * * * * * * * *");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body is ==>" +responseBody);
		Assert.assertTrue(responseBody != null);
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		String restaurant_ID1 = jsonPathEvaluator.get("R.res_id").toString();
		
		logger.info("* * * * * * * * Restaurant ID --> " + restaurant_ID  + " * * * * * * * * *");
		
		Excel_File.setCellData(22, 1, restaurant_ID);
		
		Assert.assertTrue(restaurant_ID.equals(restaurant_ID1));
		
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
