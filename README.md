# Teste de programação - VR Benefícios

### Decisões arquiteturais:

Estou utilizando o lombok para facilitar o código boilerplate como get e set
e facilitando a leitura e compreensão do mesmo.

Criei também uma estrutura de pacotes seguindo algumas boas práticas do clean-arch.
O pacote controller contem os controladores que por sua vez irão chamar a classe
que irá processar a lógica de negócio, para esse pacote eu o nomei de domain
(que se refere ao dominio da informação), dentro dele encontraremos todos os
processos que esse serviço irá fazer, nesse desafio divi em 3: creatcard, balance
e transaction.

