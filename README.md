# MedStock — Sistema de Controle de Estoque de Medicamentos

**AEP 2026.2 — Segunda Entrega**  
**Curso:** Engenharia de Software — 6º semestre  
**Instituição:** UniCesumar

## Sobre o projeto

O MedStock é uma aplicação desenvolvida para auxiliar no controle de estoque de medicamentos. O sistema permite cadastrar e consultar medicamentos, atualizar informações, registrar entradas e saídas e acompanhar situações que precisam de atenção, como estoque baixo e vencimento próximo.

A proposta está relacionada ao **ODS 3 — Saúde e Bem-Estar**, contribuindo para uma gestão mais organizada dos medicamentos, auxiliando no acompanhamento das quantidades disponíveis e na prevenção de perdas por vencimento.

Esta versão corresponde à segunda entrega da AEP 2026.2 e apresenta a evolução do sistema, a organização do código, os testes automatizados, a análise de cobertura e a documentação necessária para compreender e executar o projeto.

## Objetivos

- Organizar o cadastro e a consulta de medicamentos.
- Controlar entradas e saídas de estoque.
- Acompanhar as quantidades disponíveis.
- Organizar os lotes e suas respectivas validades.
- Identificar medicamentos com estoque baixo.
- Consultar medicamentos com vencimento próximo.
- Aplicar conceitos de orientação a objetos.
- Verificar o funcionamento do sistema por meio de testes automatizados.

## Funcionalidades

- Cadastro de medicamentos.
- Listagem de medicamentos cadastrados.
- Consulta de medicamento por identificador.
- Atualização dos dados cadastrais.
- Exclusão de medicamentos.
- Registro de entrada de estoque.
- Registro de saída de estoque.
- Consulta de medicamentos com estoque baixo.
- Consulta de medicamentos com vencimento próximo.
- Organização de múltiplos lotes dentro dos documentos de medicamentos.
- Registro das movimentações de estoque.

## Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem de programação |
| Spring Boot 4.1.1 | Estrutura principal da aplicação |
| Spring Data MongoDB | Integração com o banco de dados |
| MongoDB 7 | Armazenamento dos documentos |
| JUnit | Testes automatizados |
| Mockito | Simulação de dependências nos testes |
| Testcontainers | Testes de integração com MongoDB em contêiner |
| JaCoCo 0.8.14 | Análise da cobertura dos testes |
| Swagger / OpenAPI | Documentação interativa da API |
| Git e GitHub | Versionamento e hospedagem do código |

## Arquitetura do projeto

O código está organizado em pacotes que separam as responsabilidades:

```text
src/
├── main/
│   ├── java/com/aep/medstock/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       └── application.yml
└── test/
    └── java/com/aep/medstock/
```

Principais responsabilidades:

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints da API.
- **DTO:** organiza os dados utilizados na comunicação com a API.
- **Model:** representa os documentos e objetos utilizados pela aplicação.
- **Repository:** realiza o acesso aos dados armazenados no MongoDB.
- **Service:** concentra as regras de negócio e o processamento das operações.
- **Mapper:** auxilia na conversão entre objetos.
- **Exception:** reúne classes relacionadas ao tratamento de exceções.

## Banco de dados NoSQL

O MedStock utiliza o MongoDB, um banco de dados NoSQL orientado a documentos. O banco utilizado pela aplicação é denominado `medstock` e possui duas coleções principais: `medicamentos` e `movimentacoes`.

### Coleção `medicamentos`

A coleção `medicamentos` armazena os dados dos medicamentos cadastrados. Cada documento possui campos como `nome`, `quantidade`, `quantidadeMinima`, `validade`, `lote`, `dataEntrada` e `lotes`, além do identificador do documento.

O campo `lotes` contém uma lista de objetos do tipo `LoteEstoque`. Cada lote possui número, quantidade e data de validade. Dessa forma, um medicamento pode conter diferentes lotes dentro do mesmo documento.

Exemplo:

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

### Coleção `movimentacoes`

A coleção `movimentacoes` registra as entradas e saídas realizadas no sistema. Cada documento possui um identificador, o campo `medicamentoId`, o tipo de movimentação, a quantidade e a data da operação.

Exemplo:

```json
{
  "_id": "ID_DA_MOVIMENTACAO",
  "medicamentoId": "ID_DO_MEDICAMENTO",
  "tipo": "ENTRADA",
  "quantidade": 20,
  "data": "2026-10-29T10:30:00"
}
```

### Relacionamento e modelagem NoSQL

O relacionamento entre as coleções é realizado por referência. O campo `medicamentoId`, presente em `movimentacoes`, identifica o medicamento relacionado à operação.

Além disso, a coleção `medicamentos` possui uma lista de lotes aninhada no documento principal. Essa estrutura utiliza subdocumentos para organizar informações relacionadas sem exigir uma coleção separada para cada lote.

A modelagem contempla múltiplas coleções, relacionamento entre documentos e documentos complexos com listas de subdocumentos, conforme os requisitos de NoSQL da atividade.

## Requisitos para execução

Para executar o projeto localmente, é necessário:

- Java 17 ou versão compatível.
- Docker Desktop com Docker Compose.
- Maven Wrapper, incluído no projeto.
- Git instalado, caso deseje clonar o repositório.

O Docker Desktop também deve estar em execução para rodar os testes de integração que utilizam Testcontainers.

## Como obter o projeto

Clone o repositório:

```bash
git clone https://github.com/moraeseduardaa/Medstock.git
```

Entre na pasta:

```bash
cd Medstock
```

## Configuração e execução

Os comandos abaixo devem ser executados no terminal, a partir da pasta raiz do projeto.

### 1. Iniciar o MongoDB

```powershell
docker compose up -d
```

Para conferir o contêiner:

```powershell
docker compose ps
```

### 2. Executar a aplicação

No Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

### 3. Acessar a documentação da API

Com a aplicação em execução:

- **Swagger UI:** http://localhost:8080/docs
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

### 4. Encerrar o MongoDB

```powershell
docker compose down
```

Esse comando encerra e remove o contêiner, preservando o volume de dados. Para remover também o volume e os dados locais, utilize `docker compose down -v` somente quando desejar apagar esses dados.

## Endpoints disponíveis

| Método HTTP | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/medicamentos` | Cadastra um medicamento |
| `GET` | `/api/medicamentos` | Lista os medicamentos |
| `GET` | `/api/medicamentos/{id}` | Consulta medicamento pelo identificador |
| `PUT` | `/api/medicamentos/{id}` | Atualiza nome e quantidade mínima |
| `DELETE` | `/api/medicamentos/{id}` | Exclui um medicamento |
| `PATCH` | `/api/medicamentos/{id}/entrada` | Registra entrada de estoque |
| `PATCH` | `/api/medicamentos/{id}/saida` | Registra saída de estoque |
| `GET` | `/api/medicamentos/estoque-baixo` | Consulta medicamentos com estoque baixo |
| `GET` | `/api/medicamentos/vencimento-proximo` | Consulta medicamentos com vencimento próximo |

### Cadastrar medicamento — `POST /api/medicamentos`

```json
{
  "nome": "Paracetamol",
  "quantidade": 100,
  "quantidadeMinima": 20,
  "validade": "2027-06-30",
  "lote": "LOTE001"
}
```

### Atualizar cadastro — `PUT /api/medicamentos/{id}`

```json
{
  "nome": "Paracetamol 500 mg",
  "quantidadeMinima": 25
}
```

### Registrar entrada — `PATCH /api/medicamentos/{id}/entrada`

```json
{
  "quantidade": 40,
  "lote": "LOTE002",
  "validade": "2027-12-31"
}
```

### Registrar saída — `PATCH /api/medicamentos/{id}/saida`

```json
{
  "quantidade": 20
}
```

## Testes automatizados

O projeto utiliza testes automatizados com JUnit e Mockito para verificar o comportamento dos componentes e das regras de negócio.

Os testes de integração utilizam Testcontainers para iniciar um MongoDB temporário durante a execução. Por isso, o Docker Desktop precisa estar ativo.

No Windows (PowerShell), execute na raiz do projeto:

```powershell
.\mvnw.cmd clean test
```

A execução é considerada bem-sucedida quando o Maven apresenta `BUILD SUCCESS` e os testes não registram falhas ou erros.

## Cobertura de testes

A cobertura do código é analisada por meio do JaCoCo 0.8.14.

No relatório consultado em 09/11/2026, foram registrados:

| Métrica | Resultado |
|---|---:|
| Cobertura de instruções | **90%** |
| Cobertura de ramificações (*branches*) | **76%** |
| Classes analisadas | 7 |
| Métodos analisados | 40 |

A cobertura geral de instruções supera o requisito mínimo de 70% estabelecido para a entrega.

O relatório HTML é gerado em:

```text
target/site/jacoco/index.html
```

Para consultar os resultados atualizados, execute os testes e abra o arquivo `index.html` no navegador.

## Metodologia de desenvolvimento

A equipe acompanha as atividades por meio de um quadro de tarefas inspirado no método Kanban, utilizando etapas para visualizar o andamento do trabalho, distribuir responsabilidades e identificar pendências.

**Quadro de tarefas:** https://trello.com/b/RkmcZVqC/medstock-aep-20262

O quadro deve refletir as atividades efetivamente realizadas pela equipe e seus respectivos responsáveis e status.

## Versionamento

O código-fonte é mantido no GitHub, utilizando Git para acompanhar as alterações e a evolução do projeto.

A versão final da segunda entrega será identificada por meio de uma tag Git, preservando o histórico de desenvolvimento.

## Considerações finais

O MedStock reúne funcionalidades de controle de estoque de medicamentos, utiliza o MongoDB para armazenar os dados e aplica uma organização de código baseada na separação de responsabilidades.

Nesta segunda entrega da AEP, a implementação, os testes automatizados, a análise de cobertura e a documentação demonstram a evolução do sistema e facilitam sua instalação, execução e avaliação.