
## US01: CRUD de usuários

**Descrição Geral da Tela**:  
Usuário tem um tela para manter usuários, onde pode cadastrar, atualizar e remover um usuário.

#### 1. Campos

Campos apresentados nesta subseção e suas respectivas regras:

| **Rótulo na Tela**    | **Nome do Campo** | **Tipo** | **Tamanho** | **Exemplo**                      |
| --------------------- | ----------------- | -------- | ----------- | -------------------------------- |
| nome                  | DesNome           | Varchar  | 255         | João Silva                       |
| email                 | DesEmail          | Varchar  | 255         | joaos@gmail.com                  |
| senha                 | DesSenha          | Varchar  | 255         | 123456                           |
| perfil                | DesPerfil         | Varchar  | 50          | Administrador                    |
| status                | DesStatus         | Varchar  | 50          | Ativo | Inativo                  |
| data de criação       | DataCriacao       | DateTime | -           | 2023-10-01 12:00:00              |
| data de atualização   | DataAtualizacao   | DateTime | -           | 2023-10-01 12:00:00              |