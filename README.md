# MedStock — Sistema de Controle de Estoque de Medicamentos

**AEP 2026.2 — Segunda Entrega**  
**Curso:** Engenharia de Software — 6º semestre  
**Instituição:** UniCesumar

## Sobre o projeto

O MedStock é uma aplicação desenvolvida para auxiliar no controle de estoque de medicamentos. O sistema permite cadastrar e consultar medicamentos, atualizar informações, registrar entradas e saídas e acompanhar situações que precisam de atenção, como estoque baixo e vencimento próximo.

A proposta é facilitar a organização dos medicamentos e de seus lotes, melhorar o acompanhamento das movimentações e contribuir para uma gestão de estoque mais eficiente.

Esta versão corresponde à segunda entrega da AEP 2026.2 e apresenta a evolução do sistema, a organização do código, os testes automatizados, a análise de cobertura e a documentação necessária para compreender e executar o projeto.

## Objetivos

- Organizar o cadastro e a consulta de medicamentos.
- Controlar entradas e saídas de estoque.
- Acompanhar as quantidades disponíveis.
- Organizar os lotes e suas respectivas validades.
- Identificar medicamentos com estoque baixo.
- Consultar medicamentos com vencimento próximo.
- Aplicar conceitos de orientação a objetos.
- Utilizar um banco de dados NoSQL com documentos e estruturas aninhadas.
- Verificar o funcionamento do sistema por meio de testes automatizados.

## Funcionalidades

O sistema possui as seguintes funcionalidades:

- Cadastro de medicamentos.
- Listagem de medicamentos cadastrados.
- Consulta de medicamento por identificador.
- Atualização dos dados de um medicamento.
- Exclusão de medicamentos.
- Registro de entrada de estoque.
- Registro de saída de estoque.
- Consulta de medicamentos com estoque baixo.
- Consulta de medicamentos com vencimento próximo.
- Organização de lotes dentro dos documentos de medicamentos.
- Registro das movimentações de estoque.

## Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem de programação |
| Spring Boot | Desenvolvimento da aplicação |
| Spring Web | Criação dos endpoints REST |
| Spring Data MongoDB | Integração com o banco de dados |
| MongoDB | Armazenamento dos dados |
| Maven | Gerenciamento de dependências e execução dos testes |
| JUnit | Testes automatizados |
| Mockito | Simulação de dependências nos testes |
| JaCoCo | Análise da cobertura dos testes |
| Swagger / OpenAPI | Documentação e consulta da API |
| Git e GitHub | Versionamento e hospedagem do código |

## Arquitetura do projeto

O código está organizado em pacotes que separam as responsabilidades da aplicação, facilitando a manutenção, a leitura e a realização de testes.

```text
src/
└── main/
    ├── java/
    │   └── com/aep/medstock/
    │       ├── controller/
    │       ├── dto/
    │       ├── exception/
    │       ├── mapper/
    │       ├── model/
    │       ├── repository/
    │       └── service/
    └── resources/
        └── application.yml
```

As principais responsabilidades são:

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints da API.
- **DTO:** organiza os dados utilizados na comunicação com a API.
- **Model:** representa os documentos e objetos utilizados pela aplicação.
- **Repository:** realiza o acesso aos dados armazenados no MongoDB.
- **Service:** concentra as regras de negócio e o processamento das operações.
- **Mapper:** auxilia na conversão entre objetos.
- **Exception:** reúne classes relacionadas ao tratamento de exceções.

Essa organização contribui para separar as responsabilidades e manter o código mais fácil de compreender e evoluir.

## Banco de dados

O MedStock utiliza o MongoDB, um banco de dados NoSQL orientado a documentos. O banco utilizado pela aplicação é denominado `medstock` e possui duas coleções principais: `medicamentos` e `movimentacoes`.

### Coleção `medicamentos`

A coleção `medicamentos` armazena os dados dos medicamentos cadastrados. Cada documento possui os campos `nome`, `quantidade`, `quantidadeMinima`, `validade`, `lote`, `dataEntrada` e `lotes`, além do identificador do documento.

O campo `lotes` contém uma lista de objetos do tipo `LoteEstoque`. Cada lote possui um número, uma quantidade e uma data de validade. Dessa forma, um medicamento pode conter diferentes lotes dentro do mesmo documento.

Exemplo ilustrativo da estrutura:

```json
{
  "_id": "ID_DO_MEDICAMENTO",
  "nome": "Paracetamol",
  "quantidade": 100,
  "quantidadeMinima": 20,
  "validade": "2027-06-30",
  "lote": "LOTE001",
  "dataEntrada": "2026-10-20",
  "lotes": [
    {
      "numero": "LOTE001",
      "quantidade": 60,
      "validade": "2027-06-30"
    },
    {
      "numero": "LOTE002",
      "quantidade": 40,
      "validade": "2027-12-31"
    }
  ]
}
```

Os valores são exemplos para demonstrar a estrutura. O identificador é ilustrativo, e as datas seguem o formato `AAAA-MM-DD`, correspondente ao formato usual de `LocalDate`.

### Coleção `movimentacoes`

A coleção `movimentacoes` registra as movimentações de estoque realizadas no sistema. Cada documento possui um identificador, o campo `medicamentoId`, o tipo de movimentação, a quantidade e a data da operação.

Exemplo ilustrativo:

```json
{
  "_id": "ID_DA_MOVIMENTACAO",
  "medicamentoId": "ID_DO_MEDICAMENTO",
  "tipo": "ENTRADA",
  "quantidade": 20,
  "data": "2026-10-29T10:30:00"
}
```

O campo `medicamentoId` representa a referência ao medicamento relacionado à movimentação. O campo `data` utiliza o formato de data e hora correspondente ao tipo `LocalDateTime`.

### Relacionamento e modelagem NoSQL

O relacionamento entre as coleções é realizado por referência. O campo `medicamentoId`, presente em `movimentacoes`, identifica o medicamento relacionado à operação.

Além disso, a coleção `medicamentos` possui uma lista de lotes aninhada no documento principal. Essa estrutura utiliza subdocumentos para organizar informações relacionadas sem exigir uma coleção separada para cada lote.

A modelagem contempla múltiplas coleções, relacionamento entre documentos e documentos complexos com listas de subdocumentos, conforme os requisitos de NoSQL da atividade.

## Requisitos para execução

Para executar o projeto localmente, é necessário ter:

- Java 17 instalado.
- MongoDB instalado ou disponível em um serviço acessível pela aplicação.
- Git instalado, caso deseje clonar o repositório.
- Maven Wrapper, incluído no projeto.

O MongoDB precisa estar em execução e acessível pelo endereço configurado na aplicação.

## Como obter o projeto

Clone o repositório:

```bash
git clone https://github.com/moraeseduardaa/Medstock.git
```

Entre na pasta do projeto:

```bash
cd Medstock
```

Caso o projeto já esteja em sua máquina, abra a pasta raiz no VS Code ou na IDE de sua preferência.

## Configuração do banco de dados

A configuração da conexão com o MongoDB está no arquivo:

```text
src/main/resources/application.yml
```

A aplicação está configurada para utilizar a porta `8080` e se conectar ao MongoDB por meio do endereço definido nesse arquivo.

Antes de executar o sistema, verifique se o banco está em execução e se o endereço, o usuário, a senha e o banco de dados correspondem ao ambiente utilizado.

**Importante:** não publique senhas reais ou credenciais de produção no GitHub. Antes de disponibilizar o projeto publicamente, utilize variáveis de ambiente ou outra forma segura de configuração e remova credenciais que não devam ser compartilhadas.

## Como executar a aplicação

No Windows, abra o PowerShell na raiz do projeto e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde a inicialização. Se tudo estiver configurado corretamente, a aplicação ficará disponível na porta `8080`.

## Documentação da API

Com a aplicação em execução, a documentação interativa pode ser acessada pelo Swagger UI:

**Swagger UI:** http://localhost:8080/docs

A especificação OpenAPI pode ser consultada em:

**OpenAPI JSON:** http://localhost:8080/v3/api-docs

Esses endereços correspondem à configuração identificada no projeto e ao caminho padrão do Springdoc para a especificação OpenAPI, desde que não exista outra personalização.

## Endpoints disponíveis

Os mapeamentos abaixo foram conferidos no arquivo `MedicamentoController.java`.

| Método HTTP | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/medicamentos` | Cadastra um medicamento |
| `GET` | `/api/medicamentos` | Lista os medicamentos |
| `GET` | `/api/medicamentos/{id}` | Consulta um medicamento pelo identificador |
| `PUT` | `/api/medicamentos/{id}` | Atualiza os dados de um medicamento |
| `DELETE` | `/api/medicamentos/{id}` | Exclui um medicamento |
| `PATCH` | `/api/medicamentos/{id}/entrada` | Registra uma entrada de estoque |
| `PATCH` | `/api/medicamentos/{id}/saida` | Registra uma saída de estoque |
| `GET` | `/api/medicamentos/estoque-baixo` | Consulta medicamentos com estoque baixo |
| `GET` | `/api/medicamentos/vencimento-proximo` | Consulta medicamentos com vencimento próximo |

Os parâmetros, os campos obrigatórios e os formatos esperados para cada requisição podem ser consultados na documentação interativa do Swagger.

## Testes automatizados

O projeto utiliza testes automatizados para verificar o comportamento de seus componentes e ajudar a identificar falhas durante o desenvolvimento.

Os testes utilizam ferramentas como JUnit e Mockito, conforme a implementação presente no projeto.

Para executar os testes no Windows, utilize:

```powershell
.\mvnw.cmd clean test
```

Ao final da execução, verifique o resultado apresentado no terminal. A execução é considerada bem-sucedida quando o Maven apresenta `BUILD SUCCESS` e os testes não registram falhas.

## Cobertura de testes

A cobertura do código é analisada por meio do JaCoCo.

Na última execução do relatório consultado durante a preparação desta entrega, foram registrados os seguintes resultados:

| Métrica | Resultado |
|---|----------:|
| Cobertura de instruções |       89% |
| Cobertura de ramificações (*branches*) |       70% |
| Instruções não cobertas | 71 de 683 |
| Ramificações não cobertas |  10 de 34 |
| Classes identificadas no relatório |         7 |

A cobertura de instruções de **89% supera o requisito mínimo de 70% informado para a entrega**.

A cobertura de ramificações é uma métrica diferente: ela verifica se os diferentes caminhos de decisão do código foram exercitados pelos testes. Por isso, seu percentual pode ser inferior ao de instruções.

O relatório HTML pode ser consultado localmente em:

```text
target/site/jacoco/index.html
```

Esse arquivo é gerado no ambiente de desenvolvimento e pode não estar disponível para quem clonar o repositório. Para conferir os números atualizados, execute os testes e gere novamente o relatório conforme a configuração do JaCoCo no projeto.

Os percentuais apresentados correspondem à execução consultada durante a preparação desta documentação e podem mudar conforme o código e os testes forem atualizados.

## Versionamento

O código-fonte é mantido no GitHub, utilizando Git para acompanhar as alterações e a evolução do projeto.

**Repositório:** https://github.com/moraeseduardaa/Medstock

Para identificar a versão correspondente à segunda entrega, a equipe deve registrar o commit final e, de preferência, criar uma tag ou release específica. O identificador da versão entregue deve ser comunicado conforme as orientações da AEP.

## Metodologia de desenvolvimento

A equipe pode organizar e acompanhar as atividades por meio de um quadro de tarefas, utilizando as etapas:

- A fazer.
- Em andamento.
- Em revisão.
- Concluído.

O quadro permite visualizar o progresso, distribuir responsabilidades e identificar atividades pendentes. Ele deve refletir as tarefas realmente realizadas pela equipe durante o desenvolvimento.

## Considerações finais

O MedStock reúne funcionalidades de controle de estoque de medicamentos, utiliza o MongoDB para armazenar os dados e aplica uma organização de código baseada na separação de responsabilidades.

Nesta segunda entrega da AEP, a implementação, os testes automatizados, a análise de cobertura e a documentação contribuem para demonstrar a evolução do sistema e facilitar sua instalação, execução e avaliação.

O projeto pode continuar evoluindo com a ampliação dos testes, o aprimoramento da documentação e a inclusão de novas funcionalidades de acordo com as necessidades identificadas pela equipe.