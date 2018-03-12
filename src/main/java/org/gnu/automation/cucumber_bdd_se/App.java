package org.gnu.automation.cucumber_bdd_se;

import org.gnu.automation.cucumber_bdd_se.util.ExcelToJson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
    	ExcelToJson excelToJsonTestData = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\TestData.xlsx");
    	ExcelToJson excelToJsonConfigLayout = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\ConfigLayout.xlsx");
    	ExcelToJson excelToJsonConfigCredencial = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\ConfigCredencial.xlsx");
    	
//    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    	String json = gson.toJson(excelToJsonTestData.getJsonObject());
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    	String json = gson.toJson(excelToJsonTestData.getJsonObject());

    	
    	
    }
}
