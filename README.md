# TechFlow

Esta é uma aplicação baseada em microsserviços desenvolvida para gerenciar o fluxo de compras da loja online TechFlow, desde a compra até o envio dos produtos. A solução utiliza o RabbitMQ para comunicação entre os microsserviços, com uma arquitetura baseada em mensagens assíncronas e escaláveis, garantindo alta disponibilidade e facilidade de manutenção.

## :desktop_computer: Tecnologias Utilizadas:

* **Java 17**:  Linguagem de programação moderna e de alto desempenho.
* **Spring Boot**: Framework para facilitar o desenvolvimento de aplicações Java baseadas em microsserviços.
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

### Dependências:
- Java 17;
- MySQL;

### Configuração do Lombok:

Para que o Lombok funcione corretamente, siga os passos abaixo:

- No Intellij IDEA:

  1. Abra as configurações do IntelliJ IDEA:
     - No menu, clique em **File > Settings** (ou **Ctrl+Alt+S** no Windows/Linux, **Command+,** no macOS).
  2. Navegue até **Build, Execution, Deployment > Compiler > Annotation Processors**.
  3. Certifique-se de que as seguintes opções estão habilitadas:
     - **Enable annotation processing**
     - **Obtain processors from project classpath**
  4. Clique em **Apply** e depois em **OK** para salvar as configurações.

- Outras IDE's: Caso seja necessário configurações extras para o correto funcionamento do Lombok, pesquise por documentações.

## :computer_mouse: Passo a passo para execução:

### 1. Banco de dados:
- **Crie o banco de dados**:
  - Gerando através do MER: [Acesse aqui](https://github.com/thiago-ribeiro1/TechFlow/blob/main/MER%20-%20PROJETO%20FINAL.mwb)
  - Gerando através do SQL Script: [Acesse aqui](https://github.com/thiago-ribeiro1/TechFlow/blob/main/TechFlow%20-%20SQL%20Script.sql)
- **Carga inicial**: Caso queira, adicione uma carga inicial de dados ao sistema com clientes, produtos e estoque através do SQL Carga Inicial: [Acesse aqui](https://github.com/thiago-ribeiro1/TechFlow/blob/main/TechFlow%20-%20SQL%20Carga%20Inicial.sql)

### 2. Execute todos os serviços e a API Gateway:

```bash 
mvn spring-boot:run
```
- Serviço de Cliente: `./cliente`
- Serviço de Produto: `./produto`
- Serviço de Pedido: `./pedido`
- Serviço de Estoque: `./estoque`
- Serviço de Pagamento: `./pagamento`
- Serviço de Envio: `./envio`
- API Gateway: `./api-gateway`

### 3. Swagger e Postman:

  #### Pelo Postman:
  - [Download do arquivo Postman Collection](https://github.com/thiago-ribeiro1/TechFlow/blob/main/TechFlow.postman_collection.json)
  - Importe no seu Postman para testar as requisições.
  
  #### Pelo Swagger:
  
  - Serviço de Cliente
  ```bash
  http://localhost:8080/swagger-ui/index.html
  ```
  - Serviço de Produto
  ```bash
  http://localhost:8081/swagger-ui/index.html
  ```
  - Serviço de Pedido
  ```bash
  http://localhost:8082/swagger-ui/index.html
  ```
  - Serviço de Estoque
  ```bash
  http://localhost:8083/swagger-ui/index.html
  ```


## :printer: Resumo dos serviços:

A arquitetura do TechFlow é composta por microsserviços que colaboram de forma assíncrona para gerenciar o ciclo completo de uma compra online. Cada microsserviço é responsável por uma parte do processo, e a comunicação entre eles ocorre por meio de mensagens enviadas pelo RabbitMQ, garantindo uma execução eficiente.

## Endpoints dos serviços:
Cada serviço possui endpoints específicos para suas respectivas funcionalidades. Segue alguns exemplos:

**Microsserviço de Cliente -** Pode ser chamado em *localhost:8080* ou *localhost:8086* (API Gateway):
- `POST api/clientes` - Cadastra um novo cliente.
  - Requisição:
    ```bash
    {
      "nome": "Cliente",
      "cpf": "12345678901"
    }
    ```
- `GET api/clientes` - Retorna todos os clientes.
- `GET api/clientes/{id}` - Retorna os dados de um cliente.
- `PUT api/clientes/{id}` - Atualiza os dados de um cliente.
  - Requisição:
    ```bash
    {
      "nome": "Cliente",
      "cpf": "12345678901"
    }
    ```
- `DELETE api/clientes/{id}` - Exclui um cliente.

**Microsserviço de Produto -** Pode ser chamado em *localhost:8081* ou *localhost:8086* (API Gateway):
- `POST api/produtos` - Adiciona um novo produto.
  - Requisição:
    ```bash
    {
        "nome": "Produto",
        "descricao": "Descrição do Produto",
        "valor": 150.00,
        "estoque": {
            "quantidade": 100
        }
    }
  
    ```
- `GET api/produtos` - Retorna todos os produtos.
- `GET api/produtos/{id}` - Retorna informações de um produto.
- `PUT api/produtos/{id}` - Atualiza um produto.
  - Requisição:
    ```bash
    {
        "nome": "Produto",
        "descricao": "Descrição do Produto",
        "valor": 150.00,
        "estoque": {
            "quantidade": 100
        }
    }
  
    ```
- `DELETE api/produtos/{id}` - Exclui um produto.

**Microsserviço de Pedido -** Pode ser chamado em *localhost:8082* ou *localhost:8086* (API Gateway):
- `POST api/pedidos` - Cria um novo pedido.
  - Requisição:
    ```bash
    {
        "itensPedido": [
            {
                "produtoId": 1,
                "quantidade": 5
            },
            {
                "produtoId": 2,
                "quantidade": 3
            }
        ],
        "observacao": "Observação opcional",
        "metodoPagamento": "PIX",
        "endereco": "Endereço",
        "clienteId": 1
    }
    ```
- `GET api/pedidos` - Retorna todos os pedidos.
- `GET api/pedidos/cliente/{clienteId}` - Retorna todos os pedidos de um cliente específico.
- `GET api/pedidos/{id}` - Retorna informações mais detalhadas de um pedido.

**Microsserviço de Estoque -** Pode ser chamado em *localhost:8083* ou *localhost:8086* (API Gateway):
- `POST api/estoque/validar` - Valida se todos os produtos do array tem estoque.
  - Requisição:
    ```bash
    [
        {
            "produtoId": 3,
            "quantidade": 5
        },
        {
            "produtoId": 4,
            "quantidade": 3
        }
    ]
    ```

## :keyboard: Fluxograma do sistema:

![Image](https://github.com/user-attachments/assets/3ca81140-993b-48be-bb83-42918037cc6c)

## :iphone: MER(Modelo Entidade - Relacionamento: 
O MER está disponível diretamente no repositório. Você pode acessá-lo [aqui](https://github.com/thiago-ribeiro1/TechFlow/blob/main/MER%20-%20PROJETO%20FINAL.mwb).

![Image](https://github.com/user-attachments/assets/b4739f71-ee7a-4e3c-b268-36c7edbbf662)

## :video_game: Dicionário de dados:
O dicionário de dados completo está disponível [aqui](https://docs.google.com/document/d/1_woi1staEsMuYv_biN4-PxKxfLLe0EO2o6mUcsZBVeg/edit?usp=sharing).

## :headphones: Cobertura de testes:
Para visualizar a cobertura de testes com JaCoCo:
1. Execute: `mvn clean test`
2. Acesse `target/site/jacoco/index.html`
3. Abra no navegador para visualizar os relatórios.

## Colaboradores:

* [Thiago Ribeiro](https://github.com/thiago-ribeiro1)
* [Thayane Nunes](https://github.com/thayanenns)
* [Vagner Pessoa](https://github.com/Dev-Vagner)

**Data**: Janeiro de 2025.
