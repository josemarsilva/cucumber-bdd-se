package org.gnu.automation.cucumber_bdd_se;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.gnu.automation.cucumber_bdd_se.util.ExcelToJson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
        assertTrue( true );
    }
    
    public void testExcelToJson() throws Exception
    {
    	System.out.println("testExcelToJson()");
    	
    	ExcelToJson excelToJsonTestData = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\TestData.xlsx");
    	ExcelToJson excelToJsonConfigLayout = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\ConfigLayout.xlsx");
    	ExcelToJson excelToJsonConfigCredencial = new ExcelToJson(".\\src\\main\\resources\\testdata\\crmpro\\ConfigCredencial.xlsx");
    	
    	System.out.println(excelToJsonTestData.getJsonObject().toString());

    	assertTrue( true );
    }
 
    
}
