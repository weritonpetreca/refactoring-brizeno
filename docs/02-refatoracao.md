# Capítulo 2: A Arte da Refatoração e a Caça aos "Maus Cheiros"

Refatorar é o processo de alterar a estrutura interna de um software para torná-lo mais fácil de entender e mais barato de modificar, **sem alterar seu comportamento observável**.

Na engenharia moderna e no DevSecOps, a refatoração é uma questão de segurança: **código complexo e acoplado esconde vulnerabilidades**. Um código limpo e bem distribuído reduz a superfície de ataque e facilita a auditoria (Shift-Left).

## O Bestiário dos "Maus Cheiros" (Code Smells)

Maus cheiros não são necessariamente bugs, mas sim indicativos de que o design do código está apodrecendo e precisa de refatoração antes que quebre.

1. **A Classe Inchada (God Class):** 
   * **O Problema:** Classes que tentam fazer tudo (ex: ler arquivo, conectar no banco, fazer cálculo de negócio). Violam o Princípio da Responsabilidade Única (SRP).
   * **A Cura:** `Extrair Classe` (Extract Class). Separamos o domínio da infraestrutura (ex: `Worker` e `GerenciadorFtp`).

2. **Inveja de Dados (Feature Envy):**
    * **O Problema:** Um método em uma classe que parece mais interessado nos dados de *outra* classe do que na sua própria.
    * **A Cura:** `Mover Método` (Move Method). O método deve residir na classe cujos dados ele mais manipula (ex: mover o loop de desativação para o `RepositorioUsuarios`). Aplicação direta da regra "Tell, Don't Ask".

3. **Intimidade Inapropriada (Inappropriate Intimacy):**
    * **O Problema:** Duas classes são excessivamente dependentes dos detalhes internos (campos) uma da outra.
    * **A Cura:** `Mover Campo` (Move Field) ou `Extrair Classe`. Encapsulamos a lógica e os números mágicos em um local neutro e seguro (ex: `CalculadorDePreco` assumindo as taxas do `Taxi`).

## A Regra de Ouro do Arquiteto

**Nunca refatore sem testes.** A refatoração só é segura se houver uma rede de proteção (Padrão AAA) e ferramentas de análise (JaCoCo, SonarQube) garantindo que o comportamento externo permanece idêntico após a alteração da estrutura interna.