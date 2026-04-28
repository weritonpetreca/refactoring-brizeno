# 🐺 Refatorando com Padrões de Projeto: O Grimório do Arquiteto

> *"Todo bruxo sabe que enfrentar um monstro sem preparação é suicídio.
> Refatorar código sem testes é o mesmo."*

Laboratório prático de Engenharia de Software baseado no livro
*"Refatorando com Padrões de Projeto: Um Guia em Java"* de Marcos Brizeno.

O objetivo não é replicar exemplos — é **elevar os conceitos a padrão de produção moderno**,
integrando Design Patterns com Clean Architecture, boas práticas de OO e cultura DevSecOps (Shift-Left).

[![CI](https://github.com/weritonpetreca/refactoring-brizeno/actions/workflows/ci.yml/badge.svg)](https://github.com/weritonpetreca/refactoring-brizeno/actions/workflows/ci.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=weritonpetreca_refactoring-brizeno&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=weritonpetreca_refactoring-brizeno)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=weritonpetreca_refactoring-brizeno&metric=coverage)](https://sonarcloud.io/summary/new_code?id=weritonpetreca_refactoring-brizeno)

---

## 🏰 Estrutura da Fortaleza (Multi-Módulo)

Arquitetura Gradle multi-módulo (Kotlin DSL) que separa teoria da prática profissional:

```
refactoring-brizeno/
├── 📖 docs/                        # Resumos e análises teóricas por capítulo
├── 🧪 book-examples/               # Java puro — implementações fiéis ao livro
│   └── src/
│       ├── main/java/dev/weriton/patterns/
│       │   ├── ch02/extractclass/  # Cap. 2.5 — Extrair Classe
│       │   ├── ch02/movemethod/    # Cap. 2.3 — Mover Método
│       │   ├── ch02/movefield/     # Cap. 2.4 — Mover Campo
│       │   └── ch03/paradigma/     # Cap. 3   — Paradigma OO
│       └── test/                   # Testes unitários e de integração (Testcontainers)
├── ⚔️  real-world-implementations/  # Spring Boot — padrões em contexto profissional
└── ☁️  terraform/                   # IaC para AWS (em construção)
```

Cada seção do `book-examples` possui um subpacote `livro_original/` com o código **antes**
da refatoração, documentado com os maus cheiros identificados, e o código refatorado no
pacote pai — compare lado a lado para visualizar o que cada técnica resolve.

---

## 📚 Progresso — O Bestiário Caçado

### Capítulo 2 — A Arte da Refatoração

| Seção | Técnica | Mau Cheiro | Status |
|---|---|---|---|
| 2.3 | Mover Método | Feature Envy | ✅ |
| 2.4 | Mover Campo | Intimidade Inapropriada | ✅ |
| 2.5 | Extrair Classe | Classe Inchada (God Class) | ✅ |

### Capítulo 3 — Paradigma Orientado a Objetos

| Seção | Conceito | Status |
|---|---|---|
| 3.1 | Mensagens e Estado | ✅ |
| 3.2 | Polimorfismo, Herança, Interfaces vs. Classes Abstratas | ✅ |

---

## 🛠️ O Arsenal (Stack Tecnológica)

| Camada | Tecnologia |
|---|---|
| **Linguagem** | Java 21 (Records, Pattern Matching, Text Blocks) |
| **Framework** | Spring Boot 4.0.x (`real-world-implementations`) |
| **Build** | Gradle 9.x (Kotlin DSL) |
| **Banco de dados** | PostgreSQL (Testcontainers — efêmero nos testes) |
| **Testes unitários** | JUnit 5 + Mockito 5.x |
| **Testes de integração** | Testcontainers 1.21.x (FTP real, PostgreSQL real) |
| **Cobertura** | JaCoCo — mínimo 80% (falha o build abaixo disso) |
| **SCA / OWASP** | OWASP Dependency Check — falha em CVSS ≥ 7.0 |
| **SAST** | SonarCloud (análise estática + cobertura) |
| **CI/CD** | GitHub Actions |
| **Infraestrutura** | Terraform (em construção) |

---

## 🛡️ O Pipeline — The Automated Forge

Cada `push` ou `pull_request` para `main` executa automaticamente:

```
Checkout → Java 21 (Temurin) → Cache NVD DB
    ↓
./gradlew clean check dependencyCheckAnalyze
    │
    ├── Compilação
    ├── Testes (JUnit 5 + Testcontainers)
    ├── JaCoCo → cobertura mínima 80%
    └── OWASP SCA → bloqueia em CVSS ≥ 7.0
    ↓
Upload JaCoCo XML → artifact
    ↓
SonarCloud SAST (apenas em sucesso)
```

---

## 📜 O Código de Conduta (Diretrizes de Engenharia)

1. **Injeção de Dependências via Construtor:** `@Autowired` em atributos é proibido.
   Usamos injeção via construtor (Lombok `@RequiredArgsConstructor`) para garantir
   imutabilidade e testabilidade.

2. **Entidades Blindadas:** `@Data` do Lombok não é usado em entidades JPA — evita
   problemas de performance e referências circulares em `hashCode()`/`equals()`.

3. **Padrão AAA nos Testes:** Todo teste segue *Arrange, Act, Assert* — documentação
   viva do comportamento esperado do sistema.

4. **Shift-Left Security:** Validações de entrada obrigatórias antes que dados
   atinjam o domínio. Dados inválidos são rejeitados na fronteira da aplicação.

5. **Independência de Framework:** Padrões de projeto na camada de domínio não
   dependem do Spring. O framework atua apenas como orquestrador na camada externa.

6. **Testes de Integração com Infraestrutura Real:** Testcontainers para FTP
   (`delfer/alpine-ftp-server`) e PostgreSQL — sem mocks de infraestrutura nos
   testes de integração.

---

## 🗂️ Documentação Técnica

| Arquivo | Conteúdo |
|---|---|
| [`docs/01-introducao.md`](docs/01-introducao.md) | Padrão AAA, Mockito, a regra de ouro do arquiteto |
| [`docs/02-refatoracao.md`](docs/02-refatoracao.md) | Maus cheiros, técnicas e decisões além do livro (Cap. 2) |
| [`docs/03-paradigma-orientado-a-objetos.md`](docs/03-paradigma-orientado-a-objetos.md) | OO em Java: polimorfismo, herança, tipagem estática (Cap. 3) |

---

## ⚙️ Como Executar

**Pré-requisitos:** Java 21, Docker (para Testcontainers)

```bash
# Clonar o repositório
git clone https://github.com/weritonpetreca/refactoring-brizeno.git
cd refactoring-brizeno

# Rodar todos os testes com cobertura
./gradlew clean check

# Rodar apenas o módulo book-examples
./gradlew :book-examples:test

# Gerar relatório JaCoCo
./gradlew jacocoTestReport
# → book-examples/build/reports/jacoco/test/html/index.html
```

> **Nota:** Os testes de integração do `ClienteFtpImpl` sobem um container FTP real
> via Docker. Certifique-se de que o daemon Docker está rodando antes de executar.

---

*🐺 Projeto desenvolvido por [Weriton Petreca](https://github.com/weritonpetreca) — Mestre Bruxo Programador*
*Referência: Brizeno, M. Refatorando com Padrões de Projeto: Um Guia em Java. Casa do Código.*
*Repositório oficial do livro: [github.com/MarcosX/rppj](https://github.com/MarcosX/rppj)*