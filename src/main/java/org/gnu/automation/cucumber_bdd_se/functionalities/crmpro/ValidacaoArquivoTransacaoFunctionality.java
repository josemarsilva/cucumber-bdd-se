package org.gnu.automation.cucumber_bdd_se.functionalities.crmpro;

import org.gnu.automation.cucumber_bdd_se.util.FileTable;
import org.gnu.automation.cucumber_bdd_se.util.RecordOf;
import org.gnu.automation.cucumber_bdd_se.util.TableOf;
import org.gnu.automation.cucumber_bdd_se.util.WebDriverFactory;
import org.gnu.automation.cucumber_bdd_se.util.WorkbookSheetTable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ValidacaoArquivoTransacaoFunctionality {
	
	/*
	 * Private Properties ... 
	 */
	private String planilhaTestDataValidacao = null;
	private String arquivoTransacoesFinanceiras = null;
	private String planilhaConfigLayoutRecordType = null;
	private String planilhaConfigLayoutRecordField = null;
	private String planilhaConfigLayoutRecordSegment = null;
	private String planilhaConfigCredencial = null;
	
	private TableOf tableOfTestDataValidacao = null;
	private TableOf tableOfConfigLayoutRecType = new TableOf();
	private TableOf tableOfConfigLayoutRecField = new TableOf();
	private TableOf tableOfConfigLayoutRecSegment = new TableOf();
	private TableOf tableOfArquivoTransacoesFinanceiras = null;
	private TableOf tableOfConfigCredencial = null;

	private WebDriver driver;
	
	JsonObject jsonObjCrossValidation = new JsonObject();
	
	/*
	 * Private Error messages ...
	 */
	private String ERRORMSG_OBJECT_UNDEFINED = new String("Error: Arquivo '%s' ainda não foi definido e não pode ser nulo!");
	private String ERRORMSG_OBJECT_ALREADY_DEFINED = new String("Error: Objeto '%s' já foi definido com o valor de '%s' e não pode ser redefinido. Funcionalidade não está preparada para tratar uma lista deste atributo!");
	private String ERRORMSG_FAIL_VALIDATION = new String("Error: Houve falhas na validação das transações financeiras!");
	
	
	
	/*
	 * ValidacaoArquivoTransacaoFunctionality() - Constructor
	 */
	public ValidacaoArquivoTransacaoFunctionality() {
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

	public void setPlanilhaConfigCredencialAcessoSistema(String param) throws Exception {
		if (this.planilhaConfigCredencial==null) {
			// Set property filename ...
			this.planilhaConfigCredencial = param;
			
			// Instance WorkbookSheetTable ...
	    	WorkbookSheetTable wbSheetTable = new WorkbookSheetTable(this.planilhaConfigCredencial);
	    	
	    	// Set property TableOf from WorkbookSheetTable ...
	    	this.tableOfConfigCredencial = wbSheetTable.getTableOfWorkbookSheetTable();
	    	
		} else  {
			throw new Exception(ERRORMSG_OBJECT_ALREADY_DEFINED.replaceFirst("%s","planilhaTestDataValidacao").replaceFirst("%s",this.planilhaTestDataValidacao));
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
		
	}
	
	public void buscarSiteCrmProTransacaoCorrespondente() throws Exception {
		
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
		
		// WebPage navigation ...
		if (this.driver==null) {
			driver = WebDriverFactory.getIEDriver();
//			driver = WebDriverFactory.getChromeDriver();		
		}
		
		// mover para pageobjects
		driver.get("https://www.freecrm.com");
		driver.findElement(By.name("username")).sendKeys("naveenk");
		driver.findElement(By.name("password")).sendKeys("test@123");
		driver.findElement(By.name("password")).sendKeys(Keys.RETURN);
		
	}
	
	
	public void buscarSiteRedmineTransacaoCorrespondente() throws Exception {
		
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
		
		// WebPage navigation ...
		if (this.driver==null) {
			driver = WebDriverFactory.getIEDriver();
//			driver = WebDriverFactory.getChromeDriver();		
		}
		
		// mover para pageobjects
		driver.get("https://www.easyredmine.com/br/demonstracao-redmine");
		driver.findElement(By.id("jform_email")).sendKeys("teste@teste.com");
		driver.findElement(By.id("jform_email")).sendKeys(Keys.RETURN);
		
	}
	
	
	public void validarTodasInformacoesTransacoesFinanceiras() throws Throwable {
		
		tableOfTestDataValidacao.export("logs\\tableOfTestDataValidacao.csv");
		tableOfConfigLayoutRecType.export("logs\\tableOfConfigLayoutRecType.csv");
		tableOfConfigLayoutRecField.export("logs\\tableOfConfigLayoutRecField.csv");
		tableOfConfigLayoutRecSegment.export("logs\\tableOfConfigLayoutRecSegment.csv");
		tableOfArquivoTransacoesFinanceiras.export("logs\\tableOfArquivoTransacoesFinanceiras.csv");
		tableOfConfigCredencial.export("logs\\tableOfConfigCredencial.csv");
		
		// Cross validation ...
		buildCrossValidationJson();
		
		if (false) {
			throw new Exception(ERRORMSG_FAIL_VALIDATION);
			
		}
		
	}
	
	
	private void buildCrossValidationJson() throws Exception {
		
		// JsonArray TestData
		JsonArray jsonArrayTestData = new JsonArray();
		
		// Nested Loop on TestData ...
		
		for (int i=0;i<this.tableOfTestDataValidacao.size();i++) {
			
			// Get each recordOf from tableOfTestDataValidacao ...
			
			RecordOf recordOfTestData = tableOfTestDataValidacao.get(i);
			String cardScheme = ( (recordOfTestData.get("CardScheme")!=null) ? recordOfTestData.get("CardScheme") : new String("") );
			String merchant = ( (recordOfTestData.get("Merchant")!=null) ? recordOfTestData.get("Merchant") : new String("") );
			String productCode = ( (recordOfTestData.get("Product Code")!=null) ? recordOfTestData.get("Product Code") : new String("") );
			String amount = ( (recordOfTestData.get("Amount")!=null) ? recordOfTestData.get("Amount") : new String("") );
			String valueDate = ( (recordOfTestData.get("Value Date")!=null) ? recordOfTestData.get("Value Date") : new String("") );
			String installment = ( (recordOfTestData.get("Installment")!=null) ? recordOfTestData.get("Installment") : new String("") );
			String authorizationCode = ( (recordOfTestData.get("Authorization Code")!=null) ? recordOfTestData.get("Authorization Code") : new String("") );
			String terminalID = ( (recordOfTestData.get("Terminal ID")!=null) ? recordOfTestData.get("Terminal ID") : new String("") );
			String captureMethod = ( (recordOfTestData.get("Capture Method")!=null) ? recordOfTestData.get("Capture Method") : new String("") );
			String merchantName = ( (recordOfTestData.get("Merchant Name")!=null) ? recordOfTestData.get("Merchant Name") : new String("") );
			
			// JsonObject TestData
			JsonObject jsonObjTestData = new JsonObject();
			jsonObjTestData.addProperty("cardScheme ", cardScheme );
			jsonObjTestData.addProperty("merchant ", merchant );
			jsonObjTestData.addProperty("productCode ", productCode );
			jsonObjTestData.addProperty("amount ", amount );
			jsonObjTestData.addProperty("valueDate ", valueDate );
			jsonObjTestData.addProperty("installment ", installment );
			jsonObjTestData.addProperty("authorizationCode ", authorizationCode );
			jsonObjTestData.addProperty("terminalID ", terminalID );
			jsonObjTestData.addProperty("captureMethod ", captureMethod );
			jsonObjTestData.addProperty("merchantName ", merchantName );
			
			// JsonArray TestData->Arquivo
			JsonArray jsonArrayArquivo = new JsonArray();
			
			for (int j=0;j<this.tableOfArquivoTransacoesFinanceiras.size();j++) {
				
				// Get each recordOf from tableOfArquivoTransacoesFinanceiras ...
				
				RecordOf recordOfArquivo = tableOfArquivoTransacoesFinanceiras.get(j);
				
//				String arq0500RecordType = getStringValue(recordOfTestData.get("0500.RecordType"));	
				String arq0500RecordType = ( (recordOfTestData.get("0500.RecordType")!=null) ? recordOfTestData.get("0500.RecordType") : new String("") );
				String arq0500DtTransac = ( (recordOfTestData.get("0500.DtTransac")!=null) ? recordOfTestData.get("0500.DtTransac") : new String("") );
				String arq0500AuthorizationCode = ( (recordOfTestData.get("0500.AuthorizationCode")!=null) ? recordOfTestData.get("0500.AuthorizationCode") : new String("") );
				String arq0500RowNum = ( (recordOfTestData.get("0500.RowNum")!=null) ? recordOfTestData.get("0500.RowNum") : new String("") );
				String arq0501RecordType = ( (recordOfTestData.get("0501.RecordType")!=null) ? recordOfTestData.get("0501.RecordType") : new String("") );
				String arq0501ProductCode = ( (recordOfTestData.get("0501.ProductCode")!=null) ? recordOfTestData.get("0501.ProductCode") : new String("") );
				String arq0501Merchant = ( (recordOfTestData.get("0501.Merchant")!=null) ? recordOfTestData.get("0501.Merchant") : new String("") );
				String arq0501TerminalId = ( (recordOfTestData.get("0501.TerminalId")!=null) ? recordOfTestData.get("0501.TerminalId") : new String("") );
				String arq0501RowNum = ( (recordOfTestData.get("0501.RowNum")!=null) ? recordOfTestData.get("0501.RowNum") : new String("") );
				String arq0502RecordType = ( (recordOfTestData.get("0502.RecordType")!=null) ? recordOfTestData.get("0502.RecordType") : new String("") );
				String arq0502Country = ( (recordOfTestData.get("0502.Country")!=null) ? recordOfTestData.get("0502.Country") : new String("") );
				String arq0502RowNum = ( (recordOfTestData.get("0502.RowNum")!=null) ? recordOfTestData.get("0502.RowNum") : new String("") );
				String arq0505RecordType = ( (recordOfTestData.get("0505.RecordType")!=null) ? recordOfTestData.get("0505.RecordType") : new String("") );
				String arq0505DecimalBigSequence = ( (recordOfTestData.get("0505.DecimalBigSequence")!=null) ? recordOfTestData.get("0505.DecimalBigSequence") : new String("") );
				String arq0505RowNum = ( (recordOfTestData.get("0505.RowNum")!=null) ? recordOfTestData.get("0505.RowNum") : new String("") );
				String arq0507RecordType = ( (recordOfTestData.get("0507.RecordType")!=null) ? recordOfTestData.get("0507.RecordType") : new String("") );
				String arq0507HexaBigSequence = ( (recordOfTestData.get("0507.HexaBigSequence")!=null) ? recordOfTestData.get("0507.HexaBigSequence") : new String("") );
				String arq0507RowNum = ( (recordOfTestData.get("0507.RowNum")!=null) ? recordOfTestData.get("0507.RowNum") : new String("") );
				
				// LEFT OUTER JOIN tableOfTestDataValidacao -> tableOfArquivoTransacoesFinanceiras
				
				if (merchant.equals(arq0501Merchant) && authorizationCode.equals(arq0500AuthorizationCode)) {
					
					// JsonObject Arquivo ...
					
					JsonObject jsonObjArquivo = new JsonObject();
					jsonObjTestData.addProperty("arq0500RecordType ", arq0500RecordType );
					jsonObjTestData.addProperty("arq0500DtTransac ", arq0500DtTransac );
					jsonObjTestData.addProperty("arq0500AuthorizationCode ", arq0500AuthorizationCode );
					jsonObjTestData.addProperty("arq0500RowNum ", arq0500RowNum );
					jsonObjTestData.addProperty("arq0501RecordType ", arq0501RecordType );
					jsonObjTestData.addProperty("arq0501ProductCode ", arq0501ProductCode );
					jsonObjTestData.addProperty("arq0501Merchant ", arq0501Merchant );
					jsonObjTestData.addProperty("arq0501TerminalId ", arq0501TerminalId );
					jsonObjTestData.addProperty("arq0501RowNum ", arq0501RowNum );
					jsonObjTestData.addProperty("arq0502RecordType ", arq0502RecordType );
					jsonObjTestData.addProperty("arq0502Country ", arq0502Country );
					jsonObjTestData.addProperty("arq0502RowNum ", arq0502RowNum );
					jsonObjTestData.addProperty("arq0505RecordType ", arq0505RecordType );
					jsonObjTestData.addProperty("arq0505DecimalBigSequence ", arq0505DecimalBigSequence );
					jsonObjTestData.addProperty("arq0505RowNum ", arq0505RowNum );
					jsonObjTestData.addProperty("arq0507RecordType ", arq0507RecordType );
					jsonObjTestData.addProperty("arq0507HexaBigSequence ", arq0507HexaBigSequence );
					jsonObjTestData.addProperty("arq0507RowNum ", arq0507RowNum );
					
					// Add JsonObject to JsonArray ... 
					jsonArrayArquivo.add(jsonObjArquivo);
				}
				
			}
			
			// Add JsonArray to parent ... 
			jsonObjTestData.add("arquivos", jsonArrayArquivo);
			
		}
				
	}
	
	private static String getStringValue(String value) {
		
		if (value == null) {
			return "";
		}
		
		return value;
	}



}
