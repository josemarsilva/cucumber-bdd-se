package org.gnu.automation.cucumber_bdd_se;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.gnu.automation.cucumber_bdd_se.util.ToJsonFromExcel;
import org.gnu.automation.cucumber_bdd_se.util.TableOf;
import org.gnu.automation.cucumber_bdd_se.util.WorkbookSheetTable;

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
    
    public void testToJsonFromExcel() throws Exception
    {
    	ToJsonFromExcel excelToJsonTestData = new ToJsonFromExcel(".\\src\\main\\resources\\testdata\\crmpro\\TestData.xlsx");
    	ToJsonFromExcel excelToJsonConfigLayout = new ToJsonFromExcel(".\\src\\main\\resources\\testdata\\crmpro\\ConfigLayout.xlsx");
    	ToJsonFromExcel excelToJsonConfigCredencial = new ToJsonFromExcel(".\\src\\main\\resources\\testdata\\crmpro\\ConfigCredencial.xlsx");

    	assertTrue( true );
    }
    
    public void testWorkbookWorksheetTable() throws Exception
    {
    	WorkbookSheetTable wbSheetTableTestData = new WorkbookSheetTable(".\\src\\main\\resources\\testdata\\crmpro\\TestData.xlsx");
    	WorkbookSheetTable wbSheetTableConfigRecType = new WorkbookSheetTable(".\\src\\main\\resources\\testdata\\crmpro\\ConfigLayout.xlsx", "RecordType");
    	WorkbookSheetTable wbSheetTableConfigRecField = new WorkbookSheetTable(".\\src\\main\\resources\\testdata\\crmpro\\ConfigLayout.xlsx", "RecordField");
    	WorkbookSheetTable wbSheetTableConfigCredencial = new WorkbookSheetTable(".\\src\\main\\resources\\testdata\\crmpro\\ConfigCredencial.xlsx");
    	
    	TableOf tableOfTestData = wbSheetTableTestData.getTableOfWorkbookSheetTable();
    	TableOf tableOfConfigRecType = wbSheetTableConfigRecType.getTableOfWorkbookSheetTable();
    	TableOf tableOfConfigRecField = wbSheetTableConfigRecField.getTableOfWorkbookSheetTable("RecordField");
    	TableOf tableOfTableConfigCredencial = wbSheetTableConfigCredencial.getTableOfWorkbookSheetTable();

    	tableOfTestData.export(".\\Evidencia\\TestData.txt");
    	tableOfConfigRecType.export(".\\Evidencia\\ConfigRecType.txt");
    	tableOfConfigRecField.export(".\\Evidencia\\ConfigRecField.txt");
    	tableOfTableConfigCredencial.export(".\\Evidencia\\ConfigCredencial.txt");

    	assertTrue( true );
    }

 
    
}
