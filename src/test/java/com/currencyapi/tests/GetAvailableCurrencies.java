package com.currencyapi.tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.currencyapi.base.BaseTest;
import com.currencyapi.util.APIConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;

public class GetAvailableCurrencies extends BaseTest
{
    @Test(description="verify GetAvailableCurrencies endpoint")
    @Severity(SeverityLevel.NORMAL)
    @Description("Testcase- Verify that Get Available Currency  API works properly")
    public void verifyAvailableCurrenciesTest()
    {
    	String endpoint="/currency/list";
    	
    	//Sending the request and extracting the response
       String response= 
    given()
        //.log().all()
          .spec(reqSpec)
    .when()
          .get(endpoint)
    .then()
        //.log().all()
          .spec(respSpec).extract().response().asString();
       
       //Parsing the response to a JS object
        js=new JsonPath(response);
       int currenciesList=js.getInt("currencies.size()");
       
       //Assertion
       Assert.assertEquals(currenciesList,APIConstants.TOTAL_CURRENCIES_COUNT,"Assertion failed for Available Currencies");
       
    }
}
