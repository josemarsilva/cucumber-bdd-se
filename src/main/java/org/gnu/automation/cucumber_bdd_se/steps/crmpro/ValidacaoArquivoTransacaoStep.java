package org.gnu.automation.cucumber_bdd_se.steps.crmpro;

import java.util.Map;

import org.gnu.automation.cucumber_bdd_se.functionalities.crmpro.ValidacaoArquivoTransacaoFunctionality;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Entao;

public class ValidacaoArquivoTransacaoStep {
	
	static ValidacaoArquivoTransacaoFunctionality validacaoArquivoTransacaoFunctionality = new ValidacaoArquivoTransacaoFunctionality();

	@Dado("^Planilha com informações de validação do teste de transações financeiras$")
	public boolean planilhaInfoValidacaoTransacaoFinanceira(DataTable testDataTransacaoFinanceiraValidacao) throws Throwable {
		System.out.println("Dado planilhaInfoValidacaoTransacaoFinanceira()");
		for (Map<String, String> map : testDataTransacaoFinanceiraValidacao.asMaps(String.class, String.class)) {
			validacaoArquivoTransacaoFunctionality.setPlanilhaTestDataValidacao(map.get("planilha TestData validação"));
		}
		return true;
	}

	@E("^Arquivo de transações financeiras$")
	public boolean arquivoTransacaoFinanceira(DataTable testDataArquivo) throws Throwable {
		System.out.println("E arquivoTransacaoFinanceira()");
		for (Map<String, String> map : testDataArquivo.asMaps(String.class, String.class)) {
			validacaoArquivoTransacaoFunctionality.setArquivoTransacoesFinanceiras(map.get("arquivo Transações Financeiras"));
		}
		return true;
	}


	@E("^Planilha de configuração do layout de transações financeiras$")
	public boolean planilhaConfigLayoutTransacaoFinanceira(DataTable configPlanLayout) throws Throwable {
		System.out.println("E planilhaConfigLayoutTransacaoFinanceira()");
		for (Map<String, String> map : configPlanLayout.asMaps(String.class, String.class)) {
			validacaoArquivoTransacaoFunctionality.setPlanilhaConfigLayoutTransacoesFinanceiras(
					map.get("planilha ConfigLayoutRecordType"),
					map.get("planilha ConfigLayoutRecordField"),
					map.get("planilha ConfigLayoutRecordSegment")
					);
		}
		return true;
	}

	@Quando("^Eu buscar no arquivo de transações financeiras a transação correspondente com as informações para validação$")
	public boolean euBuscarArquivoTransacaoFinanceiraCorrespondente() throws Throwable {
		System.out.println("Quando euBuscarArquivoTransacaoFinanceiraCorrespondente()");
		validacaoArquivoTransacaoFunctionality.buscarTransacoesArquivo();
		return true;
	}
	
	@Entao("^Eu valido as informações de transação finaceiras do arquivo$")
	public boolean euValidoInfoTransacaoFinanceiraArquivo() throws Throwable {
		System.out.println("Entao euValidoInfoTransacaoFinanceiraArquivo()");
		return true;
	}
	
}
