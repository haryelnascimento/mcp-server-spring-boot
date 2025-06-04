---
mode: 'agent'
tools: ['githubRepo', 'codebase']
description: 'Geração automatizada de migrações Liquibase e código Java (model, repository, service, controller, dto, exception, testes) para projetos Spring Boot 3.3.0 usando Java 21, SOLID e Clean Architecture.'
---

# Objetivo

Gerar código-fonte para um projeto Java 21 com Spring Boot 3.3.0 estruturado em camadas, seguindo os princípios da **Clean Architecture** e **SOLID**.

A geração deve incluir:

- Migrações em YAML para **Liquibase**, com convenção de nome do arquivo: `create_table_{NomeEntidade}.yml`
- Classes de **modelo** com anotações JPA 
- Interfaces de **repositório** estendendo `JpaRepository`
- Serviços com métodos CRUD anotados com `@Transactional`
- Controladores REST com mapeamento de endpoints para operações CRUD
- **DTOs** para transferência de dados entre camadas na pasta
- Exceções customizadas para tratamento estruturado de erros na pasta
- Validações com **Bean Validation** 
- **Testes unitários** para `model` e `service` com JUnit 5 e Mockito

---

## Fonte de Dados

As informações devem ser extraídas de um arquivo Markdown contendo a definição de entidades, atributos e regras de negócio.

**Padrão:** `/requisitos/Solicitações.md`

Caso não especificado, use este caminho como fonte de entrada.

---

## Estrutura de Diretórios Esperada

Gerar os arquivos sob a raiz de `src/main/java/br/com/mcp/mcp_server` com a seguinte hierarquia:

src/main/java/br/com/mcp/mcp_server
├── domain/
│ ├── model/
│ └── repository/
├── application/
│ └── service/
├── infrastructure/
│ ├── controller/
│ ├── persistence/
│ └── exception/
└── shared/
└── dto/