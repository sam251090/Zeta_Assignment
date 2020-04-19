package com.RestAssured.Method;

import java.util.Map;

import com.RestAssured.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class Http_methods extends TestBase{
	
	public static Response Get_method_withOut_queryParam(Map<String, String> headerMap, String baseURI, String basePath) throws InterruptedException
	{
		RestAssured.baseURI=baseURI;
		
		httpRequest = RestAssured.given();
		response=httpRequest.headers(headerMap).request(Method.GET, basePath);
		
		return response;
	}
	
	public static Response Get_ethod_with_queryParam(Map<String, String> headerMap, String baseURI, String basePath, Map<String, String> queryMap) throws InterruptedException
	{
		RestAssured.baseURI=baseURI;
		
		httpRequest = RestAssured.given();
		response=httpRequest.headers(headerMap).queryParams(queryMap).request(Method.GET, basePath);
		
		return response;
	}

}
