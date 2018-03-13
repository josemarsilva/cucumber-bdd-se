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
import org.gnu.automation.cucumber_bdd_se.util.RecordOf;
import org.gnu.automation.cucumber_bdd_se.util.TableOf;

/*
 * WorkbookSheetTable class is responsible to represent a table on a Excel sheet
 * of an Workbook. The first row of sheet has the columns names, and the others
 * rows until end of sheet are data. WorkbookSheetTable can return a object of
 * class TableOf with Excel data
 * 
 * @author josemarsilva
 * 
 */
public class WorkbookSheetTable {
	
	final static Logger logger = Logger.getLogger(WorkbookSheetTable.class);
	
	/*
	 * Private properties ...
	 */
	private String workbookFileName;
	private String sheetName;
	private Workbook workbook;
	private LinkedHashMap<String, Integer> tableColumns = new LinkedHashMap<String, Integer>();
	
	/*
	 * Private Error messages ...
	 */
	public final static String MSG_TXT_ERROR_WORKBOOKSHEETTABLE_FILENAME_CANT_BE_NULL = new String("Error: Workbook file name can not be null");
	public final static String MSG_TXT_ERROR_WORKBOOKSHEETTABLE_LOAD_EXCEPTION = new String("Error: Exception '%s' occurred when trying to load workbook '%s'");
	public final static String MSG_TXT_ERROR_WORKBOOKSHEETTABLE_SHEET_DOES_NOT_EXISTS = new String("Error: Sheet '%' in workbook '' does not exists");

	/*
	 * Constructor - Constructor
	 */
	public WorkbookSheetTable() throws Exception {
		this(null, null);
	}
	
	
	/*
	 * Constructor (wbFilename) - Constructor
	 * 
	 * @param wbFilename  Workbook filename
	 * 
	 */
	public WorkbookSheetTable(String wbFilename) throws Exception {
		this(wbFilename, null);
	}
	
	
	/*
	 * Constructor (wbFilename,wsName) - Constructor
	 * 
	 * @param wbFilename  Workbook filename
	 * @param wsName      Worksheet name
	 * 
	 */
	public WorkbookSheetTable(String wbFilename, String wsName) throws Exception {
		setWorkbookFileName(wbFilename);
		setSheetName(wsName);
		load();
	}
	
	
	/*
	 * setWorkbookFileName (wbFilename) - Set 'workbookFileName' property 
	 * 
	 * @param wbFilename  Workbook filename
	 * 
	 */
	private void setWorkbookFileName(String wbFilename) {
		this.workbookFileName = wbFilename;
	}
	
	/*
	 * setSheetName (wsName) - Set 'sheetName' property 
	 * 
	 * @param wsName  Worksheet name
	 * 
	 */
	private void setSheetName(String wsName) {
		this.sheetName = wsName;
	}
	
	/*
	 * getWorkbookFileName () - Return 'workbookFileName' property 
	 * 
	 * @return return 'workbookFileName'
	 * 
	 */
	private String getWorkbookFileName() {
		return this.workbookFileName;
	}
	
	/*
	 * getSheetName () - Return 'sheetName' property 
	 * 
	 * @return return 'sheetName'
	 * 
	 */
	private String getSheetName() {
		return this.sheetName;
	}
	
	/*
	 * load () - Load workbook 
	 * 
	 */
	public void load() throws Exception {
		
		// Get workbook from file ...
		
		if (getWorkbookFileName()==null) {
			throw new Exception(MSG_TXT_ERROR_WORKBOOKSHEETTABLE_FILENAME_CANT_BE_NULL);
		} else {
			try {
				this.workbook = WorkbookFactory.create(new FileInputStream(getWorkbookFileName().replace("\\", "//")));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(MSG_TXT_ERROR_WORKBOOKSHEETTABLE_LOAD_EXCEPTION.replaceFirst("%s", e.getClass().getName()).replaceFirst("%s", getWorkbookFileName()));
			}
		}
		
		// Default sheet is the first sheet of workbook at index 0 ...
		if (getSheetName()==null) {
			setSheetName(this.workbook.getSheetAt(0).getSheetName());
		}
		
	}


	/*
	 * getTableOfWorkbookSheetTable() - Get table of sheet in workbook
	 */
	public TableOf getTableOfWorkbookSheetTable() throws Exception {
		return getTableOfWorkbookSheetTable(null);
	}
	
	
	/*
	 * getTableOfWorkbookSheetTable(wsName) - Get table of sheet in workbook
	 * 
	 * @param wsName Worksheet name
	 * 
	 */
	public TableOf getTableOfWorkbookSheetTable(String wsName) throws Exception {
		
		TableOf tableOfWorkbookSheetTable = new TableOf();

		// Find out sheet (default is first sheet of workbook ) ...
		
		Sheet sheet = null;
		if (wsName==null) {
			sheet = this.workbook.getSheetAt(0);
		} else {
			if (wsName.equals("")) {
				sheet = this.workbook.getSheetAt(0);
			} else {
				sheet = this.workbook.getSheet(wsName);
			}
		}
		
		// Check sheet before continue ...
		if (sheet==null) {
			throw new Exception(MSG_TXT_ERROR_WORKBOOKSHEETTABLE_SHEET_DOES_NOT_EXISTS.replaceFirst("%s", (wsName==null) ? "" : wsName).replaceFirst("%s", this.getWorkbookFileName()));
		}
		
		getAllTableColumns(sheet.getRow(0));
		// Loop all rows vs all coluns of sheet ...
		for (int i=0+1;i<=sheet.getLastRowNum(); i++ ) {
			Row row = sheet.getRow(i);
			if (row != null) {
				RecordOf recordOf = new RecordOf();
				for (int j=0;j<=row.getLastCellNum();j++) {
					Cell cell = row.getCell(j);
					if (cell!=null) {
						if (!getCellValue(cell).equals("")) {
							String colName = new String("");
							colName = getTableColumnByIndex(this.tableColumns,j);
							if (colName != null) {
								if (!colName.equals("")) {
									if (colName!=null) {
										recordOf.set(colName, getCellValue(cell));
									}
									
								}
							}
						}
					}
				}
				tableOfWorkbookSheetTable.add(recordOf);
			}
		}
		return tableOfWorkbookSheetTable;
		
	}
	
	
	/*
	 * getAllTableColumns(row) - Get all table columns from first row 
	 * 
	 * @param row First row
	 * 
	 */
	private void getAllTableColumns(Row row){
		if (row != null) {
			for (int i=0;i<row.getLastCellNum();i++) {
				Cell cell = row.getCell(i);
				if (cell!=null) {
					if (getCellValue(cell)!=null) {
						if (!getCellValue(cell).equals("")) {
							this.tableColumns.put(getCellValue(cell),i);
						}
					}
				}
			}
		}
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
