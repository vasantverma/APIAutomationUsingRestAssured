package com.currencyapi.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.currencyapi.base.BaseTest;
import com.currencyapi.util.APIConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertCurrency extends BaseTest
{
   @Test(description="verify the Convert Currency api endpoint")
   @Severity(SeverityLevel.BLOCKER)
   @Description("Testcase- Verify that Convert Currency API works properly")
   public void verifyConvertCurrencyTest()
   {
	   String endpoint="/currency/convert";
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	   LocalDateTime now = LocalDateTime.now();  
	   String currentDate=dtf.format(now);
	   
    //Sending the request and extracting the response
	 String response=
	  given()
	  	 //.log().all()
	       .spec(reqSpec)
	       .queryParam("amount",prop.getProperty("amount"))
	   .when()
	       .get(endpoint)
	   .then()
	   		//.log().all()
	       .spec(respSpec)
	       .extract().response().asString();
	 
	 //Parsing to JS object
	    js=new JsonPath(response);
	 
	 //Assertions
	    Assert.assertEquals(Double.parseDouble(js.getString("amount")),Double.parseDouble(prop.getProperty("amount")));
		Assert.assertEquals(js.getString("base_currency_code"), APIConstants.BASE_CURRENCY_CODE);
		Assert.assertEquals(js.getString("base_currency_name"), APIConstants.BASE_CURRENCY_NAME);
		Assert.assertEquals(js.getInt("rates.size()"),APIConstants.TOTAL_CURRENCIES_COUNT, "Assertion failed for retreiving currencies rates of available currencies"); 
		Assert.assertEquals(js.getString("updated_date"),currentDate);
		
		
	     
   }
}
