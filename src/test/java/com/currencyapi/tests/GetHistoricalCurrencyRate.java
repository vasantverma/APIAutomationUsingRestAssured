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

public class GetHistoricalCurrencyRate extends BaseTest
{
  @Test(description="verify the GetHistoricalCurrencyRate endpoint")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Testcase- Verify that Get Historical Currency Rate API works properly")
  public void verifyHistoricalCurrencyRateTest()
  {
	  String endpoint="/currency/historical/{date}";
	  
	 //Sending the api request and extracting the response
	String response= 
	 given()	
	   //.log().all()
	     .spec(reqSpec)
	     .queryParam("amount",prop.getProperty("amount"))
	     .pathParam("date", prop.getProperty("date"))
	 .when()
	      .get(endpoint)
	 .then()
	    //.log().all()
	      .spec(respSpec)
	      .extract().response().asString();
	      
	  //Parsing the response
	 js=new JsonPath(response);
	 
	 //Assertions
	Assert.assertEquals(Double.parseDouble(js.getString("amount")),Double.parseDouble(prop.getProperty("amount")));
	Assert.assertEquals(js.getString("base_currency_code"), APIConstants.BASE_CURRENCY_CODE);
	Assert.assertEquals(js.getString("base_currency_name"), APIConstants.BASE_CURRENCY_NAME);
	Assert.assertEquals(js.getInt("rates.size()"),APIConstants.TOTAL_CURRENCIES_COUNT, "Assertion failed for retreiving currencies rates of available currencies");  
	  
  }
}
