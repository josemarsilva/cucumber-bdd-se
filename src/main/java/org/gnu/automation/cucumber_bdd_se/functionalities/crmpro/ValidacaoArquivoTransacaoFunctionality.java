package org.gnu.automation.cucumber_bdd_se.functionalities.crmpro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

	private WebDriver driver = null;
	
	JsonObject jsonObjCrossValidationData = new JsonObject(); // see specification on buildCrossValidationJson()
	JsonObject jsonObjCrossValidationReport = new JsonObject(); // see specification on jsonObjCrossValidationReport()
	
	/*
	 * Private Error messages ...
	 */
	private String ERRORMSG_OBJECT_UNDEFINED = new String("Error: Arquivo '%s' ainda não foi definido e não pode ser nulo!");
	private String ERRORMSG_OBJECT_ALREADY_DEFINED = new String("Error: Objeto '%s' já foi definido com o valor de '%s' e não pode ser redefinido. Funcionalidade não está preparada para tratar uma lista deste atributo!");
	private String ERRORMSG_FAIL_VALIDATION = new String("Error: Houve falhas na validação das transações financeiras! Consulte os logs da automação para verificar os detalhes");
	private String ERRORMSG_PASSED_SUCCESSFULLY = new String("Success: Validação ocorreu com sucesso!");
	private String ERRORMSG_FAIL_CROSS_VALIDATON_JSON = new String("Fail: Elemento de validação '%s' não foi encontrado no '%s'!");
	private String ERRORMSG_FAIL_CROSS_VALIDATION_EQUALS = new String("Fail: Falha na comparação do valor do campo '%s' entre '%s' e '%s'!");
	
	private String STATUS_PASSED = new String("Passed");
	private String STATUS_FAILED = new String("Failed");
	
	
	
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

		System.out.println("getParamPageObjects(): " + getParamPageObjects());
		
//		// WebPage navigation ...
//		if (this.driver==null) {
//			driver = WebDriverFactory.getIEDriver();
////			driver = WebDriverFactory.getChromeDriver();		
//		}
//		
//		// mover para pageobjects
//		driver.get("https://www.freecrm.com");
//		driver.findElement(By.name("username")).sendKeys("naveenk");
//		driver.findElement(By.name("password")).sendKeys("test@123");
//		driver.findElement(By.name("password")).sendKeys(Keys.RETURN);
		
	}
	
	
	private List<String> getParamPageObjects() throws Exception {
		List<String> strListReturn = new ArrayList<String>();
		
		for (int i=0;i<this.tableOfTestDataValidacao.size();i++) {
			// Get each recordOf from tableOfTestDataValidacao ...
			RecordOf recordOfTestData = tableOfTestDataValidacao.get(i);
			String merchant = ( (recordOfTestData.get("Merchant")!=null) ? recordOfTestData.get("Merchant") : new String("") );
			String valueDate = ( (recordOfTestData.get("Value Date")!=null) ? recordOfTestData.get("Value Date") : new String("") );
			valueDate = ( (valueDate.length()> 10) ) ? valueDate.substring(0, 10) : valueDate;
			strListReturn.add(merchant + ";" + valueDate);
		}
		return strListReturn;
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
		
		// Build Cross Validation Json ...
		buildCrossValidationJson();
		exportJsonToFile(this.jsonObjCrossValidationData, "logs\\jsonObjCrossValidationData.txt" );
		
		// Execute Cross Validation ...
		Boolean bRet = executeCrossValidation();
		exportJsonToFile(this.jsonObjCrossValidationReport, "logs\\jsonObjCrossValidationReport.txt" );
		
		// Report CrossValidation();
		reportCrossValidationExecution();

		if (!bRet) {
			throw new Exception(ERRORMSG_FAIL_VALIDATION);
			
		}
		
	}
	

	/*
	 * buildCrossValidationJson() - Build a Cross Validation JSON
	 * 
{
  "cross_validations":
  [
    {
      "testdata":
      {
        "productCode": "productCode",
        "amount": "amount",
        "valueDate": "valueDate",
        "installment": "installment",
        "authorizationCode": "authorizationCode",
        "terminalID": "terminalID",
        "captureMethod": "captureMethod",
        "merchantName": "merchantName"
      },
      "arquivo":
      {
        "arq0500RecordType": "arq0500RecordType",
        "arq0500DtTransac": "arq0500DtTransac",
        "arq0500AuthorizationCode": "arq0500AuthorizationCode",
        "arq0500RowNum": "arq0500RowNum",
        "arq0501RecordType": "arq0501RecordType",
        "arq0501ProductCode": "arq0501ProductCode",
        "arq0501Merchant": "arq0501Merchant",
        "arq0501TerminalId": "arq0501TerminalId",
        "arq0501RowNum": "arq0501RowNum",
        "arq0502RecordType": "arq0502RecordType",
        "arq0502Country": "arq0502Country",
        "arq0502RowNum": "arq0502RowNum",
        "arq0505RecordType": "arq0505RecordType",
        "arq0505DecimalBigSequence": "arq0505DecimalBigSequence",
        "arq0505RowNum": "arq0505RowNum",
        "arq0507RecordType": "arq0507RecordType",
        "arq0507HexaBigSequence": "arq0507HexaBigSequence",
        "arq0507RowNum": "arq0507RowNum"
      },
      "webapp":
      {
          "company_name": "company_name",
          "company_website": "http://wwww.company_name.com",
          "company_evidence": "YYYYMMDDHHMMSSSS.jpg"
      }
   }
  ]
}
	 * 
	 */
	private void buildCrossValidationJson() throws Exception {
		
		// JsonArray jsonArrayCrossValidations
		JsonArray jsonArrayCrossValidations = new JsonArray();
		
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
			String merchantWebSite = ( (recordOfTestData.get("Merchant Web Site")!=null) ? recordOfTestData.get("Merchant Web Site") : new String("") );
			
			// JsonObjects ...
			JsonObject jsonObjArrayItem = new JsonObject();
			JsonObject jsonObjTestData = new JsonObject();
			JsonObject jsonObjArquivo = new JsonObject();
			JsonObject jsonObjWebapp = new JsonObject();
			
			// Fill jsonObjTestData ...
			
			jsonObjTestData.addProperty("cardScheme", cardScheme );
			jsonObjTestData.addProperty("merchant", merchant );
			jsonObjTestData.addProperty("productCode", productCode );
			jsonObjTestData.addProperty("amount", amount );
			jsonObjTestData.addProperty("valueDate", valueDate );
			jsonObjTestData.addProperty("installment", installment );
			jsonObjTestData.addProperty("authorizationCode", authorizationCode );
			jsonObjTestData.addProperty("terminalID", terminalID );
			jsonObjTestData.addProperty("captureMethod", captureMethod );
			jsonObjTestData.addProperty("merchantName", merchantName );
			jsonObjTestData.addProperty("merchantWebSite", merchantWebSite );
			
			// Nested Loop Arquivo ...
			
			for (int j=0;j<this.tableOfArquivoTransacoesFinanceiras.size();j++) {
				
				// Get each recordOf from tableOfArquivoTransacoesFinanceiras ...
				
				RecordOf recordOfArquivo = tableOfArquivoTransacoesFinanceiras.get(j);
				String arq0500RecordType = ( (recordOfArquivo.get("0500.RecordType")!=null) ? recordOfArquivo.get("0500.RecordType") : new String("") ); // String arq0500RecordType = getStringValue(recordOfArquivo.get("0500.RecordType"));
				String arq0500DtTransac = ( (recordOfArquivo.get("0500.DtTransac")!=null) ? recordOfArquivo.get("0500.DtTransac") : new String("") );
				String arq0500AuthorizationCode = ( (recordOfArquivo.get("0500.AuthorizationCode")!=null) ? recordOfArquivo.get("0500.AuthorizationCode") : new String("") );
				String arq0500VlrVenda= ( (recordOfArquivo.get("0500.VlrVenda")!=null) ? recordOfArquivo.get("0500.VlrVenda") : new String("") );
				String arq0500RowNum = ( (recordOfArquivo.get("0500.RowNum")!=null) ? recordOfArquivo.get("0500.RowNum") : new String("") );
				String arq0501RecordType = ( (recordOfArquivo.get("0501.RecordType")!=null) ? recordOfArquivo.get("0501.RecordType") : new String("") );
				String arq0501ProductCode = ( (recordOfArquivo.get("0501.ProductCode")!=null) ? recordOfArquivo.get("0501.ProductCode") : new String("") );
				String arq0501Merchant = ( (recordOfArquivo.get("0501.Merchant")!=null) ? recordOfArquivo.get("0501.Merchant") : new String("") );
				String arq0501TerminalId = ( (recordOfArquivo.get("0501.TerminalId")!=null) ? recordOfArquivo.get("0501.TerminalId") : new String("") );
				String arq0501RowNum = ( (recordOfArquivo.get("0501.RowNum")!=null) ? recordOfArquivo.get("0501.RowNum") : new String("") );
				String arq0502RecordType = ( (recordOfArquivo.get("0502.RecordType")!=null) ? recordOfArquivo.get("0502.RecordType") : new String("") );
				String arq0502Country = ( (recordOfArquivo.get("0502.Country")!=null) ? recordOfArquivo.get("0502.Country") : new String("") );
				String arq0502RowNum = ( (recordOfArquivo.get("0502.RowNum")!=null) ? recordOfArquivo.get("0502.RowNum") : new String("") );
				String arq0505RecordType = ( (recordOfArquivo.get("0505.RecordType")!=null) ? recordOfArquivo.get("0505.RecordType") : new String("") );
				String arq0505DecimalBigSequence = ( (recordOfArquivo.get("0505.DecimalBigSequence")!=null) ? recordOfArquivo.get("0505.DecimalBigSequence") : new String("") );
				String arq0505RowNum = ( (recordOfArquivo.get("0505.RowNum")!=null) ? recordOfArquivo.get("0505.RowNum") : new String("") );
				String arq0507RecordType = ( (recordOfArquivo.get("0507.RecordType")!=null) ? recordOfArquivo.get("0507.RecordType") : new String("") );
				String arq0507HexaBigSequence = ( (recordOfArquivo.get("0507.HexaBigSequence")!=null) ? recordOfArquivo.get("0507.HexaBigSequence") : new String("") );
				String arq0507RowNum = ( (recordOfArquivo.get("0507.RowNum")!=null) ? recordOfArquivo.get("0507.RowNum") : new String("") );
				
				// LEFT OUTER JOIN tableOfTestDataValidacao -> tableOfArquivoTransacoesFinanceiras
				
				if (merchant.equals(arq0501Merchant) && authorizationCode.equals(arq0500AuthorizationCode)) {
					
					// Fill jsonObjArquivo ...
					
					jsonObjArquivo.addProperty("arq0500RecordType", arq0500RecordType );
					jsonObjArquivo.addProperty("arq0500DtTransac", arq0500DtTransac );
					jsonObjArquivo.addProperty("arq0500AuthorizationCode", arq0500AuthorizationCode );
					jsonObjArquivo.addProperty("arq0500VlrVenda", arq0500VlrVenda );
					jsonObjArquivo.addProperty("arq0500RowNum", arq0500RowNum );
					jsonObjArquivo.addProperty("arq0501RecordType", arq0501RecordType );
					jsonObjArquivo.addProperty("arq0501ProductCode", arq0501ProductCode );
					jsonObjArquivo.addProperty("arq0501Merchant", arq0501Merchant );
					jsonObjArquivo.addProperty("arq0501TerminalId", arq0501TerminalId );
					jsonObjArquivo.addProperty("arq0501RowNum", arq0501RowNum );
					jsonObjArquivo.addProperty("arq0502RecordType", arq0502RecordType );
					jsonObjArquivo.addProperty("arq0502Country", arq0502Country );
					jsonObjArquivo.addProperty("arq0502RowNum", arq0502RowNum );
					jsonObjArquivo.addProperty("arq0505RecordType", arq0505RecordType );
					jsonObjArquivo.addProperty("arq0505DecimalBigSequence", arq0505DecimalBigSequence );
					jsonObjArquivo.addProperty("arq0505RowNum", arq0505RowNum );
					jsonObjArquivo.addProperty("arq0507RecordType", arq0507RecordType );
					jsonObjArquivo.addProperty("arq0507HexaBigSequence", arq0507HexaBigSequence );
					jsonObjArquivo.addProperty("arq0507RowNum", arq0507RowNum );
					
					// TestData 1:(0,1) Arquivo
					break;
					
				}
			}
			
			// add { <jsonObjTestData>, <jsonObjArquivo>, <jsonObjWebapp> } to jsonObjArrayItem ...
			if (jsonObjTestData!=null) {
				if (jsonObjTestData.size()!=0) {
					// "testdata":
					jsonObjArrayItem.add("testdata", jsonObjTestData);
					// "arquivo":
					if (jsonObjArquivo!=null) {
						if (jsonObjArquivo.size()!=0) {
							jsonObjArrayItem.add("arquivo", jsonObjArquivo);
						}
					}
					// "webapp":
					if (jsonObjWebapp!=null) {
						if (jsonObjWebapp.size()!=0) {
							jsonObjArrayItem.add("webapp", jsonObjWebapp);
						}
					}
				}
			}
			
			// add jsonObjArrayItem to jsonArrayCrossValidations
			jsonArrayCrossValidations.add(jsonObjArrayItem);
			
		}
		
		// Fill jsonObjCrossValidation { "cross_validations": [ <jsonArrayCrossValidations> ] } ...
		this.jsonObjCrossValidationData.add("cross_validations", jsonArrayCrossValidations);
		
	}
	
	private static String getStringValue(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}
	
	/**
	 * exportJsonToFile(jsonObj,exportFileName) - Export content of jsonObj to file exportFileName
	 * 
	 * @param jsonObj  - Json object to be exported
	 * @param exportFileName  - filename where to place content
	 */
	private void exportJsonToFile(JsonObject jsonObj, String exportFileName) throws IOException {
		if (exportFileName!=null) {
			if (!exportFileName.equals("")) {
				File exportFile = new File(exportFileName);
				BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile));
				if (jsonObj!=null) {
					if (jsonObj.size()!=0) {
						String jsonString = jsonObj.toString();
						writer.write(jsonString);
					}
				}
				writer.close();
			}
		}		
	}

	private boolean executeCrossValidation()  throws Exception {
		
		Boolean bReturn = true;
		
		// { "cross_validations": [ <jsonArrayCrossValidations> ] }  
		JsonArray jsonArrayCrossValidations = this.jsonObjCrossValidationData.getAsJsonArray("cross_validations");
		
		if (jsonArrayCrossValidations!=null) {
			if (jsonArrayCrossValidations.size()!=0) {
				
				// Iterate <jsonObjArrayItem> into <jsoArrayCrossValidation>
				for (int i=0;i<jsonArrayCrossValidations.size();i++) {
					
					JsonObject jsonObjArrayItem = ( (JsonObject) jsonArrayCrossValidations.get(i) );
					
					Boolean bHasTestdata = jsonObjArrayItem.has("testdata");
					Boolean bHasArquivo = jsonObjArrayItem.has("arquivo");
					
					if (bHasTestdata) {
						
						String testdata_CardScheme = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("cardScheme").getAsString() );
						String testdata_merchant = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("merchant").getAsString() );
						String testdata_productCode = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("productCode").getAsString() );
						String testdata_amount = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("amount").getAsString() );
						String testdata_valueDate = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("valueDate").getAsString() );
						String testdata_installment = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("installment").getAsString() );
						String testdata_authorizationCode = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("authorizationCode").getAsString() );
						String testdata_terminalID = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("terminalID").getAsString() );
						String testdata_captureMethod = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("captureMethod").getAsString() );
						String testdata_merchantName = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("merchantName").getAsString() );
						String testdata_merchantWebSite = getStringValue( (jsonObjArrayItem.getAsJsonObject("testdata")).get("merchantWebSite").getAsString() );
						
						if (bHasArquivo) {
							
							String arquivo_arq0500RecordType = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0500RecordType").getAsString() );
							String arquivo_arq0500DtTransac = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0500DtTransac").getAsString() );
							String arquivo_arq0500AuthorizationCode = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0500AuthorizationCode").getAsString() );
							String arquivo_arq0500VlrVenda = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0500VlrVenda").getAsString() );
							String arquivo_arq0500RowNum = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0500RowNum").getAsString() );
							String arquivo_arq0501RecordType = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0501RecordType").getAsString() );
							String arquivo_arq0501ProductCode = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0501ProductCode").getAsString() );
							String arquivo_arq0501Merchant = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0501Merchant").getAsString() );
							String arquivo_arq0501TerminalId = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0501TerminalId").getAsString() );
							String arquivo_arq0501RowNum = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0501RowNum").getAsString() );
							String arquivo_arq0502RecordType = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0502RecordType").getAsString() );
							String arquivo_arq0502Country = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0502Country").getAsString() );
							String arquivo_arq0502RowNum = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0502RowNum").getAsString() );
							String arquivo_arq0505RecordType = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0505RecordType").getAsString() );
							String arquivo_arq0505DecimalBigSequence = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0505DecimalBigSequence").getAsString() );
							String arquivo_arq0505RowNum = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0505RowNum").getAsString() );
							String arquivo_arq0507RecordType = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0507RecordType").getAsString() );
							String arquivo_arq0507HexaBigSequence = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0507HexaBigSequence").getAsString() );
							String arquivo_arq0507RowNum = getStringValue( (jsonObjArrayItem.getAsJsonObject("arquivo")).get("arq0507RowNum").getAsString() );
							
							testdata_productCode = "000".concat(testdata_productCode).substring("000".concat(testdata_productCode).length()-3, "000".concat(testdata_productCode).length());
							testdata_valueDate = ((testdata_valueDate.substring(6, 10)).concat(testdata_valueDate.substring(3, 5)).concat(testdata_valueDate.substring(0, 2)) );
							double testdata_amountDouble = Double.parseDouble(testdata_amount); // number.replace(",",".")
							String testdata_amountFmt  = new DecimalFormat("#############").format(testdata_amountDouble*100);
							testdata_amountFmt = "000000000000".substring(0, 12 - testdata_amountFmt.length()).concat(testdata_amountFmt);
							
							if (!testdata_productCode.equals(arquivo_arq0501ProductCode)) {
								jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, 
										ERRORMSG_FAIL_CROSS_VALIDATION_EQUALS.replaceFirst("%s", "productCode").replaceFirst("%s", "planilha validação").replaceFirst("%s", "arquivo"), 
										"ProductCode: '%s' x '%s' (RecType: '%s', RowNum: %s)".replaceFirst("%s", testdata_productCode).replaceFirst("%s", arquivo_arq0501ProductCode).replaceFirst("%s", arquivo_arq0501RecordType).replaceFirst("%s", arquivo_arq0501RowNum));
								bReturn = false;
							} else if (!testdata_terminalID.equals(arquivo_arq0501TerminalId)) {
								jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, 
										ERRORMSG_FAIL_CROSS_VALIDATION_EQUALS.replaceFirst("%s", "terminalID").replaceFirst("%s", "planilha validação").replaceFirst("%s", "arquivo"), 
										"TerminalID: '%s' x '%s' (RecType: '%s', RowNum: %s)".replaceFirst("%s", testdata_terminalID).replaceFirst("%s", arquivo_arq0501TerminalId).replaceFirst("%s", arquivo_arq0501RecordType).replaceFirst("%s", arquivo_arq0501RowNum));
								bReturn = false;								
							} else if (!testdata_valueDate.equals(arquivo_arq0500DtTransac)) {
								jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, 
										ERRORMSG_FAIL_CROSS_VALIDATION_EQUALS.replaceFirst("%s", "valueDate").replaceFirst("%s", "planilha validação").replaceFirst("%s", "arquivo"), 
										"ValueDate: '%s' x '%s' (RecType: '%s', RowNum: %s)".replaceFirst("%s", testdata_valueDate).replaceFirst("%s", arquivo_arq0500DtTransac).replaceFirst("%s", arquivo_arq0500RecordType).replaceFirst("%s", arquivo_arq0500RowNum));
								bReturn = false;								
							} else if (!testdata_amountFmt.equals(arquivo_arq0500VlrVenda)) {
								jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, 
										ERRORMSG_FAIL_CROSS_VALIDATION_EQUALS.replaceFirst("%s", "amount").replaceFirst("%s", "planilha validação").replaceFirst("%s", "arquivo"), 
										"Amount: '%s' x '%s' (RecType: '%s', RowNum: %s)".replaceFirst("%s", testdata_amountFmt).replaceFirst("%s", arquivo_arq0500VlrVenda).replaceFirst("%s", arquivo_arq0500RecordType).replaceFirst("%s", arquivo_arq0500RowNum));
								bReturn = false;
								//
							} else if (testdata_captureMethod.equals("Chip") && !arquivo_arq0507RecordType.equals("0507")) {
								jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, 
										ERRORMSG_FAIL_CROSS_VALIDATON_JSON.replaceFirst("%s", "Capture Method").replaceFirst("%s", "planilha validação").replaceFirst("%s", "arquivo"), 
										"CaptureMethod: '%s' nao existe no arquivo abaixo da sequencia ('0500', .. , '0507') (RecType: '0500'., RowNum: %s)".replaceFirst("%s", testdata_captureMethod).replaceFirst("%s", arquivo_arq0500RowNum));
								bReturn = false;								
							} else {
								// Passed
								jsonObjCrossValidationReport(STATUS_PASSED, testdata_merchant, testdata_authorizationCode, "", "");
							}
							
							
						} else {
							jsonObjCrossValidationReport(STATUS_FAILED, testdata_merchant, testdata_authorizationCode, ERRORMSG_FAIL_CROSS_VALIDATON_JSON.replaceFirst("%s", "registro correspondente no arquivo").replaceFirst("%s", "arquivo"), "");
							bReturn = false;
						}
						
					} else {
						jsonObjCrossValidationReport(STATUS_FAILED, "", "", ERRORMSG_FAIL_CROSS_VALIDATON_JSON.replaceFirst("%s", "").replaceFirst("%s", "testdata"), "");
						bReturn = false;
					}
										
					
				}
				
				
				// { "testdata":{ <jsonObjTestData>, "arquivo":{ <jsonObjArquivo> } }  
			}
		}
		
		return bReturn;
		
	}
	
	/**
	 * jsonObjCrossValidationReport()
	 * @param status
	 * @param merchant
	 * @param authorizationCode
	 * @param summary
	 * @param detail
	 *
{  
   "CrossValidationReport":[  
      {  
         "Status":"Passed",
         "Merchant":"67700001",
         "Authorization Code":"Authorization Code",
         "Summary":"",
         "Detail":""
      },
      {  
         "Status":"Failed",
         "Merchant":"67700001",
         "Authorization Code":"674537",
         "Summary":"arquivoFail: Falha na comparação do valor do campo 'productCode' entre 'planilha validação' e '%s'!",
         "Detail":"ProductCode: '073' x '070' (RecType: '0501', RowNum: 8)"
      }
   ]
}	 * 
	 */
	private void jsonObjCrossValidationReport(String status, String merchant, String authorizationCode, String summary, String detail ) {
		JsonObject jsonObjCrossValidationReportItem = new JsonObject();
		
		jsonObjCrossValidationReportItem.addProperty("Status", status);
		jsonObjCrossValidationReportItem.addProperty("Merchant", merchant);
		jsonObjCrossValidationReportItem.addProperty("Authorization Code", authorizationCode);
		jsonObjCrossValidationReportItem.addProperty("Summary", summary);
		jsonObjCrossValidationReportItem.addProperty("Detail", detail);
		
		if (this.jsonObjCrossValidationReport!=null) {
			JsonArray jsonArrayCrossValidationReport = null;
			if (this.jsonObjCrossValidationReport.has("CrossValidationReport")) {
				jsonArrayCrossValidationReport = this.jsonObjCrossValidationReport.getAsJsonArray("CrossValidationReport");
			} else  {
				jsonArrayCrossValidationReport = new JsonArray();
				jsonObjCrossValidationReport.add("CrossValidationReport", jsonArrayCrossValidationReport);
			}
			jsonArrayCrossValidationReport.add(jsonObjCrossValidationReportItem);
		}
	}

	/**
	 * reportCrossValidationExecution()
	 */
	private void reportCrossValidationExecution() {
		// { "cross_validations": [ <jsonArrayCrossValidations> ] }  
		JsonArray jsonArrayCrossValidationReport = this.jsonObjCrossValidationReport.getAsJsonArray("CrossValidationReport");
		
		if (jsonArrayCrossValidationReport!=null) {
			if (jsonArrayCrossValidationReport.size()!=0) {
				
				// Iterate <jsonObjArrayItem> into <jsoArrayCrossValidation>
				System.out.println( "Status | Merchant | AuthCode | Summary");
				for (int i=0;i<jsonArrayCrossValidationReport.size();i++) {
					
					JsonObject jsonObjCrossValidationReportItem = ( (JsonObject) jsonArrayCrossValidationReport.get(i) );
					
					System.out.println(jsonObjCrossValidationReportItem.get("Status").getAsString() 
							+   " | " + jsonObjCrossValidationReportItem.get("Merchant").getAsString() 
							+   " | " + jsonObjCrossValidationReportItem.get("Authorization Code").getAsString() 
							+ "   | " + jsonObjCrossValidationReportItem.get("Summary").getAsString() 
							);
				}
			}
		}
	}


}
