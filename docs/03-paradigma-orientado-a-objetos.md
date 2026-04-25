# Capítulo 3: Java e o Paradigma Orientado a Objetos
Nas próximas seções do livro, vamos aplicar alguns dos padrões descritos pela Gangue dos Quatro. Porém, antes de mergulhar neles, vamos avaliar algumas características da linguagem Java que impactam diretamente na utilização dos padrões.

## 3.1 Pensando orientado a objetos
O paradigma orientado a objetos nos faz pensar no design da aplicação em termos de objetos trocando mensagens.

### Mensagens e estado
Alan Kay, criador da programação orientada a objetos, descreve a linguagem usando a metáfora de células biológicas: objetos se comunicariam utilizando mensagens, visando proteger e esconder seu estado interno.

* Uma célula não altera a estrutura interna de outra, elas apenas trocam mensagens por meio de estímulos.

* Uma vez recebido o estímulo externo, o estado interno da célula sofrerá modificações.

* A definição de Orientação a Objetos é fortemente baseada nesses dois conceitos: **mensagens e estado**.

O estado de um objeto é basicamente composto por seus atributos, representando uma parte do mundo virtual modelado. Ter o estado interno escondido e protegido centraliza as mudanças dentro dos objetos, acionadas por mensagens através de uma **interface** (conjunto de métodos).

### Padrões orientados a objetos e Tipagem
O impacto principal da tipagem no uso de padrões é a necessidade de criar mais classes em linguagens de tipagem estática (como Java e C++) para permitir a troca de tipos.

* Tipagem Estática: Exige declaração explícita de tipo. Um mecanismo de herança e polimorfismo é necessário para facilitar a troca de tipos sem perda de informação.

* Tipagem Dinâmica: (Ex: Smalltalk) Não requer declaração de tipo. Qualquer tipo passado será atribuído e o único requisito é que o método esteja declarado no tipo referenciado.

## 3.2 Características do Java
Java é estaticamente tipado, o que dá mais segurança contra erros em tempo de execução, mas exige mais código para obter flexibilidade.

### Polimorfismo
A ideia de utilizar um objeto de um tipo genérico e poder mudar sua implementação por outros objetos é chamada de **polimorfismo**.

* Exemplo: A interface `List` serve como base para `ArrayList` e `LinkedList`. O código que utiliza a variável do tipo `List` não sofre alterações ao trocar a implementação.

### Sobrecarga e sobrescrita de métodos
* **Sobrescrever (@Override):** Elimina completamente o código original do método da classe pai para redefinir o comportamento.

* **Sobrecarregar:** Adiciona mais regras à lógica que já existia.

### Interfaces e classes abstratas
São formas de alcançar o polimorfismo definindo um "contrato".

* **Interface:** Representa um contrato puro, sem lógica (ex: interface `Arma`).

* **Classe Abstrata:** Pode implementar métodos comuns a todas as classes que a herdam, evitando repetição de código (ex: `danoComArmaQuebrada()` em uma classe abstrata `Arma`).