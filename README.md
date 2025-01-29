# TechFlow

Esta é uma aplicação baseada em microsserviços desenvolvida para gerenciar o fluxo de compras da loja online TechFlow, desde a compra até o envio dos produtos. A solução utiliza o RabbitMQ para comunicação entre os microsserviços, com uma arquitetura baseada em mensagens assíncronas e escaláveis, garantindo alta disponibilidade e facilidade de manutenção.

## :desktop_computer: Tecnologias Utilizadas: 

* **Java 17**:  Linguagem de programação moderna e de alto desempenho. 
* **Spring Boot**: Framework para facilitar o desenvolvimento de aplicações Java baseadas em microserviços.
* **MySQL**: Banco de dados relacional utilizado para persistência dos dados.
* **RabbitMQ**: Sistema de mensageria assíncrona para comunicação entre os módulos.
* **Spring Data JPA**: Abstração para acesso ao banco de dados utilizando JPA.
* **Lombok**: Biblioteca que simplifica o código, gerando automaticamente getters, setters, construtores, etc.
* **Swagger**: Ferramenta para documentação e testes das APIs RESTful.
* **JUnit5 / Mockito**: Frameworks para testes automatizados e mocks.
* **JaCoCo**: Ferramenta para medição de cobertura de testes.
* **Trello**: Ferramenta de gerenciamento de tarefas utilizada para acompanhamento do progresso do projeto.
  🔗 [Acesse nosso Trello](https://trello.com/b/gB6npr6G/academia-accenture-equipe-8)

## :gear: Configuração:

## :computer_mouse: Passo a passo para execução: 
## :printer: Resumo dos serviços: 
A arquitetura do TechFlow é composta por microsserviços que colaboram de forma assíncrona para gerenciar o ciclo completo de uma compra online. Cada microsserviço é responsável por uma parte do processo, e a comunicação entre eles ocorre por meio de mensagens enviadas pelo RabbitMQ, garantindo uma execução eficiente. Abaixo estão os detalhes de cada serviço:

* **Microsserviço de Cliente**:
  * Gerencia as informações dos clientes, esse microsserviço recebe as requisições para cadastrar, atualizar, deletar ou consultar os dados dos clientes.
* **Microsserviço de Produto**:
  * Gerencia as informações sobre os produtos disponíveis para venda na loja
* **Microsserviço de Pedido**:
  * Gerencia a criação e atualização de pedidos.
* **Microsserviço de Pagamento**:
  * Simula o processamento de pagamento dos pedidos.
* **Microsserviço de Estoque**:
  * Gerencia o estoque dos produtos.
* **Microsserviço de Envio**:
  * Simula o envio dos produtos aos clientes.

## Configuração para IntelliJ IDEA (Lombok)

Para que o Lombok funcione corretamente no IntelliJ IDEA, é necessário ativar o processamento de anotações. Siga os passos abaixo:

1. Abra as configurações do IntelliJ IDEA:
   - No menu, clique em **File > Settings** (ou **Ctrl+Alt+S** no Windows/Linux, **Command+,** no macOS).
2. Navegue até **Build, Execution, Deployment > Compiler > Annotation Processors**.
3. Certifique-se de que as seguintes opções estão habilitadas:
   - **Enable annotation processing**
   - **Obtain processors from project classpath**
4. Clique em **Apply** e depois em **OK** para salvar as configurações.

## :keyboard: Fluxograma do sistema:

![Image](https://github.com/user-attachments/assets/3ca81140-993b-48be-bb83-42918037cc6c)

## :iphone: MER(Modelo Entidade - Relacionamento: 
O MER está disponível diretamente no repositório. Você pode acessá-lo [aqui](https://github.com/thiago-ribeiro1/TechFlow/blob/main/MER%20-%20PROJETO%20FINAL.mwb).

![Image](https://github.com/user-attachments/assets/b4739f71-ee7a-4e3c-b268-36c7edbbf662)

## :video_game: Dicionário de dados: 
O dicionário de dados completo está disponível em um link externo. Você pode consultá-lo [aqui](https://docs.google.com/document/d/1_woi1staEsMuYv_biN4-PxKxfLLe0EO2o6mUcsZBVeg/edit?usp=sharing).


## :headphones: Cobertura de testes: 
A cobertura de testes pode ser visualizada utilizando o JaCoCo. Ele gera um relatório que indica a porcentagem de código coberta pelos testes. Para visualizar o relatório de cobertura, basta seguir os seguintes passos:
1. Utilizando o Maven, execute o seguinte comando:
   `mvn clean test`
2. O relatório gerado pode ser encontrado no diretório "target/site/jacoco/index.html"
3. Abra o arquivo "index.html" em um navegador para visualizar o gráfico e as informações sobre a cobertura de testes.


## Colaboradores:

* [Thiago Ribeiro](https://github.com/thiago-ribeiro1)
* [Thayane Nunes](https://github.com/thayanenns)
* [Vagner Pessoa](https://github.com/Dev-Vagner)




**Data**: Janeiro de 2025.
