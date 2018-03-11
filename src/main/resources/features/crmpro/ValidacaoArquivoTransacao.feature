#language: pt
#Version: 1.0
#Encoding: iso-8859-1
Funcionalidade: Valida��o de arquivo de transa��es financeiras

  @Crmpro @TransacaoFinanceira @PlanilhaVsArquivo @CrmproTransacFinancPlanVsArq
  Cenario: Valida��o de transa��es financeira planilha vs arquivo
    Dado Planilha com informa��es de valida��o do teste de transa��es financeiras
           | planilha TestData                                  |
           | .\src\main\resources\testdata\crmpro\TestData.xlsx |
    E Arquivo de transa��es financeiras
           | arquivo TestData Transacoes Financeiras               |
           | .\src\main\resources\testdata\crmpro\TestDataFile.txt |
    E Planilha de configura��o do layout de transa��es financeiras
           | planilha ConfigLayout                                       |
           | .\src\main\resources\testdata\crmpro\ConfigLayout.xlsx |
    Quando Eu buscar no arquivo de transa��es financeiras a transa��o correspondente com as informa��es para valida��o
    Entao Eu valido as informa��es de transa��o finaceiras do arquivo
