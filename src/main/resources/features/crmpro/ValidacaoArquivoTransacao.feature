#language: pt
#Version: 1.0
#Encoding: iso-8859-1
Funcionalidade: Valida��o de arquivo de transa��es financeiras

  @TransacFinanc @TransacFinancArq @TransacFinancArqCrmpro @TransacFinancArqCrmpro
  Cenario: Valida��o de transa��es financeira planilha vs arquivo
    Dado Planilha com informa��es de valida��o do teste de transa��es financeiras
           | planilha TestData valida��o                                           |
           | .\src\main\resources\testdata\crmpro\TestData.xlsx                   |
    E Arquivo de transa��es financeiras
           | arquivo Transa��es Financeiras                        |
           | .\src\main\resources\testdata\crmpro\TestDataFile.txt |
#           | .\src\main\resources\testdata\firstdata\0075120C_20180215_T134425.txt |
    E Planilha de configura��o do layout de transa��es financeiras
           | planilha ConfigLayoutRecordType                                  | planilha ConfigLayoutRecordField                                  | planilha ConfigLayoutRecordSegment                                  |
           | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordType.xlsx | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordField.xlsx | .\src\main\resources\testdata\crmpro\ConfigLayoutRecordSegment.xlsx |
    E Planilha de configura��o de credenciais de acesso aos sistemas
           | planilha ConfigCredencial                                  |
           | .\src\main\resources\testdata\crmpro\ConfigCredencial.xlsx |
    Quando Eu buscar no arquivo de transa��es financeiras a transa��o correspondente com as informa��es para valida��o
#    E Eu buscar no site CrmPro a transa��o correspondente com as informa��es para valida��o
#    E Eu buscar no site Redmine a transa��o correspondente com as informa��es para valida��o
    Entao Eu valido todas as informa��es de transa��es financeiras
