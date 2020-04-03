package com.currencyapi.util;

public class APIConstants 
{
  //Config file path
  public static String CONFIG_FILE_PATH=System.getProperty("user.dir")+"\\src\\main\\java\\com\\currencyapi\\config\\config.properties";
  
  //Response Constants
  public static int SUCCESS_STATUS_CODE=200;
  public static String SUCCESS_STATUS_LINE="HTTP/1.1 200 OK";
  public static String SERVER_NAME="RapidAPI-1.1.0";
  public static String API_VERSION="1.1.0";
  public static String CONTENT_TYPE="application/json";
  
  public static String BASE_CURRENCY_CODE="EUR";
  public static String BASE_CURRENCY_NAME="Euro";
  public static int TOTAL_CURRENCIES_COUNT=77;
  
  
  
}
