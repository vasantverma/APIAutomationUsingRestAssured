package com.currencyapi.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelUtil 
{
	  public static  Workbook book;
	   public static Sheet datasheet;
	   public static Object[][] getTestData(String sheetName)
	   {
		  Object data[][]=null;
		   try 
		   {
			FileInputStream ip=new FileInputStream(APIConstants.TESTDATA_FILE_PATH);
			book=WorkbookFactory.create(ip);
			 datasheet=book.getSheet(sheetName);
			data=new Object[datasheet.getLastRowNum()][datasheet.getRow(0).getLastCellNum()];
			for(int i=0;i<datasheet.getLastRowNum();i++)
			{
				for(int j=0;j<datasheet.getRow(0).getLastCellNum();j++)
				{
					data[i][j]=datasheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		   } 
		   catch (FileNotFoundException e) 
		   {
			e.printStackTrace();
		   } catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return data;   
	   }
}
