package org.gnu.automation.cucumber_bdd_se.steps.crmpro;

import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Entao;

public class ValidacaoArquivoTransacaoStep {

	@Dado("^Planilha com informa��es de valida��o do teste de transa��es financeiras$")
	public boolean planilhaInfoValidacaoTransacaoFinanceira(DataTable testDataTransacaoFinanceiraValidacao) throws Throwable {
		System.out.println("planilhaInfoValidacaoTransacaoFinanceira()");
		for (Map<String, String> map : testDataTransacaoFinanceiraValidacao.asMaps(String.class, String.class)) {
		}
		return true;
	}

	@E("^Arquivo de transa��es financeiras$")
	public boolean arquivoTransacaoFinanceira(DataTable testDataArquivo) throws Throwable {
		System.out.println("arquivoTransacaoFinanceira()");
		for (Map<String, String> map : testDataArquivo.asMaps(String.class, String.class)) {
		}
		return true;
	}

	@E("^Planilha de configura��o do layout de transa��es financeiras$")
	public boolean planilhaConfigLayoutTransacaoFinanceira(DataTable configPlanLayout) throws Throwable {
		System.out.println("planilhaConfigLayoutTransacaoFinanceira()");
		for (Map<String, String> map : configPlanLayout.asMaps(String.class, String.class)) {
		}
		return true;
	}

	@Quando("^Eu buscar no arquivo de transa��es financeiras a transa��o correspondente com as informa��es para valida��o$")
	public boolean euBuscarArquivoTransacaoFinanceiraCorrespondente() throws Throwable {
		System.out.println("euBuscarArquivoTransacaoFinanceiraCorrespondente()");
		return true;
	}
	
	@Entao("^Eu valido as informa��es de transa��o finaceiras do arquivo$")
	public boolean euValidoInfoTransacaoFinanceiraArquivo() throws Throwable {
		System.out.println("euValidoInfoTransacaoFinanceiraArquivo()");
		return true;
	}
	
}
