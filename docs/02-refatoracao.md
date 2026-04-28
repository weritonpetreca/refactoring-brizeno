# ⚔️ Capítulo 2: A Arte da Refatoração — Caçando os Monstros do Código

> *"Os monstros mais perigosos não têm presas nem garras. São invisíveis —
> vivem no código que ninguém quer tocar."*
> — Inspirado em Brizeno, Cap. 2

Refatorar é o processo de alterar a estrutura interna de um software para torná-lo
mais fácil de entender e mais barato de modificar, **sem alterar seu comportamento observável**.

Na engenharia moderna e no DevSecOps, a refatoração é uma questão de segurança:
**código complexo e acoplado esconde vulnerabilidades**. Um código limpo e bem
distribuído reduz a superfície de ataque e facilita a auditoria (Shift-Left).

**Referência:** Brizeno, M. *Refatorando com Padrões de Projeto: Um Guia em Java*. Casa do Código, Cap. 2.
**Repositório oficial do livro:** [github.com/MarcosX/rppj](https://github.com/MarcosX/rppj)

---

## O Bestiário dos "Maus Cheiros" (Code Smells)

Maus cheiros não são necessariamente bugs — são sinais de que o design está
apodrecendo e atrairá problemas sérios se não for refatorado.

---

### 🐉 Monstro 1 — A Classe Inchada (God Class)

> *Brizeno, Cap. 2, seção 2.5 — Extrair Classe*

**O Problema:** Classes que tentam fazer tudo. No livro, a
`BaixarRegistrosDeVendaFtpNoBancoWorker` sabia conectar ao FTP **e** salvar no banco.
Viola o **Princípio da Responsabilidade Única (SRP)**.

| | Antes (livro_original) | Depois (refatorado) |
|---|---|---|
| **Classe** | `BaixarRegistrosDeVendaFtpNoBancoWorker` | `SalvarRegistroDeVendasWorker` + `GerenciadorFtp` |
| **Responsabilidade** | FTP **e** banco juntos | Uma por classe |
| **`ClienteFtp`** | Classe concreta instanciada com `new` | Interface injetada via construtor |
| **`RepositorioDeVendas`** | Classe concreta | Interface injetada via construtor |
| **Testabilidade** | ❌ Impossível sem servidor FTP real | ✅ Mock injetável |
| **Reutilização** | ❌ Lógica FTP presa no Worker | ✅ `GerenciadorFtp` reutilizável |
| **Segurança de conexão** | ❌ Conexão pode vazar em caso de erro | ✅ `try/finally` garante `desconectar()` |

**A Cura: Extrair Classe**

```java
// ⚠️ ANTES — God Class (livro_original)
public class BaixarRegistrosDeVendaFtpNoBancoWorker {
    private String host, porta, usuario, senha;      // domínio FTP
    private RepositorioDeVendas repositorioDeVendas; // domínio de persistência

    public void requisitarFtp(String caminhoArquivo) {
        ClienteFtp cliente = new ClienteFtp(host, porta); // acoplamento rígido
        cliente.login(usuario, senha);
        ArquivoFtp arquivo = cliente.buscarArquivo(caminhoArquivo);
        repositorioDeVendas.salvarDeArquivo(arquivo);
    }
}

// ✅ DEPOIS — Responsabilidades separadas com interfaces
public interface ClienteFtp {
    void conectar();
    ArquivoFtp buscarArquivo(String caminhoArquivo);
    void desconectar();
}

public class GerenciadorFtp {
    private final ClienteFtp clienteFtp;

    public ArquivoFtp requisitarFtp(String caminhoArquivo) {
        clienteFtp.conectar();
        try {
            return clienteFtp.buscarArquivo(caminhoArquivo);
        } finally {
            clienteFtp.desconectar(); // sempre desconecta, mesmo em caso de erro
        }
    }
}

public class SalvarRegistroDeVendasWorker {
    private final GerenciadorFtp gerenciadorFtp;
    private final RepositorioDeVendas repositorioDeVendas;

    public void requisitarFtp(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty())
            throw new IllegalArgumentException("Caminho inválido"); // Shift-Left Security
        ArquivoFtp arquivo = gerenciadorFtp.requisitarFtp(caminhoArquivo);
        repositorioDeVendas.salvar(arquivo);
    }
}
```

**Decisões além do livro:**
- `ClienteFtp` e `RepositorioDeVendas` vieram interfaces — o livro usa classes concretas, mas interfaces permitem mockar nos testes sem depender de servidor FTP ou banco real
- As credenciais (`host`, `porta`, `usuario`, `senha`) ficam encapsuladas na implementação concreta `ClienteFtpImpl`, que usa Apache Commons Net — o `GerenciadorFtp` não precisa saber nada sobre infraestrutura
- O `try/finally` no `GerenciadorFtp` garante que a conexão FTP sempre seja fechada, evitando vazamento de recursos
- Validação do `caminhoArquivo` no worker aplica Shift-Left Security antes que dados inválidos cheguem ao domínio

📂 Código: [`ch02/extractclass/livro_original`](../book-examples/src/main/java/dev/weriton/patterns/ch02/extractclass/livro_original) → [`ch02/extractclass`](../book-examples/src/main/java/dev/weriton/patterns/ch02/extractclass)

---

### 🐺 Monstro 2 — A Inveja de Dados (Feature Envy)

> *Brizeno, Cap. 2, seção 2.3 — Mover Método*

**O Problema:** Um método que parece mais interessado nos dados de *outra* classe
do que na sua própria. O `DesativarUsuariosWorker` original instanciava o `RepositorioUsuarios`
internamente com `new`, iterava a lista, verificava `semLoginRecente()` e `estaAtivo()` —
tudo trabalho que pertence ao próprio repositório e ao usuário.

| | Antes (livro_original) | Depois (refatorado) |
|---|---|---|
| **Dependências** | Instanciadas com `new` internamente | Injetadas via construtor (`@RequiredArgsConstructor`) |
| **Quem filtra a lista** | `DesativarUsuariosWorker` | `DesativarUsuariosWorker` via stream delegando ao `Usuario` |
| **Notificação** | `NotificadorViaEmail` estático | `Notificador` (interface) injetado via construtor |
| **Testabilidade** | ❌ Impossível mockar dependências | ✅ Mocks injetáveis |
| **Notificação vazia** | ❌ Notifica mesmo sem usuários | ✅ Guard clause evita notificação desnecessária |

**A Cura: Mover Método + Injeção de Dependências**

```java
// ⚠️ ANTES — Feature Envy: dependências instanciadas internamente (livro_original)
public void desativarUsuarios() {
    RepositorioUsuarios repositorio = new RepositorioUsuarios(); // impossível mockar
    List<Usuario> usuarios = repositorio.all().stream()
            .filter(u -> u.semLoginRecente() && u.estaAtivo())
            .collect(Collectors.toList());
    usuarios.forEach(repositorio::desativar);
    NotificadorViaEmail.usuariosDesativados(usuarios); // acoplamento estático
}

// ✅ DEPOIS — dependências injetadas, notificador como interface
@RequiredArgsConstructor
public class DesativarUsuariosWorker {
    private final RepositorioUsuarios repositorio;
    private final Notificador notificador; // interface, não implementação concreta

    public void desativarUsuarios() {
        List<Usuario> usuarios = usuariosParaDesativar();
        if (usuarios.isEmpty()) return; // guard clause
        usuarios.forEach(repositorio::desativar);
        notificador.usuariosDesativados(usuarios);
    }
}
```

**Decisões além do livro:**
- `Notificador` virou interface — permite trocar a implementação (email, SMS, push) sem tocar no worker
- `Usuario` nasce sem `ultimoLogin` — desacoplado da criação, o login é um evento explícito via `usuario.login()`
- `semLoginRecente()` trata `null` — usuário sem nenhum login é considerado inativo por definição
- Guard clause no worker evita chamar o notificador com lista vazia

📂 Código: [`ch02/movemethod/livro_original`](../book-examples/src/main/java/dev/weriton/patterns/ch02/movemethod/livro_original) → [`ch02/movemethod`](../book-examples/src/main/java/dev/weriton/patterns/ch02/movemethod)

---

### 🦂 Monstro 3 — A Intimidade Inapropriada (Inappropriate Intimacy)

> *Brizeno, Cap. 2, seção 2.4 — Mover Campo*

**O Problema:** O `Taxi` guardava `BANDEIRA_UM = 1.2f` e `BANDEIRA_DOIS = 1.8f`,
mas quem as *usava de verdade* era o `CalculadorDePreco`. Além disso,
o `CalculadorDePreco` recebia um `float` sem semântica — um número mágico.

| | Antes (livro_original) | Depois (refatorado) |
|---|---|---|
| **Onde ficam as constantes** | `Taxi` (lugar errado) | `CalculadorDePreco` (lugar certo) |
| **Parâmetro de bandeira** | `float bandeira` (número mágico) | `boolean ehFinalDeSemana` → lógica no `CalculadorDePreco` |
| **Valores das bandeiras** | `float` soltos | `enum Bandeira` com valor encapsulado |
| **Proteção contra valores inválidos** | ❌ Qualquer float passa | ✅ O enum só aceita valores válidos |

**A Cura: Mover Campo**

```java
// ⚠️ ANTES — Taxi guarda constantes que pertencem ao Calculador (livro_original)
public class Taxi {
    private static final float BANDEIRA_UM  = 1.2f; // ⚠️ não é responsabilidade do Taxi
    private static final float BANDEIRA_DOIS = 1.8f;

    public float calcularCorrida(float kmRodados) {
        return calculador.calcularCorrida(kmRodados, ehFinalDeSemana ? BANDEIRA_DOIS : BANDEIRA_UM);
    }
}

// ✅ DEPOIS — Taxi só sabe se é final de semana, Calculador decide a bandeira
public enum Bandeira {
    BANDEIRA_1(1.2f),
    BANDEIRA_2(1.8f);
    private final float valor; // valor encapsulado no enum
}

public class CalculadorDePreco {
    private static final float VALOR_POR_KM = 0.48f;

    public float calcularCorrida(float kmRodados, boolean bandeiraDois) {
        return (!bandeiraDois ? Bandeira.BANDEIRA_1.getValor() : Bandeira.BANDEIRA_2.getValor())
               * kmRodados * VALOR_POR_KM;
    }
}
```

**Decisões além do livro:**
- Os `float` soltos `BANDEIRA_UM` e `BANDEIRA_DOIS` foram substituídos pelo `enum Bandeira` — os valores agora têm nome, significado e ficam encapsulados no próprio enum via `@Getter` do Lombok
- A lógica de decidir qual bandeira usar permanece no `CalculadorDePreco`, fiel ao livro — o `Taxi` só informa se é final de semana, sem conhecer os valores das bandeiras

📂 Código: [`ch02/movefield/livro_original`](../book-examples/src/main/java/dev/weriton/patterns/ch02/movefield/livro_original) → [`ch02/movefield`](../book-examples/src/main/java/dev/weriton/patterns/ch02/movefield)

---

## A Regra de Ouro do Arquiteto

> *"Nunca refatore sem testes. A armadura protege o guerreiro durante a batalha —
> os testes protegem o código durante a refatoração."*

**Nunca refatore sem testes.** A refatoração só é segura se houver uma rede de proteção
(Padrão AAA) e ferramentas de análise (JaCoCo ≥ 80%, SonarCloud) garantindo que o
comportamento externo permanece **idêntico** após a alteração da estrutura interna.

Os pacotes `livro_original` deste repositório contêm o código **antes** da refatoração,
com testes que documentam o comportamento que deve ser preservado. Compare os dois
lado a lado para visualizar o que cada técnica resolve.
