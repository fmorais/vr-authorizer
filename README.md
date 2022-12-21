# Teste de programação - VR Benefícios

### Decisões arquiteturais:

Estou utilizando o lombok para facilitar o código boilerplate como get e set
e facilitando a leitura e compreensão do mesmo.

Criei também uma estrutura de pacotes seguindo algumas boas práticas do clean-arch.
O pacote controller contem os controladores que por sua vez irão chamar as classes
que irão processar a lógica de negócio, para esse pacote eu o nomeei de domain
(que se refere ao dominio da informação), dentro dele encontraremos todos os
processos que esse serviço irá fazer, nesse desafio dividi em 3: creatcard, balance
e transaction.

Tentei fazer utilizando a menor quantidade de if possível utilizando as interfaces
Optional, mas alguns ainda teve que ficar.

Utilizei o ControllerAdivisor para tratar as exceptions que lançei durante algumas validações,
principalmente nas de saldo, cartão inválido e senha errada.

Utilizei também o design pattern Factory Method, para que através das própias classes de demínio/DTO
sejam convertido para o padrao que desejar, aqui tem alguns
exemplos: https://refactoring.guru/design-patterns/factory-method

Estou utilizando alguns conceitos do SOLID também, separei as responsabilidades dessa API dentro do pacote de dominio.

Decidi utilizar o banco MySQL, pois devido ao controle transacional que ele proporciona, conseguimos ter a garantia de
que
a transação salva foi de fato salva, e conseguimos validar a integridade das informações salvas.

Qualquer outra dúvida que tenham vamos marcar um bate papo pra gente discutir melhor as decisões que tomei.

### Alteração no docker-compose

Eu utilizo Mac com processador M1, e por não ter a imagem docker do MySQL na versão 5.4, alterei para a versão 8. Mas
acredito
que na 5.4 deve funcionar perfeitamente

### Testes unitários

Decidi utilizar o Mockito para realização dos testes, sem nenhuma razão específica, foi mais por familiaridade com a
ferramenta mesmo.