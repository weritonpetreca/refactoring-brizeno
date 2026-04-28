# 🐺 Capítulo 3: Java e o Paradigma Orientado a Objetos — A Filosofia do Bruxo

> *"Um bruxo não é apenas um guerreiro. Ele é um sistema de regras, mutações e
> escolhas — cada parte separada, mas cooperando em harmonia. Isso é Orientação a Objetos."*

Antes de mergulhar nos padrões da Gangue dos Quatro, precisamos dominar a linguagem
em que eles vivem. Java é estaticamente tipado — isso nos dá segurança, mas exige
mais código para obter flexibilidade. Entender essas características é o que separa
um desenvolvedor que *usa* padrões de um que os *compreende*.

**Referência:** Brizeno, M. *Refatorando com Padrões de Projeto: Um Guia em Java*. Casa do Código, Cap. 3.
**Repositório oficial do livro:** [github.com/MarcosX/rppj](https://github.com/MarcosX/rppj)

---

## 3.1 Pensando Orientado a Objetos — Mensagens e Estado

Alan Kay, criador da programação orientada a objetos, descreve a linguagem com
a metáfora de células biológicas: objetos se comunicam por **mensagens**, protegendo
e escondendo seu estado interno — exatamente como Geralt protege seus sinais internos
do mundo exterior.

> *Brizeno, Cap. 3, seção 3.1*

| Conceito | Biologia (Kay) | Programação OO |
|---|---|---|
| **Célula** | Unidade com estado protegido | Objeto com atributos privados |
| **Estímulo externo** | Mensagem química enviada | Chamada de método público |
| **Reação interna** | Mudança de estado da célula | Alteração dos atributos internos |
| **Encapsulamento** | Uma célula não altera diretamente outra | Um objeto não modifica atributos alheios |

O estado de um objeto é composto por seus **atributos**. Tê-lo escondido e protegido
centraliza as mudanças dentro dos objetos, acionadas por mensagens através de uma
**interface** — o conjunto de métodos públicos.

---

## 3.2 Características do Java — O Arsenal do Bruxo

> *Brizeno, Cap. 3, seção 3.2*

### Polimorfismo — A Espada que se Adapta

A ideia de usar um objeto de tipo genérico e poder trocar sua implementação é
chamada de **polimorfismo**. É o que permite ao Bruxo usar qualquer arma que
respeite o contrato de `Arma`:

```java
// Contrato (interface) — o "tipo genérico"
public interface Arma {
    int getDano();
    int getBonusVelocidade();
}

// Implementação — uma arma específica
public class Adaga implements Arma {
    @Override public int getDano() { return 10; }
    @Override public int getBonusVelocidade() { return 3; }
}
```

> *O código que usa `Arma` não sofre alteração ao trocar `Adaga` por `EspadaDePrata` —
> desde que ambas respeitem o contrato.* (Brizeno, Cap. 3)

### Tipagem Estática vs. Dinâmica — Segurança vs. Flexibilidade

| Característica | Java (Estática) | Smalltalk (Dinâmica) |
|---|---|---|
| **Declaração de tipo** | Obrigatória em tempo de compilação | Não necessária |
| **Erros** | Detectados em compilação | Detectados em execução |
| **Flexibilidade** | Requer interfaces e herança | Qualquer tipo pode ser passado |
| **Impacto nos padrões** | Mais classes necessárias para flexibilidade | Padrões mais simples de implementar |

> *"O impacto principal da tipagem no uso de padrões é a necessidade de criar mais
> classes em linguagens de tipagem estática para permitir a troca de tipos."*
> — Brizeno, Cap. 3, seção 3.1

### Sobrecarga vs. Sobrescrita — Dois Feitiços Diferentes

| | Sobrescrita (`@Override`) | Sobrecarga |
|---|---|---|
| **O que faz** | Redefine completamente o comportamento herdado | Adiciona novas regras à lógica existente |
| **Assinatura** | Mesma assinatura da classe pai | Assinaturas diferentes (parâmetros distintos) |
| **Polimorfismo** | ✅ Sim — o tipo em tempo de execução decide | ❌ Não — decidido em tempo de compilação |
| **Exemplo** | `toString()` em `Estudante` | `calcular(int)` e `calcular(int, int)` |

```java
// Sobrescrita — Ciri redefine como ela se apresenta ao mundo
@Override
public String toString() {
    return "Nome: " + nome + "\nSerie: " + serie;
}
```

📂 Código: [`ch03/paradigma`](../book-examples/src/main/java/dev/weriton/patterns/ch03/paradigma)

### Interfaces vs. Classes Abstratas — Contrato vs. Herança Parcial

| | Interface | Classe Abstrata |
|---|---|---|
| **Define** | Contrato puro — *o que* fazer | Comportamento parcial — *como* fazer parte |
| **Implementação** | Nenhuma (antes do Java 8) | Pode ter métodos concretos |
| **Herança múltipla** | ✅ Uma classe pode implementar várias | ❌ Java permite apenas uma herança |
| **Caso de uso** | Polimorfismo desacoplado | Compartilhar código entre subclasses relacionadas |
| **Exemplo no livro** | `interface Arma` | `abstract class Arma` com `danoComArmaQuebrada()` |

> *"Uma interface representa um contrato puro, enquanto uma classe abstrata pode
> implementar métodos comuns a todas as classes que a herdam, evitando repetição
> de código."* — Brizeno, Cap. 3, seção 3.2

---

## Por Que Isso Importa para os Padrões?

Os padrões da Gangue dos Quatro foram criados em um mundo de tipagem estática.
Cada padrão que veremos nos próximos capítulos usa **interfaces**, **herança** e
**polimorfismo** como ferramentas fundamentais.

| Padrão (próx. caps.) | Mecanismo OO usado |
|---|---|
| Factory | Interface + polimorfismo |
| Strategy | Interface + injeção de dependência |
| Decorator | Herança + composição |
| Template Method | Classe abstrata + sobrescrita |

Dominar este capítulo é montar o equipamento antes de partir para a Caçada.
