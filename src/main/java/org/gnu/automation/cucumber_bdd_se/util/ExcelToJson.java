package org.gnu.automation.cucumber_bdd_se.util;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * ExcelToJson class is responsible to convert a table on a Excel sheet
 * of an Workbook. The first row of sheet has the columns names, and the others
 * rows until end of sheet are data. ExcelToJson returns an object instance of
 * class JsonObject with Excel data
 * 
 * JsonObject Sample:
 * 
 * @author josemarsilva@yahoo.com.br
 * 
{
  "workbook":
   {
     "name": "c:\\sample.xls",
     "worksheets":
     [
      {
        "worksheet":
        {
          "index": 0,
          "name": "sheet1",
		  "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}],
          "rows":
          [
            { "index": 0, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
            { "index": 1, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
            { "index": 2, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" }
          ]
        }
      },
      {
        "worksheet":
        {
          "index": 1,
          "name": "sheet2",
		  "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}],
          "rows":
          [
            { "index": 0, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" }
          ]
        }
      },
      {
        "worksheet":
        {
          "index": 2,
          "name": "sheet3",
		  "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}]
        }
      }
     ]
   }
}
 * 
 * 
 */
public class ExcelToJson {
	
	final static Logger logger = Logger.getLogger(ExcelToJson.class);
	
	private Workbook workbook;
	private LinkedHashMap<String, Integer> tableColumns = new LinkedHashMap<String, Integer>();
	private JsonObject jsonObjectWorkbook = new JsonObject();
	
	/*
	 * Error Messages .... 
	 */
	public final static String ERRORMSG_FILENAME_CANT_BE_NULL = new String("Error: Workbook file name can not be null");
	public final static String ERRORMSG_LOAD_EXCEPTION = new String("Error: Exception '%s' occurred when trying to load workbook '%s'");
	public final static String ERRORMSG_SHEET_DOES_NOT_EXISTS = new String("Error: Sheet can not be null");

	

	/*
	 * Constructor (wbFilename) - Constructor
	 * 
	 * @param wbFilename  Workbook filename
	 * 
	 */
	public ExcelToJson(String wbFilename) throws Exception {
		// { "workbook": {"name": "c:\\sample.xls" } } }"
		JsonObject jsonWorkbookName = new JsonObject();
		jsonWorkbookName.addProperty("name", wbFilename);
		jsonObjectWorkbook.add("workbook", jsonWorkbookName);
		loadWorksheets(wbFilename);
	}
	
	
	/*
	 * getJsonObject() - Get Workbook converted to JsonObject
	 * 
	 * @return
	 */
	public JsonObject getJsonObject() throws Exception {
		return jsonObjectWorkbook;
	}
	
	
	/*
	 * loadWorksheets () - Iterate all sheets 
	 * 
	 */
	private void loadWorksheets(String wbFilename) throws Exception {
		
		// Get workbook from file ...
		
		if (wbFilename==null) {
			throw new Exception(ERRORMSG_FILENAME_CANT_BE_NULL);
		} else {
			try {
				this.workbook = WorkbookFactory.create(new FileInputStream(wbFilename.replace("\\", "//")));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(ERRORMSG_LOAD_EXCEPTION.replaceFirst("%s", e.getClass().getName()).replaceFirst("%s", wbFilename));
			}
		}
		
		// { "workbook": {"name": "c:\\sample.xls" }, "worksheets": [] } }"
		JsonArray jsonArrayWorksheets = new JsonArray(); 

		// Foreach Load worksheet(i) ...
		for (int i=0; i<this.workbook.getNumberOfSheets(); i++ ) {
			// Add jsonWorksheet to jsonArrayWorksheets ...
			JsonObject jsonWorksheet = getJsonWorksheet( this.workbook.getSheetAt(i), i );
			jsonArrayWorksheets.add(jsonWorksheet);
		}
		
		// Add jsonArrayWorksheets to jsonObjectWorkbook ...
		jsonObjectWorkbook.add("worksheets", jsonArrayWorksheets);
		
	}


	/*
	 * getJsonWorksheet(Sheet sheet) - Get table of sheet in workbook
	 * 
	 * @param wsName Worksheet name
	 * @return JsonObject
	 * 
	 */
	private JsonObject getJsonWorksheet(Sheet sheet, int sheetIndex) throws Exception {
		
		// Check sheet before continue ...
		if (sheet==null) {
			throw new Exception(ERRORMSG_SHEET_DOES_NOT_EXISTS);
		}
		
		// Get jsonWorksheetCols ...
		JsonObject jsonWorksheetCols = getJsonWorksheetCols(sheet.getRow(0));
		JsonArray jsonWorksheetRows = new JsonArray(); 
		
		// Loop all rows vs all coluns of sheet ...
		for (int i=0+1;i<=sheet.getLastRowNum(); i++ ) {
			Row row = sheet.getRow(i);
			if (row != null) {
				// jsonWorksheetRow = { "index": 0, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" } 
				JsonObject jsonWorksheetRow = new JsonObject();
				for (int j=0;j<=row.getLastCellNum();j++) {
					Cell cell = row.getCell(j);
					if (cell!=null) {
						if (!getCellValue(cell).equals("")) {
							String colName = new String("");
							colName = getTableColumnByIndex(this.tableColumns,j);
							if (colName != null) {
								if (!colName.equals("")) {
									if (colName!=null) {
										// jsonRow = { "row": <row>, "field#1": "value#1", "field#2": "value#2", ... , "field#n": "value#n" } 
										jsonWorksheetRow.addProperty(colName, getCellValue(cell));
										//System.out.println(colName + ": " + getCellValue(cell));
									}
									
								}
							}
						}
					}
				}
				// "rows": [ { "index": 0, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
				//		     { "index": 1, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
				//		     { "index": 2, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" } ]
				if (jsonWorksheetRows!=null) jsonWorksheetRows.add(jsonWorksheetRows);
			}
		}
		// { "index": 0, "name": "sheet1", 
		//     "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}],
		//     "rows": [{ "index": 0, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
		//              { "index": 1, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" },
		//              { "index": 2, "field#1": "value#1", "field#2": "value#2", "field#n": "value#n" } ] 
		// } 
		JsonObject jsonWorksheetReturn = new JsonObject();
		jsonWorksheetReturn.addProperty("index", sheetIndex);
		jsonWorksheetReturn.addProperty("name", sheet.getSheetName());
		jsonWorksheetReturn.add("jsonWorksheetCols", jsonWorksheetCols);
		jsonWorksheetReturn.add("rows", jsonWorksheetRows);
		return jsonWorksheetReturn;
		
	}
	
	
	/*
	 * getJsonWorksheetCols(row) - Get Json with all sheet columns names
	 * 
	 * @param row First row
	 * @return JsonObject 
	 * 
	 * { "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}] }
	 * 
	 */
	private JsonObject getJsonWorksheetCols(Row row){
		JsonObject jsonWorksheetCols = new JsonObject(); 
		JsonArray jsonArrayCols = new JsonArray(); 
		if (row != null) {
			for (int i=0;i<row.getLastCellNum();i++) {
				Cell cell = row.getCell(i);
				if (cell!=null) {
					if (getCellValue(cell)!=null) {
						if (!getCellValue(cell).equals("")) {
							// Save column in tableColumns ...
							this.tableColumns.put(getCellValue(cell),i);
							// {"index": 0, "name": "field#n1"}
							JsonObject jsonCol = new JsonObject();
							jsonCol.addProperty("index", i);
							jsonCol.addProperty("name", getCellValue(cell));
							// [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}]
							jsonArrayCols.add(jsonCol);
						}
					}
				}
			}
		}
		// { "cols": [{"index": 0, "name": "field#1"}, {"index": 1, "name": "field#1"}, {"index": 2, "name": "field#n"}] }
		if (jsonArrayCols!=null) jsonWorksheetCols.add("cols", jsonArrayCols);
		return jsonWorksheetCols;
	}
	

	/*
	 * getTableColumnByIndex (tableColumnsMap,index) - Get column name by index
	 */
	private String getTableColumnByIndex(LinkedHashMap<String, Integer> tableColumnsMap,int index){
		for (Entry<String, Integer> entry : tableColumnsMap.entrySet()) {
			if (entry.getValue()==index) {
				return entry.getKey(); 
			}
		}
		// default
	    return null;
	}
	
	
	/*
	 * getCellValue (cell) - Get cell value as string
	 * 
	 * @param cell Cell
	 * 
	 */
	private String getCellValue(Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();

			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cell.getDateCellValue());
				}
				Double d = new Double(String.valueOf(cell.getNumericCellValue()));
				if (d % 1 == 0)
					return String.valueOf(d.longValue());
				else
					return String.valueOf(d);

			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());

			case Cell.CELL_TYPE_FORMULA:
				return getCellValue(workbook.getCreationHelper().createFormulaEvaluator().evaluateInCell(cell));

			case Cell.CELL_TYPE_BLANK:
				return cell.getStringCellValue();
			}
		}
		
		// Default null
		return new String();
	}
	
	
}
