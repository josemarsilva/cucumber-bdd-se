package org.gnu.automation.cucumber_bdd_se.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.gnu.automation.cucumber_bdd_se.util.RecordOf;
import org.gnu.automation.cucumber_bdd_se.util.TableOf;

public class FileTable {

	final static Logger logger = Logger.getLogger(FileTable.class);
	
	/*
	 * Private properties ...
	 */
	private String fileName = null;
	private TableOf tableOfConfigLayoutRecType = null;
	private TableOf tableOfConfigLayoutRecField = null;
	private TableOf tableOfConfigLayoutRecSegment = null;
	
	/*
	 * Private Error messages ...
	 */
	public final static String MSG_TXT_ERROR_FILETABLE_FILENAME_CANT_BE_NULL = new String("Error: File name can not be null");
	public final static String MSG_TXT_ERROR_FILETABLE_FILENAME_IS_INVALID = new String("Error: File name defined '%s' is invalid or not exists");
	public final static String MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID = new String("Error: Layout configuração arquivo está inconsistente! WorkbookFilename '%s', TableOf '%s', Column '%s', Value '%s'");
	public final static String MSG_TXT_ERROR_FILE_DATA_INVALID = new String("Error: Os dados do arquivo não estão consistente com o Layout de configuração! WorkbookFilename '%s', TableOf '%s', Column '%s', Linha '%s'");
	
	
	/*
	 * FileTable() - Constructor
	 */
	public FileTable(String pFileName, TableOf pTableOfRecType, TableOf pTableOfRecField, TableOf pTableOfRecSegment) {
		this.fileName = pFileName;
		this.tableOfConfigLayoutRecType = pTableOfRecType;
		this.tableOfConfigLayoutRecField = pTableOfRecField;
		this.tableOfConfigLayoutRecSegment = pTableOfRecSegment;
		
	}
	
	/*
	 * getTableOfFile() - Returns TableOf structure with file contents
	 * 
	 * @return TableOf
	 * 
	 */
	public TableOf getTableOfFile() throws Exception {
		TableOf tableOfFileReturn = new TableOf();
		RecordOf recordOfFileToBeAdded = null;
		
		// Parse file into TableOf ...
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(this.fileName);
			br = new BufferedReader(fr);
			int nRow = 1;
			String sCurrentRecordType = new String("");
			String sLastRecordType = new String("");
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				// Get current line ...
				sCurrentRecordType = getRecordType(sCurrentLine);
				
				// CurrentRecordType is valid ?
				if (sCurrentRecordType!=null){
					if (!sCurrentRecordType.equals("")) {
						// Segment ?
						boolean bSegment = false;
						RecordOf RecordOfSegment = tableOfConfigLayoutRecSegment.getRecordOfBy("RecordTypeSegment", sCurrentRecordType);
						if (RecordOfSegment!=null) {
							if (RecordOfSegment.get("RecordTypeSegmentParent")!=null) {
								if (RecordOfSegment.get("RecordTypeSegmentParent").equals(sLastRecordType)) {
									bSegment = true;
								}
							}
						}
						// add recordOf to tableOf or add segment to recordOf ?
						if (bSegment) {
							// add a segment to recordOf ...
							if (recordOfFileToBeAdded==null) {
								recordOfFileToBeAdded = new RecordOf();
							}
							fillRecordOfFile(recordOfFileToBeAdded, sCurrentRecordType, sCurrentLine, nRow);
							
						} else {
							// add recordOf to tableOf ...
							if (recordOfFileToBeAdded==null) {
								// recordOfFileToBeAdded was empty so create new recordOfFileToBeAdded ...
								recordOfFileToBeAdded = new RecordOf();
								// fill recordOfFileToBeAdded with sCurrentLine parse fields ...
								fillRecordOfFile(recordOfFileToBeAdded, sCurrentRecordType, sCurrentLine, nRow);
								// Can't this record be parent of another segment?
								if (tableOfConfigLayoutRecSegment.getRecordOfBy("RecordTypeSegmentParent", sCurrentRecordType)==null) {
									// this record can't be parent of another segment so insert it right now ...
									tableOfFileReturn.add(recordOfFileToBeAdded);
									recordOfFileToBeAdded = null; // used
								}
							} else {
								// recordOfFileToBeAdded was *not* empty ...
								
								// save last recordOfFileToBeAdded so insert it right now ...
								tableOfFileReturn.add(recordOfFileToBeAdded);
								// create recordOfFileToBeAdded ...
								recordOfFileToBeAdded = new RecordOf();
								// fill recordOfFileToBeAdded with sCurrentLine parse fields ...
								fillRecordOfFile(recordOfFileToBeAdded, sCurrentRecordType, sCurrentLine, nRow);
								
								// Can't this record be parent of another segment?
								if (tableOfConfigLayoutRecSegment.getRecordOfBy("RecordTypeSegmentParent", sCurrentRecordType)==null) {
									// this record can't be parent of another segment so insert it right now ...
									tableOfFileReturn.add(recordOfFileToBeAdded);
									recordOfFileToBeAdded = null; // used
								}
							}
						}
					}
				}
				
				// Set LastRecordType for next turn ...
				if (sCurrentRecordType==null) {
					sLastRecordType = new String("");
				} else { 
					sLastRecordType = new String(sCurrentRecordType);
				}
				
				// Next line ...
				nRow++;
			}
			
			// add RecordOfToBeAdded ... 
			if (recordOfFileToBeAdded!=null) 
				tableOfFileReturn.add(recordOfFileToBeAdded);
			
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return tableOfFileReturn;
		
	}


	/**
	 *  getRecordType(sLine) - Get RecordType field from line
	 *  
	 *  @param sLine
	 */
	private String getRecordType(String sLine) throws Exception {
		
		for (int i=0;i<this.tableOfConfigLayoutRecField.size();i++) {
			RecordOf recordOf = tableOfConfigLayoutRecField.get(i);
			if (recordOf!=null){
				String recordType = recordOf.get("RecordType");
				String recordField = recordOf.get("RecordField");
				String recordFieldStart = recordOf.get("RecordFieldStart");
				String recordFieldEnd = recordOf.get("RecordFieldEnd");
				int nRecordFieldStart;
				int nRecordFieldEnd;
				
				if (recordType!=null && recordField!=null && sLine!=null) {
					if (!recordType.equals("") && !recordField.equals("")){
						
						// checking RecordFieldStart ...
						if (recordFieldStart==null) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", "IS NULL"));
						} else if (recordFieldStart.equals("")) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart));
						} else {
							try {
								nRecordFieldStart = Integer.parseInt(recordFieldStart);
							} catch (Exception e) {
								throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart + " - e: " + e.toString()));
							}
						}
						
						// checking RecordFieldEnd ...
						if (recordFieldEnd==null) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", "IS NULL"));
						} else if (recordFieldEnd.equals("")) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldEnd));
						} else {
							try {
								nRecordFieldEnd = Integer.parseInt(recordFieldEnd);
							} catch (Exception e) {
								throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart + " - e: " + e.toString()));
							}
						}
						
						// Checking if line is instance of this RecordType ...
						if (sLine.length() > nRecordFieldStart && sLine.length() >= nRecordFieldEnd ) {
							if ( recordType.equals(sLine.substring(nRecordFieldStart-1,nRecordFieldEnd))) {
								return recordType;
							}
						}
						
					}
				}				
			}
		}		
		return null;
	}
	
	/*
	 * fillRecordOfFile(sCurrentRecordType,sCurrentLine)
	 */
	private void fillRecordOfFile(RecordOf recordOfFill, String sCurrentRecordType, String sCurrentLine, int nRow) throws Exception {
		if (recordOfFill!= null) {
			for (int i=0;i<this.tableOfConfigLayoutRecField.size();i++) {
				RecordOf recordOf = tableOfConfigLayoutRecField.get(i);
				if (recordOf!=null){
					String recordType = recordOf.get("RecordType");
					if (recordType.equals(sCurrentRecordType)) {
						String recordField = recordOf.get("RecordField");
						String recordFieldStart = recordOf.get("RecordFieldStart");
						String recordFieldEnd = recordOf.get("RecordFieldEnd");
						int nRecordFieldStart;
						int nRecordFieldEnd;
						try {
							nRecordFieldStart = Integer.parseInt(recordFieldStart);
						} catch (Exception e) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s","").replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart + " - e: " + e.toString()));
						}
						try {
							nRecordFieldEnd = Integer.parseInt(recordFieldEnd);
						} catch (Exception e) {
							throw new Exception(MSG_TXT_ERROR_CONFIG_LAYOUT_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart + " - e: " + e.toString()));
						}
						try {
							String value = sCurrentLine.substring(nRecordFieldStart-1,nRecordFieldEnd);	
							recordOfFill.set(recordType + "." + recordField, value);
						} catch (Exception e) {
							throw new Exception(MSG_TXT_ERROR_FILE_DATA_INVALID.replaceFirst("%s",this.fileName).replaceFirst("%s", "tableOfConfigLayoutRecField").replaceFirst("%s", recordType).replaceFirst("%s", recordFieldStart + " - e: " + e.toString()));
						}
					}
				}
			}
			recordOfFill.set(sCurrentRecordType + "." + "RowNum", Integer.toString(nRow));
		}
	}
	
}
