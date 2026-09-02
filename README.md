# MedStock

## Sistema de Controle de Estoque de Medicamentos

Projeto desenvolvido para a **AEP do curso de Engenharia de Software — 6º semestre**

O MedStock é uma Prova de Conceito (PoC) desenvolvida para auxiliar no controle de estoque de medicamentos em Unidades Básicas de Saúde (UBS)

O sistema permite cadastrar medicamentos e acompanhar informações como quantidade disponível, quantidade mínima, lote e validade. Também é possível registrar entradas e saídas do estoque e consultar medicamentos que estão com estoque baixo ou próximos do vencimento.

## Problema

O controle de medicamentos em uma unidade de saúde precisa ser organizado para evitar problemas como falta de medicamentos, produtos próximos do vencimento e dificuldade para acompanhar as entradas e saídas

O MedStock foi pensado como uma solução simples para centralizar essas informações e facilitar o acompanhamento do estoque.

## ODS

O projeto está relacionado ao **ODS 3 — Saúde e Bem-Estar**, da Organização das Nações Unidas (ONU).

A proposta é utilizar a tecnologia para auxiliar na organização do estoque de medicamentos e contribuir para uma melhor gestão dos recursos utilizados na área da saúde.

## Tecnologias

* Java 17
* Spring Boot
* MongoDB
* Docker
* Testcontainers
* JUnit
* Mockito
* JaCoCo
* Swagger/ OpenAPI
* Maven
* Git e GitHub

## Estrutura do projeto

```text
src
├── main
   └── java
      └── com.aep.medstock
           ├── controller
           ├── dto
           ├── exception
           ├── mapper
           ├── model
           ├── repository
           └── service

└── test
    └── java
        └── com.aep.medstock
            ├── controller
            ├── exception
            ├── mapper
            └── service
```

## Banco de dados

Projeto utiliza o **MongoDB** como banco de dados NoSQL

Exemplo de medicamento armazenado:

```json
{
  "nome": "Paracetamol",
  "quantidade": 100,
  "quantidadeMinima": 20,
  "validade": "2027-12-31",
  "lote": "LOT-001",
  "dataEntrada": "2026-08-29"
}
```

## Como executar

### Pré-requisito

* Java 17
* Docker Desktop
* Git

### Executar a aplicação

Clone o projeto:

```text
git clone https://github.com/moraeseduardaa/Medstock.git
```

Entre na pasta:

```text
cd Medstock
```

Suba o banco de dados MongoDB via Docker:

```text
docker compose up -d
```

Execute a aplicação:

```text
.\mvnw spring-boot:run
```

A aplicação será executada em:

```text
http://localhost:8080
```

## Swagger

A documentação da API é acessada pelo Swagger:

```text
http://localhost:8080/docs
```

Para visualizar e testar os endpoints

## Testes

O Docker Desktop precisa estar aberto antes de rodar os testes.

O projeto possui testes automatizados utilizando **JUnit e Mockito**.

Execute:

```text
.\mvnw clean test
```

Resultado atual:

* 26 testes executados
* 0 falhas
* 0 erros
* 90% de cobertura

## Relatório de cobertura

O relatório de cobertura é gerado pelo **JaCoCo**.

Execute:

```text
.\mvnw clean test
```

Depois abra o arquivo:

```text
target/site/jacoco/index.html
```

### Disciplinas envolvidas

* Banco de Dados NoSQL
* Paradigmas de Linguagens de Programação
* Processo de Software
* Projeto, Implementação e Testes

### Integrantes

* Eduarda Pereira de Moraes
* Johan Gabriel da Silva dos Santos
