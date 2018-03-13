package org.gnu.automation.cucumber_bdd_se.functionalities.crmpro;

import org.gnu.automation.cucumber_bdd_se.util.FileTable;
import org.gnu.automation.cucumber_bdd_se.util.TableOf;
import org.gnu.automation.cucumber_bdd_se.util.WebDriverFactory;
import org.gnu.automation.cucumber_bdd_se.util.WorkbookSheetTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ValidacaoArquivoTransacaoFunctionality {
	
	/*
	 * Private Properties ... 
	 */
	private String planilhaTestDataValidacao = null;
	private String arquivoTransacoesFinanceiras = null;
	private String planilhaConfigLayoutRecordType = null;
	private String planilhaConfigLayoutRecordField = null;
	private String planilhaConfigLayoutRecordSegment = null;
	
	private TableOf tableOfTestDataValidacao = null;
	private TableOf tableOfConfigLayoutRecType = new TableOf();
	private TableOf tableOfConfigLayoutRecField = new TableOf();
	private TableOf tableOfConfigLayoutRecSegment = new TableOf();
	private TableOf tableOfArquivoTransacoesFinanceiras = null;
//	private TableOf tableOfTableConfigCredencial = null;

	
	/*
	 * Private Error messages ...
	 */
	private String ERRORMSG_OBJECT_UNDEFINED = new String("Error: Arquivo '%s' ainda não foi definido e não pode ser nulo!");
	private String ERRORMSG_OBJECT_ALREADY_DEFINED = new String("Error: Objeto '%s' já foi definido com o valor de '%s' e não pode ser redefinido. Funcionalidade não está preparada para tratar uma lista deste atributo!");
	
	private WebDriver driver;
	
	
	/*
	 * ValidacaoArquivoTransacaoFunctionality() - Constructor
	 */
	public ValidacaoArquivoTransacaoFunctionality() {
//		driver = WebDriverFactory.getChromeDriver();		
//		driver = WebDriverFactory.getIEDriver();
//		driver.get("http://www.uol.com.br");
//		driver.findElement(By.name("q")).sendKeys("teste");
	}
	
	public void setPlanilhaTestDataValidacao(String param) throws Exception {
		if (this.planilhaTestDataValidacao==null) {
			// Set property filename ...
			this.planilhaTestDataValidacao = param;
			
			// Instance WorkbookSheetTable ...
	    	WorkbookSheetTable wbSheetTable = new WorkbookSheetTable(this.planilhaTestDataValidacao);
	    	
	    	// Set property TableOf from WorkbookSheetTable ...
	    	this.tableOfTestDataValidacao = wbSheetTable.getTableOfWorkbookSheetTable();
	    	
		} else  {
			throw new Exception(ERRORMSG_OBJECT_ALREADY_DEFINED.replaceFirst("%s","planilhaTestDataValidacao").replaceFirst("%s",this.planilhaTestDataValidacao));
		}
	}
	
	public void setArquivoTransacoesFinanceiras(String param) throws Exception {
		if (this.arquivoTransacoesFinanceiras==null) {
			// Set property filename ...
			this.arquivoTransacoesFinanceiras = param;
			
		} else  {
			throw new Exception(ERRORMSG_OBJECT_ALREADY_DEFINED.replaceFirst("%s","arquivoTransacoesFinanceiras").replaceFirst("%s",this.arquivoTransacoesFinanceiras));
		}
	}
	
	public void setPlanilhaConfigLayoutTransacoesFinanceiras(String sPlanilhaConfigLayoutRecordType, String sPlanilhaConfigLayoutRecordField, String sPlanilhaConfigLayoutRecordSegment) throws Exception {
		if (this.planilhaConfigLayoutRecordType==null) {
			// Set property filename ...
			this.planilhaConfigLayoutRecordType = sPlanilhaConfigLayoutRecordType;
			this.planilhaConfigLayoutRecordField = sPlanilhaConfigLayoutRecordField;
			this.planilhaConfigLayoutRecordSegment = sPlanilhaConfigLayoutRecordSegment;
			
			// Instance WorkbookSheetTable ...
	    	WorkbookSheetTable wbSheetTableRecType = new WorkbookSheetTable(this.planilhaConfigLayoutRecordType);
	    	WorkbookSheetTable wbSheetTableRecField = new WorkbookSheetTable(this.planilhaConfigLayoutRecordField);
	    	WorkbookSheetTable wbSheetTableRecSegment = new WorkbookSheetTable(this.planilhaConfigLayoutRecordSegment);
	    	
	    	// Set property TableOf from WorkbookSheetTable ...
	    	this.tableOfConfigLayoutRecType = wbSheetTableRecType.getTableOfWorkbookSheetTable();
	    	this.tableOfConfigLayoutRecField = wbSheetTableRecField.getTableOfWorkbookSheetTable();
	    	this.tableOfConfigLayoutRecSegment = wbSheetTableRecSegment.getTableOfWorkbookSheetTable();

	    	
		} else  {
			throw new Exception(ERRORMSG_OBJECT_ALREADY_DEFINED.replaceFirst("%s","planilhaConfigLayoutTransacoesFinanceiras").replaceFirst("%s",this.planilhaConfigLayoutRecordType));
		}
	}

	public void buscarTransacoesArquivo() throws Throwable {
		
		// Check consistency ...
		if (this.planilhaTestDataValidacao==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","planilhaTestDataValidacao"));
		if (this.arquivoTransacoesFinanceiras==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","arquivoTransacoesFinanceiras"));
		if (this.planilhaConfigLayoutRecordType==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","planilhaConfigLayoutTransacoesFinanceiras"));
		if (this.tableOfTestDataValidacao==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","tableOfTestDataValidacao"));
		if (this.tableOfConfigLayoutRecType==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","tableOfConfigLayoutRecType"));
		if (this.tableOfConfigLayoutRecField==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","tableOfConfigLayoutRecField"));
		if (this.tableOfConfigLayoutRecSegment==null) 
			throw new Exception(ERRORMSG_OBJECT_UNDEFINED.replaceFirst("%s","tableOfConfigLayoutRecSegment"));
		
		// Convert a file to a TableOf ...
		FileTable fileTable = new FileTable(
				this.arquivoTransacoesFinanceiras, 
				this.tableOfConfigLayoutRecType, 
				this.tableOfConfigLayoutRecField, 
				this.tableOfConfigLayoutRecSegment );
		tableOfArquivoTransacoesFinanceiras = fileTable.getTableOfFile();
		
		// Cross validation ...
		crossValidationTestDataArquivo();
		

	}
	

	/**
	 * crossValidationTestDataArquivo() - Execute cross validation nested loop between:
	 * @throws Throwable 
	 * 		
	 */
	private void crossValidationTestDataArquivo() throws Throwable {
		
		tableOfTestDataValidacao.export("tmp_tableOfTestDataValidacao.csv");
		tableOfConfigLayoutRecType.export("tmp_tableOfConfigLayoutRecType.csv");
		tableOfConfigLayoutRecField.export("tmp_tableOfConfigLayoutRecField.csv");
		tableOfConfigLayoutRecSegment.export("tmp_tableOfConfigLayoutRecSegment.csv");
		tableOfArquivoTransacoesFinanceiras.export("tmp_tableOfArquivoTransacoesFinanceiras.csv");
		
	}

}
