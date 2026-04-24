# 🐺 Refatorando com Padrões de Projeto: O Grimório do Arquiteto

Este repositório é um laboratório prático e avançado de Engenharia de Software, baseado no livro *"Refatorando com Padrões de Projeto: Um Guia em Java"* de Marcos Brizeno.

Mais do que apenas replicar exemplos, o objetivo deste projeto é **elevar os conceitos teóricos a um padrão de produção moderno**, integrando Design Patterns com Clean Architecture, boas práticas de Orientação a Objetos e uma forte cultura de DevSecOps (Shift-Left).

## 🏰 Estrutura da Fortaleza (Multi-Módulo)

A arquitetura foi desenhada para separar estritamente a teoria da prática profissional, utilizando Gradle (Kotlin DSL):

* 📖 **`/docs`**: O bestiário. Contém os resumos, análises críticas e a documentação teórica de cada capítulo e padrão estudado.
* 🧪 **`/book-examples`**: Laboratório de Java Puro. Implementações fiéis aos exemplos do livro, focadas na essência do padrão de projeto sem a interferência de frameworks externos.
* ⚔️ **`/real-world-implementations`**: O campo de batalha. Módulo profissional utilizando Spring Boot, focado em resolver problemas arquiteturais reais com os padrões estudados, aplicando infraestrutura ágil e segurança.
* ☁️ **`/terraform`**: (*Em Construção*) Infraestrutura como Código (IaC) para provisionamento de recursos na AWS.

## 🛠️ O Arsenal (Stack Tecnológica)

* **Linguagem:** Java 21 (Aproveitando *Records*, *Pattern Matching*, etc.)
* **Framework:** Spring Boot 4.0.x
* **Build Tool:** Gradle (Kotlin DSL)
* **Banco de Dados:** PostgreSQL e MongoDB (orquestrados via Docker Compose)
* **Qualidade e Testes:** JUnit 5, Mockito e **Testcontainers** (para testes de integração fidedignos).
* **CI/CD:** GitHub Actions com análise estática (SonarQube/SonarLint) e verificação de dependências.

## 📜 O Código de Conduta (Diretrizes de Engenharia)

Para garantir que o código neste repositório seja resiliente e de fácil manutenção, seguimos regras rigorosas em nossas implementações reais:

1. **Injeção de Dependências Pura:** É estritamente proibido o uso de `@Autowired` em atributos de classe. Utilizamos injeção via construtor (facilitada pelo Lombok) para garantir classes testáveis e a imutabilidade do estado.
2. **Entidades Blindadas:** Não utilizamos `@Data` do Lombok em Entidades JPA para evitar problemas crônicos de performance e referências circulares nos métodos `hashCode()` e `equals()`.
3. **Padrão AAA nos Testes:** Todos os testes seguem o padrão *Arrange, Act, Assert*, garantindo legibilidade e documentação viva do comportamento do sistema.
4. **Shift-Left Security:** A segurança começa no código. Validações de entrada rigorosas (Bean Validation) são obrigatórias na camada de entrada antes que os dados atinjam o domínio.
5. **Independência de Framework:** Os padrões de projeto aplicados na camada de domínio não dependem do Spring. O framework atua apenas como um orquestrador na camada externa (Clean Architecture / Hexagonal).

---
*Projeto em constante evolução.*