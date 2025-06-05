# MCP Server com Spring Boot

Este projeto é uma API RESTful desenvolvida em Spring Boot 3 para demonstrar a integração via **Model Context Protocol (MCP)**, permitindo que agentes de IA como o GitHub Copilot interajam com a API de forma inteligente e automatizada.

## 🤖 Permitindo executar comandos via linguagem natural no GitHub Copilot

- Consultar usuários pelos filtros de nome, email, status e perfil
![MCP Inspector](docs/copilot-busca-inativos.png)

- Buscar o usuário com ID X
![MCP Inspector](docs/copilot-busca-por-id.png)

- Criar vários usuários com dados fictícios
![MCP Inspector](docs/copilot-criar.png)

- Atualizar o email do usuário com ID X
![MCP Inspector](docs/copilot-atualizar.png)

- Remover o usuário com ID Y
![MCP Inspector](docs/copilot-deleta.png)

## O que é o Model Context Protocol (MCP)?

O **Model Context Protocol (MCP)** é um protocolo aberto que permite conectar agentes de IA (como o GitHub Copilot) a aplicações externas, expondo métodos do seu backend como "ferramentas" acessíveis por linguagem natural. Com o MCP, você pode:

- **Automatizar tarefas**: Expor operações do seu sistema para serem executadas por IA, como criar, buscar ou atualizar dados.
- **Integrar com assistentes**: Permitir que ferramentas como Copilot Chat, scripts ou outros clientes interajam com sua API de forma inteligente.
- **Testar e depurar**: Usar interfaces como o MCP Inspector para explorar e testar as ferramentas expostas.

### Tipos de Aplicações MCP
- **Servidores MCP**: Backends (como este projeto) que expõem métodos via MCP.
- **Clientes MCP**: Ferramentas, IAs ou interfaces (VSCode, Copilot, MCP Inspector) que consomem essas ferramentas.
- **Proxies MCP**: Intermediários que facilitam a comunicação entre clientes e servidores MCP (ex: MCPP do MCP Inspector).

#### Saiba mais sobre o MCP em [Model Context Protocol](https://modelcontextprotocol.io/introduction)
---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL 17
- Docker e Docker Compose
- GitHub Copilot (via MCP)

---

## 📁 Estrutura de Pastas

```
src/
└── main/
    ├── java/
    │ └── br.com.mcp.mcp_server
    │ ├── domain
    │ ├── application
    │ ├── infrastructure
    │ └── shared
    └── resources/
    ├── db/
    │ └── changelog/
    │ ├── db.changelog-master.yaml
    │ └── create-table-usuario.yaml
    └── application.yml
```

---

## ⚙️ Como Rodar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/haryelnascimento/mcp-server-spring-boot.git
cd mcp-server
```

### 2. Suba os containers com Docker Compose

```bash
docker compose up -d
```

### 3. Execute o projeto
```bash
./mvnw spring-boot:run
```

---

## 💡 Integração com GitHub Copilot (MCP Server)

O projeto já está preparado para funcionar com a extensão GitHub Copilot (com suporte ao Model Context Protocol - MCP), permitindo que você interaja com a API por comandos em linguagem natural (em português ou inglês), diretamente no VSCode.

### ✅ Pré-requisitos

- GitHub Copilot habilitado na sua conta
- Visual Studio Code instalado
- Projeto rodando com MCP Server (como neste projeto)
- Extensão Copilot Chat instalada no VSCode (Preview)

### ⚙️ Como configurar no VSCode

#### 1. Abra o projeto no VSCode.

#### 2. Abra o painel do Copilot Chat (Ctrl + Shift + P e digite "Copilot Chat").

![Copilot Chat](docs/chat-copilot.png)

- Com o chat aberto, mude para o modo agente (Agent Mode) e clique no ícone de ferramentas ou use o atalho **(Ctrl + Shift + /)**.

#### 3. Preencha as informações solicitadas para adicionar o MCP Server:

- Arquivos `.vscode/mcp.json` na raiz do projeto.

```json
{
    "servers": {
        "mcp-server": {
            "type": "sse",
            "url": "http://localhost:8080/sse",
            "headers": {
                "authorization": "basic Y2xpZW50OnNlY3JldA=="
            }
        }
    }
}
```
- O valor do authorization é a string client:secret codificada em Base64.

![Conectando ao MCP-Server](mcp-server-config.png)

---

O agente AI reconhecerá a ferramenta exposta com @Tool no seu projeto e fará chamadas reais para os métodos do serviço Java.

## 💬 Como funciona nos bastidores?

O MCP Server Starter para Spring Boot permite que você exponha métodos do seu backend como ferramentas acessíveis por agentes de IA.

```
<dependency>
    <groupId>org.springframework.ai</groupId>
	    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
	<version>1.0.0</version>
</dependency>
```

Ele utiliza anotações específicas para identificar métodos que podem ser chamados via linguagem natural. Você anota seu método com @Tool:

```java
@Tool(
    name = "listarTodosUsuarios",
    description = "Lista todos os usuários cadastrados no sistema."
)
@Transactional(readOnly = true)
public List<Usuario> listarTodos() {
    return usuarioRepository.findAll();
}
```

Isso gera uma interface de ferramenta que o Copilot pode "entender", permitindo interações diretas sem precisar conhecer o código.

#### Saiba mais sobre [Spring AI MCP Server Starter](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html)
---

## 🎁 Dica para visualizar as tools disponíveis com o MCP Inspector 

O MCP Inspector é uma ferramenta de desenvolvedor feita para testar e depurar servidores MCP de forma visual e interativa, direto no navegador.

### 🔍 O que é o MCP Inspector?
Ele é composto por dois componentes:

- MCPI (MCP Inspector Client): Uma interface web em React que permite explorar e testar ferramentas expostas no seu servidor MCP.

- MCPP (MCP Proxy): Um servidor Node.js que conecta o navegador ao servidor MCP (via stdio, SSE, ou streamable-http).

O MCPP não é um proxy de rede tradicional. Ele atua como um cliente MCP e expõe o conteúdo em uma interface web.

### ✅ Requisitos
- Node.js 22.7.5 ou superior (recomendado: nvm install 22 && nvm use 22)

### ⚙️ Como executar
No terminal (com seu MCP Server já em execução):

```
npx @modelcontextprotocol/inspector
```

A interface será aberta automaticamente no navegador em:

```
 http://localhost:6274
```

### 🖼️ Console MCP Inspector
![MCP Inspector](docs/mcp-inspector.png)