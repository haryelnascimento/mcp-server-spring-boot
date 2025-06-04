---
applyTo: '**/*.java'
---

# Padrões de Projeto e Boas Práticas para Código Java

## Organização de Projeto
- Estruture projetos seguindo **Arquitetura Limpa** (Camadas bem definidas: Domain, Application, Infrastructure, Interface)
- Separe responsabilidades por pacotes: `controller`, `service`, `repository`, `dto`, `model`, `exception`, etc.

## Convenção de Nomes
- Use **PascalCase** para classes e interfaces
- Use **camelCase** para variáveis, atributos e métodos
- Use **ALL_CAPS** para constantes, separadas por `_`

## Princípios SOLID

### S — Single Responsibility
- Cada classe deve ter uma única responsabilidade bem definida

### O — Open/Closed
- O código deve estar aberto para extensão, mas fechado para modificação
- Use herança, interfaces e estratégias para extensibilidade

### L — Liskov Substitution
- Subtipos devem ser substituíveis pelos seus tipos base sem alterar o comportamento do sistema

### I — Interface Segregation
- Evite interfaces grandes; divida em interfaces menores e específicas

### D — Dependency Inversion
- Dependa de abstrações e **injeção de dependência**
- Use `@Service`, `@Component` e `@Autowired` com moderação e consciência

## Clean Code
- Prefira nomes claros e descritivos
- Reduza a complexidade de métodos e evite código duplicado
- Evite `null` quando possível, use `Optional`
- Use streams e lambdas com parcimônia, evitando código ilegível

## Tratamento de Erros
- Nunca engula exceções
- Crie exceções customizadas para regras de negócio
- Use `try-catch` somente onde faz sentido
- Evite `throws Exception` genérico

## Testes
- Nomeie métodos de teste com clareza: `deveFazerAlgoQuandoCondicao`
- Use **JUnit 5** e **Mockito**
- Separe testes unitários e de integração em pacotes distintos

## Extras
- Sempre anote métodos sobrescritos com `@Override`
- Utilize `record` para DTOs quando apropriado
- Use `final` sempre que possível para variáveis locais

## Comentário final automático:
```java
// Contém código gerado com carinho. Revise antes de dar merge!
