package com.currencyapi.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;

import com.currencyapi.util.APIConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

public class BaseTest
{
   public Properties prop;
   public RequestSpecification reqSpec;
   public ResponseSpecification respSpec;
   public JsonPath js;
   
   /**
    * This method initializes the properties object
    */
   public void init_prop()
   {
	   prop=null;
	   try 
	   {
		FileInputStream file=new FileInputStream(APIConstants.CONFIG_FILE_PATH);
	    prop=new Properties(); 
	    prop.load(file);
	   } 
	   catch (FileNotFoundException e) 
	   {
		System.out.println("config file was not found.Check the file path.");
	   } 
	   catch (IOException e)
	   {
		System.out.println("Error occured while reading the properties file");
	   }
    }
   
   
   @BeforeMethod
   public void setUp()
   {
	   //Initialize the properties 
	    init_prop();
	    
	   //Construct RequestSpecBuilder to specify the common parameters
	   reqSpec=new RequestSpecBuilder()
	  .setBaseUri(prop.getProperty("base_uri"))	  
	  .addHeader("X-RapidAPI-Key", prop.getProperty("api_key"))
	  .build();
	   
	  //Construct ResponseSpecBuilder to assert common parameters in the response body of all the apis
	      //Adding common response headers in a HashMap
	   Map<String,Object> resHeaders=new HashMap<String,Object>();
	   resHeaders.put("Content-Type",APIConstants.CONTENT_TYPE);
	   resHeaders.put("Server", APIConstants.SERVER_NAME);
	   resHeaders.put("X-RapidAPI-Version", APIConstants.API_VERSION);
	   
	   respSpec=new ResponseSpecBuilder()
	  .expectStatusCode(APIConstants.SUCCESS_STATUS_CODE)	
	  .expectStatusLine(APIConstants.SUCCESS_STATUS_LINE)
	  .expectHeaders(resHeaders)
	  .expectBody("status",equalTo("success"))
	  .build();
  }
   
  }
