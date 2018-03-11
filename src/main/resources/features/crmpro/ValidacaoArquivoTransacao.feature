#language: pt
#Version: 1.0
#Encoding: iso-8859-1
Funcionalidade: Validação de arquivo de transações financeiras

  @Crmpro @TransacaoFinanceira @PlanilhaVsArquivo @CrmproTransacFinancPlanVsArq
  Cenario: Validação de transações financeira planilha vs arquivo
    Dado Planilha com informações de validação do teste de transações financeiras
           | planilha TestData                                  |
           | .\src\main\resources\testdata\crmpro\TestData.xlsx |
    E Arquivo de transações financeiras
           | arquivo TestData Transacoes Financeiras               |
           | .\src\main\resources\testdata\crmpro\TestDataFile.txt |
    E Planilha de configuração do layout de transações financeiras
           | planilha ConfigLayout                                       |
           | .\src\main\resources\testdata\crmpro\ConfigLayout.xlsx |
    Quando Eu buscar no arquivo de transações financeiras a transação correspondente com as informações para validação
    Entao Eu valido as informações de transação finaceiras do arquivo
