#language: pt
#Version: 1.0
#Encoding: iso-8859-1
Funcionalidade: Validação de arquivo de transações financeiras

  @Crmpro @TransacaoFinanceira @PlanilhaVsArquivo @CrmproTransacFinancPlanVsArq
  Cenario: Validação de transações financeira planilha vs arquivo
    Dado Planilha com informações de validação do teste de transações financeiras
           | planilha TestData validação                        |
           | .\src\main\resources\testdata\crmpro\TestData.xlsx |
    E Arquivo de transações financeiras
           | arquivo Transações Financeiras                        |
           | .\src\main\resources\testdata\crmpro\TestDataFile.txt |
    E Planilha de configuração do layout de transações financeiras
           | planilha ConfigLayoutRecordType                                  | planilha ConfigLayoutRecordField                                  | planilha ConfigLayoutRecordSegment                                  |
           | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordType.xlsx | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordField.xlsx | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordSegment.xlsx |
    E Planilha de configuração de credenciais de acesso aos sistemas
           | planilha ConfigCredencial                                  |
           | .\src\main\resources\testdata\crmpro\ConfigCredencial.xlsx |
    Quando Eu buscar no arquivo de transações financeiras a transação correspondente com as informações para validação
#    E Eu buscar no site CrmPro a transação correspondente com as informações para validação
#    E Eu buscar no site Redmine a transação correspondente com as informações para validação
    Entao Eu valido todas as informações de transações financeiras
