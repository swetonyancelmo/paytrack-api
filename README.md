# PayTrack API

API REST para controle de finanças pessoais: cadastro de usuários, registro de rendas e despesas e consulta de resumo (saldo).

**Versão:** V1 (MVP)  
**Descrição:** API do PayTrack — backend para aplicação de controle financeiro.

---

## Índice

- [Visão geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Conceitos e tecnologias](#conceitos-e-tecnologias)
- [Stack tecnológico](#stack-tecnológico)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Modelo de dados](#modelo-de-dados)
- [API REST](#api-rest)
- [Configuração](#configuração)
- [Como executar](#como-executar)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Tratamento de erros](#tratamento-de-erros)
- [Testes](#testes)

---

## Visão geral

O PayTrack é uma API que permite:

- **Usuários:** criar e consultar por ID.
- **Rendas:** criar, listar por usuário e excluir.
- **Despesas:** criar, listar por usuário e excluir (com tipo: fixa ou variável).
- **Resumo:** obter totais de rendas, despesas e saldo por usuário.

A aplicação segue arquitetura em camadas, usa DTOs para entrada/saída, validação de requisições, mapeamento Entity ↔ DTO (MapStruct) e documentação OpenAPI (Swagger UI).  
Este repositório representa a **V1 (MVP)**: escopo mínimo para validar o produto e a stack.

---

## Arquitetura

O projeto adota **arquitetura em camadas** e **padrão REST**:

```
Cliente HTTP
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Controllers (REST)                                          │
│  - UserController, IncomeController, ExpenseController,      │
│    SummaryController                                         │
│  - Implementam interfaces *ControllerDocs (contrato + docs)  │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Services (regras de negócio)                                │
│  - UserService, IncomeService, ExpenseService, SummaryService│
│  - @Transactional, validações, orquestração                  │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Repositories (Spring Data JPA)                             │
│  - UserRepository, IncomeRepository, ExpenseRepository      │
│  - Acesso a dados, queries customizadas (JPQL)              │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Model (entidades JPA)                                       │
│  - User, Income, Expense                                    │
│  - Persistência em banco (H2 em memória no perfil dev)      │
└─────────────────────────────────────────────────────────────┘
```

**Fluxo de dados:**

- **Entrada:** JSON → DTO de request (record com Bean Validation) → Controller → Service.
- **Saída:** Entity → Mapper (MapStruct) → DTO de response (record) → JSON.
- **Exceções:** exceções de negócio (ex.: `ResourceNotFoundException`, `ExistingEmailException`) são tratadas pelo `GlobalExceptionHandler` e convertidas em respostas HTTP padronizadas.

**Conceitos aplicados:**

- **Inversão de dependência:** controllers e services dependem de abstrações (interfaces de repositório, mappers injetados).
- **Separação de responsabilidades:** cada camada tem um papel definido (apresentação, negócio, persistência).
- **DTOs:** request/response não expõem entidades JPA; reduz acoplamento e controla o contrato da API.
- **Documentação por contrato:** interfaces `*ControllerDocs` com anotações OpenAPI (Swagger) para descrição dos endpoints.

---

## Conceitos e tecnologias

| Conceito / prática        | Uso no projeto |
|---------------------------|----------------|
| **REST**                  | Recursos (`/api/users/v1`, `/api/incomes/v1`, etc.), verbos HTTP (GET, POST, DELETE), códigos de status (200, 201, 204, 400, 404). |
| **Bean Validation**       | Validação em DTOs de entrada (`@NotBlank`, `@Email`, `@Size`, `@NotNull`, `@Positive`, `@PastOrPresent`, `@FutureOrPresent`). |
| **JPA / Hibernate**       | Entidades, relacionamentos (`@OneToMany`, `@ManyToOne`), geração de schema (`ddl-auto: update`). |
| **Spring Data JPA**       | Repositórios com métodos derivados e `@Query` (JPQL) para somas e buscas por usuário. |
| **MapStruct**             | Mapeamento type-safe entre Entity e DTO (geração em tempo de compilação). |
| **OpenAPI 3 / Swagger**   | Documentação da API e UI interativa (SpringDoc). |
| **Controller Advice**     | Tratamento global de exceções e respostas de erro padronizadas. |
| **Profiles**              | Configuração por ambiente (`application.yml` + `application-dev.yml`). |
| **Records (Java)**        | DTOs imutáveis (request e response). |
| **Enums**                 | Tipo de despesa (`ExpenseType`: FIXA, VARIAVEL). |

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 4.0.2**
  - spring-boot-starter-webmvc
  - spring-boot-starter-data-jpa
  - spring-boot-starter-validation
  - spring-boot-h2console
  - spring-boot-devtools (opcional)
- **H2** (banco em memória no perfil `dev`)
- **MapStruct 1.5.5** (mapeamento Entity ↔ DTO)
- **SpringDoc OpenAPI 3.0.1** (Swagger UI compatível com Spring Boot 4)
- **Lombok** (redução de boilerplate em entidades)
- **Dozer** (presente no `pom.xml`; MapStruct é o mapper principal em uso)
- **Testes:** spring-boot-starter-webmvc-test, spring-boot-starter-data-jpa-test, spring-boot-starter-validation-test

---

## Estrutura do projeto

```
paytrack/
├── pom.xml
├── README.md
├── mvnw, .mvn/
└── src/
    ├── main/
    │   ├── java/com/swetonyancelmo/paytrack/
    │   │   ├── PaytrackApplication.java
    │   │   ├── config/
    │   │   │   └── OpenApiConfig.java           # Configuração OpenAPI (título, versão, licença)
    │   │   ├── controller/
    │   │   │   ├── UserController.java
    │   │   │   ├── IncomeController.java
    │   │   │   ├── ExpenseController.java
    │   │   │   ├── SummaryController.java
    │   │   │   └── docs/                         # Contratos + anotações Swagger
    │   │   │       ├── UserControllerDocs.java
    │   │   │       ├── IncomeControllerDocs.java
    │   │   │       ├── ExpenseControllerDocs.java
    │   │   │       └── SummaryControllerDocs.java
    │   │   ├── dtos/
    │   │   │   ├── request/
    │   │   │   │   ├── CreateUserDto.java
    │   │   │   │   ├── CreateIncomeDto.java
    │   │   │   │   └── CreateExpenseDto.java
    │   │   │   └── response/
    │   │   │       ├── UserDto.java
    │   │   │       ├── IncomeDto.java
    │   │   │       ├── ExpenseDto.java
    │   │   │       └── SummaryDto.java
    │   │   ├── exceptions/
    │   │   │   ├── GlobalExceptionHandler.java
    │   │   │   ├── ResourceNotFoundException.java
    │   │   │   └── ExistingEmailException.java
    │   │   ├── mapper/
    │   │   │   ├── UserMapper.java
    │   │   │   ├── IncomeMapper.java
    │   │   │   └── ExpenseMapper.java
    │   │   ├── model/
    │   │   │   ├── User.java
    │   │   │   ├── Income.java
    │   │   │   ├── Expense.java
    │   │   │   └── enums/
    │   │   │       └── ExpenseType.java
    │   │   ├── repository/
    │   │   │   ├── UserRepository.java
    │   │   │   ├── IncomeRepository.java
    │   │   │   └── ExpenseRepository.java
    │   │   └── service/
    │   │       ├── UserService.java
    │   │       ├── IncomeService.java
    │   │       ├── ExpenseService.java
    │   │       └── SummaryService.java
    │   └── resources/
    │       ├── application.yml                   # Profile ativo: dev
    │       └── application-dev.yml               # Datasource H2, JPA, H2 Console
    └── test/
        └── java/.../PaytrackApplicationTests.java
```

---

## Modelo de dados

### Entidades

- **User** (`users_tb`)
  - `id` (PK), `nome`, `email` (único), `senha`
  - Relacionamento: 1 usuário → N rendas, N despesas

- **Income** (`incomes_tb`)
  - `id` (PK), `descricao`, `valor`, `data`
  - `user_id` (FK) → User
  - Regra de negócio (validação no DTO): data da renda não pode ser futura (`@PastOrPresent`)

- **Expense** (`expenses_tb`)
  - `id` (PK), `descricao`, `valor`, `data`, `tipo` (enum)
  - `user_id` (FK) → User
  - **ExpenseType:** `FIXA` | `VARIAVEL`
  - Regra de negócio (validação no DTO): data da despesa planejada é hoje ou futura (`@FutureOrPresent`)

### Relacionamentos

- `User` 1 ──< `Income` (OneToMany, cascade ALL, orphanRemoval)
- `User` 1 ──< `Expense` (OneToMany, cascade ALL, orphanRemoval)
- `Income` e `Expense` ManyToOne → `User` (fetch LAZY)

---

## API REST

Base URL (local): `http://localhost:8080`

### Usuários — `/api/users/v1`

| Método | Endpoint        | Descrição                    |
|--------|-----------------|------------------------------|
| POST   | `/api/users/v1` | Criar usuário (body: nome, email, senha) |
| GET    | `/api/users/v1/{id}` | Buscar usuário por ID |

### Rendas — `/api/incomes/v1`

| Método | Endpoint        | Descrição                    |
|--------|-----------------|------------------------------|
| POST   | `/api/incomes/v1` | Criar renda (body: descricao, valor, data, userId) |
| GET    | `/api/incomes/v1?userId={id}` | Listar renda do usuário |
| DELETE | `/api/incomes/v1/{id}` | Excluir renda por ID |

### Despesas — `/api/expenses/v1`

| Método | Endpoint        | Descrição                    |
|--------|-----------------|------------------------------|
| POST   | `/api/expenses/v1` | Criar despesa (body: descricao, valor, data, tipo, userId) |
| GET    | `/api/expenses/v1?userId={id}` | Listar despesa do usuário |
| DELETE | `/api/expenses/v1/{id}` | Excluir despesa por ID |

### Resumo — `/api/summary/v1`

| Método | Endpoint        | Descrição                    |
|--------|-----------------|------------------------------|
| GET    | `/api/summary/v1?userId={id}` | Resumo: total de rendas, total de despesas, saldo (totalRendas - totalDespesas) |

**Exemplo de corpo de resposta do resumo (SummaryDto):**

```json
{
  "totalRendas": 5000.00,
  "totalDespesas": 3200.50,
  "saldoFinal": 1799.50
}
```

**Convenções:**  
- Request/response em JSON (`Content-Type: application/json`).  
- Códigos utilizados: 200 (OK), 201 (Created), 204 (No Content), 400 (Bad Request), 404 (Not Found).  
- Validações de entrada via Bean Validation nos DTOs de request.

---

## Configuração

- **Profile ativo:** definido em `application.yml` → `spring.profiles.active: dev`.
- **Perfil `dev`** (`application-dev.yml`):
  - **Datasource:** H2 em memória (`jdbc:h2:mem:developmentdb`), usuário `sa`, senha vazia.
  - **JPA:** dialect H2, `ddl-auto: update`, `show-sql: true`, `open-in-view: false`.
  - **H2 Console:** habilitada em `/h2-console` (acesso ao banco durante o desenvolvimento).

Para outros ambientes, basta criar `application-<profile>.yml` e ativar o profile correspondente.

---

## Como executar

**Pré-requisitos:** Java 21 e Maven (ou use o wrapper `./mvnw`).

```bash
# Clonar o repositório (se ainda não tiver)
git clone <url-do-repositorio>
cd paytrack

# Compilar e rodar
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

A API sobe em **http://localhost:8080**.  
O console H2 (perfil `dev`) fica em **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:developmentdb`).

---

## Documentação da API (Swagger)

Com a aplicação rodando:

- **Swagger UI:** http://localhost:8080/swagger-ui.html ou http://localhost:8080/swagger-ui/index.html  
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs  

A configuração customizada (título, versão, descrição, licença) está em `OpenApiConfig`.  
Os detalhes dos endpoints (resumos, códigos de resposta, exemplos) são definidos nas interfaces `*ControllerDocs`.

---

## Tratamento de erros

O `GlobalExceptionHandler` (`@ControllerAdvice`) centraliza o tratamento:

- **ResourceNotFoundException** → `404 Not Found` (ex.: usuário/renda/despesa não encontrado).
- **ExistingEmailException** → `400 Bad Request` (e-mail já cadastrado).

O corpo da resposta de erro inclui `timestamp`, `status`, `error` (reason phrase), `message` (mensagem da exceção), em formato consistente para o cliente.

---

## Testes

O projeto inclui dependências para testes (JUnit, Spring Boot Test, MockMvc, etc.) e a classe `PaytrackApplicationTests`. A suíte de testes pode ser ampliada para cobrir controllers, services e repositórios conforme a evolução do MVP.

```bash
./mvnw test
# ou
mvn test
```

---

## Licença e contato

- **Licença:** Apache 2.0 (conforme referência no OpenAPI).  
- **Termos de uso:** https://github.com/swetonyancelmo  
- **Contato / perfil:** https://www.linkedin.com/in/swetony-ancelmo/

---

*Documentação gerada para a V1 (MVP) do PayTrack API. O projeto está em evolução; novas funcionalidades (autenticação, mais filtros, relatórios, etc.) podem ser documentadas em versões futuras do README.*
