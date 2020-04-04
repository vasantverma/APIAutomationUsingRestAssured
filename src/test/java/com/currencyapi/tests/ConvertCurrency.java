package com.currencyapi.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.currencyapi.base.BaseTest;
import com.currencyapi.util.APIConstants;
import com.currencyapi.util.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertCurrency extends BaseTest
{
	@DataProvider(name="getDataForAmountParam")
	public Object[][] getAmountData()
	{
		Object[][] data=ExcelUtil.getTestData("ConvertCurrency");
		return data;
	}
	
   @Test(dataProvider="getDataForAmountParam")
   @Severity(SeverityLevel.BLOCKER)
   @Description("Testcase- Verify that Convert Currency API works properly")
   public void verifyConvertCurrencyTest(String amt)
   {
	   String endpoint="/currency/convert";
	   double amount;
	   
	   //Generate current date 
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	   LocalDateTime now = LocalDateTime.now();  
	   String currentDate=dtf.format(now);
	   
	   //Checking for empty or text value in the parameter
	   if(amt.isEmpty() || amt.chars().allMatch(Character::isLetter))
	   {
		   //Default value of amount parameter of the api when input is empty or a text.
		    amount=1;
		    System.out.println("When no amount value is provided,default amount is :"+amount);
		    
	   }
	   else
	   {
		   amount=Double.parseDouble(amt);
	   }
	   System.out.println("Testing for amount :"+amount);
	 
	  //Sending the request and extracting the response
	 String response=
	  given()
	  	 //.log().all()
	       .spec(reqSpec)
	       .queryParam("amount",amount)
	   .when()
	       .get(endpoint)
	   .then()
	   	 //.log().all()
	       .spec(respSpec)
	       .extract().response().asString();
	 
	 //Parsing to JS object
	    js=new JsonPath(response);
	 
	 //Assertions
	    Assert.assertEquals(Double.parseDouble(js.getString("amount")),amount);
		Assert.assertEquals(js.getString("base_currency_code"), APIConstants.BASE_CURRENCY_CODE);
		Assert.assertEquals(js.getString("base_currency_name"), APIConstants.BASE_CURRENCY_NAME);
		Assert.assertEquals(js.getInt("rates.size()"),APIConstants.TOTAL_CURRENCIES_COUNT, "Assertion failed for retreiving currencies rates of available currencies"); 
		Assert.assertEquals(js.getString("updated_date"),currentDate);   
	   
   }
}

