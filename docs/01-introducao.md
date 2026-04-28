# ⚔️ Capítulo 1: O Código de Proteção — Testes como Escudo do Bruxo

> *"Todo bruxo sabe que enfrentar um monstro sem preparação é suicídio.
> Refatorar código sem testes é o mesmo."*

Antes de aplicar qualquer Padrão de Projeto ou técnica de refatoração, é obrigatório
garantir que o comportamento do sistema esteja protegido. A refatoração só é segura
quando amparada por uma suíte de testes confiável — a armadura do arquiteto.

**Referência:** Brizeno, M. *Refatorando com Padrões de Projeto: Um Guia em Java*. Casa do Código.
**Repositório oficial do livro:** [github.com/MarcosX/rppj](https://github.com/MarcosX/rppj)

---

## O Padrão AAA (Arrange, Act, Assert)

Para manter os testes limpos, expressivos e profissionais, todos os testes deste
repositório seguem estritamente a anatomia AAA — o ritual em três etapas do Bruxo
antes de cada contrato:

1. **Arrange (Preparar o terreno):** Preparação do cenário. Instanciamos os objetos,
   configuramos os *Mocks* e definimos os dados de entrada. É o momento de estudar
   o bestiário antes de partir para a batalha.

2. **Act (Executar o golpe):** Onde a ação-alvo (o método sendo testado) é invocada.
   Deve ser geralmente uma única linha de código — um golpe preciso, sem desperdício.

3. **Assert (Verificar o resultado):** Onde validamos se o resultado da ação corresponde
   à expectativa ou se as dependências corretas foram chamadas. O troféu do contrato cumprido.

### Exemplo de Estrutura:

```java
@Test
void deveRetornarResultadosPorPaginaQuandoEspecificado() {
    // Arrange
    Parametros parametros = new Parametros("Um produto", 20);

    // Act
    Criterio criterio = Busca.criarCriterio(parametros);

    // Assert
    assertEquals(20, criterio.getResultadosPorPagina());
}
```

---

## Dublês de Teste (Mockito) — Os Fantoches do Bruxo

Quando testamos uma classe que interage com serviços externos ou lógicas pesadas
(como banco de dados ou chamadas REST), utilizamos o **Mockito** para isolar o
comportamento e criar ilusões controladas do mundo exterior.

| Ferramenta | O que faz | Analogia no Witcher |
|---|---|---|
| `mock()` | Cria um objeto falso que imita a interface da dependência | Uma ilusão de Quen — parece real, mas é controlada |
| `when().thenReturn()` | Ensina o mock a retornar um dado específico | Programar um golem para responder sempre da mesma forma |
| `verify()` | Confirma que o código interagiu com a dependência corretamente | Checar as pegadas — o golem foi de fato chamado? |
| `never()` | Verifica que um método jamais foi chamado | Certificar que o feitiço errado não foi lançado |

---

## A Regra de Ouro do Arquiteto

> **Nunca refatore sem testes.**

A refatoração só é segura se houver uma rede de proteção (Padrão AAA) e ferramentas
de análise (JaCoCo ≥ 80%, SonarCloud) garantindo que o comportamento externo
permanece idêntico após a alteração da estrutura interna.

---

*🛡️ Nota DevSecOps: Mocks são excelentes para o módulo `book-examples`. No entanto,
no módulo `real-world-implementations`, damos preferência aos **Testcontainers** para
testes de integração com o banco de dados real (PostgreSQL 18), garantindo máxima fidelidade.*
