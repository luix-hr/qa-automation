# QA Automation Suite — API + Web

Projeto de automação de testes desenvolvido como parte da disciplina de Automação de Testes. Cobre dois cenários: **automação de API** (Swagger Petstore) e **automação Web E2E** (SauceDemo), ambos com pipeline de CI integrada via GitHub Actions.

**Autor:** Luiz Henrique

---

## Sumário

- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Execução dos testes](#execução-dos-testes)
- [Pipeline de CI](#pipeline-de-ci)
- [Cenários cobertos](#cenários-cobertos)
- [Boas práticas aplicadas](#boas-práticas-aplicadas)
- [Prints de execução](#prints-de-execução)

---

## Tecnologias

| Camada | Ferramenta |
|---|---|
| Linguagem | Java 17 |
| Build | Maven (multi-módulo) |
| Testes API | RestAssured 5.4 + JUnit 5 |
| Testes Web | Selenium 4.20 + JUnit 5 |
| Driver Manager | WebDriverManager (Boni Garcia) |
| Serialização | Jackson |
| Asserções | JUnit Jupiter + Hamcrest |
| CI/CD | GitHub Actions |

---

## Estrutura do projeto

```
qa-automation/
├── api-tests/                          # Módulo de testes de API
│   ├── pom.xml
│   └── src/test/java/com/petstore/
│       ├── clients/                    # Clients RestAssured (User, Pet, Store)
│       ├── models/                     # POJOs (User, Pet, Order)
│       ├── tests/                      # Casos de teste JUnit 5
│       └── utils/                      # Config e Data Factory
├── web-tests/                          # Módulo de testes Web
│   ├── pom.xml
│   └── src/test/java/com/saucedemo/
│       ├── base/                       # BaseTest e DriverFactory
│       ├── pages/                      # Page Objects (POM)
│       ├── tests/                      # Casos de teste E2E
│       └── utils/                      # Config e Screenshot
├── .github/workflows/
│   └── ci.yml                          # Pipeline rodando ambos projetos
├── docs/                               # Prints de execução
└── pom.xml                             # POM raiz multi-módulo
```

---

## Pré-requisitos

- **Java 17+** (`java -version`)
- **Maven 3.9+** (`mvn -version`)
- **Google Chrome** instalado (apenas para testes Web)
- **Git**

---

## Instalação

```bash
git clone https://github.com/luix-hr/qa-automation
cd qa-automation
mvn clean install -DskipTests
```

---

## Execução dos testes

### Rodar tudo (API + Web)

```bash
mvn test
```

### Apenas API

```bash
mvn test -pl api-tests
```

### Apenas Web (com navegador visível)

```bash
mvn test -pl web-tests
```

### Web em modo headless (igual ao CI)

```bash
mvn test -pl web-tests -Pci
```

### Rodar uma classe de teste específica

```bash
mvn test -pl api-tests -Dtest=PetTest
mvn test -pl web-tests -Dtest=CheckoutE2ETest
```

Os relatórios são gerados em `*/target/surefire-reports/` e screenshots em `web-tests/target/screenshots/`.

---

## Pipeline de CI

A pipeline está configurada em `.github/workflows/ci.yml` e dispara em **push** e **pull_request** nas branches `main` e `develop`.

São executados **dois jobs em paralelo**:

1. **api-tests** — instala JDK 17, executa `mvn test` no módulo `api-tests` e publica relatórios como artefato.
2. **web-tests** — instala JDK 17 e Chrome, executa `mvn test -Pci` (modo headless) no módulo `web-tests`, publica relatórios e screenshots.

Para acompanhar a execução, acesse a aba **Actions** do repositório no GitHub.

---

## Cenários cobertos

### API — Petstore (`https://petstore.swagger.io/v2`)

**User**
- POST `/user` — criar usuário
- GET `/user/{username}` — buscar usuário
- GET `/user/login` — autenticar
- PUT `/user/{username}` — atualizar
- GET `/user/logout` — logout
- DELETE `/user/{username}` — remover
- GET `/user/{username}` 404 — usuário inexistente

**Pet**
- POST `/pet` — criar pet
- GET `/pet/{id}` — buscar por ID
- PUT `/pet` — atualizar status
- GET `/pet/findByStatus` — listar por status
- DELETE `/pet/{id}` — remover
- GET `/pet/{id}` 404 — após deleção

**Store**
- GET `/store/inventory` — inventário
- POST `/store/order` — criar pedido
- GET `/store/order/{id}` — buscar pedido
- DELETE `/store/order/{id}` — remover pedido
- GET `/store/order/{id}` 404 — após deleção

### Web — SauceDemo (`https://www.saucedemo.com/`)

- **Login** — usuário válido, usuário bloqueado e credenciais inválidas
- **Cart** — adicionar múltiplos produtos e validar contador
- **E2E Checkout** — login → adicionar 2 produtos → ir ao carrinho → preencher dados → revisar → finalizar pedido → validar mensagem de confirmação

---

## Boas práticas aplicadas

- **Page Object Model** — cada página do SauceDemo encapsulada em sua própria classe; testes não conhecem seletores.
- **Client Pattern** para API — cada recurso (User/Pet/Store) tem seu próprio client com métodos HTTP isolados.
- **Data Factory** — geração de dados de teste com IDs aleatórios para evitar colisão entre execuções.
- **Config externalizada** em `config.properties` com possibilidade de override via `-D` na linha de comando.
- **Driver Factory** — criação centralizada do WebDriver com suporte a modo headless detectado por property/env.
- **BaseTest e BasePage** — herança para reuso de setup/teardown e waits.
- **RequestSpecBuilder** reutilizável para padronizar headers e logging das requisições.
- **Screenshots automáticos** após cada teste Web, anexados como artefato no CI.
- **Asserções específicas** com mensagens descritivas usando JUnit Jupiter + Hamcrest.

---

## Prints de execução

Adicionar em `docs/`:
- `docs/api-success.png` — execução local dos testes de API
- `docs/web-success.png` — execução local dos testes Web
- `docs/ci-actions.png` — pipeline verde no GitHub Actions

---

## Licença

Projeto acadêmico — uso livre para fins de estudo.
