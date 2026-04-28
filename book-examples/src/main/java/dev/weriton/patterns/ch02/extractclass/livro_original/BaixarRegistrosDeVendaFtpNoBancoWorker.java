package dev.weriton.patterns.ch02.extractclass.livro_original;

import dev.weriton.patterns.ch02.extractclass.ArquivoFtp;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Mau Cheiro: <b>Classe Inchada (God Class)</b>
 * <p>Brizeno, Cap. 2, seção 2.5 — Extrair Classe
 *
 * <p>Esta classe viola o <b>Princípio da Responsabilidade Única (SRP)</b>:
 * ela sabe se conectar a um servidor FTP <b>E</b> sabe salvar dados no banco.
 * Dois motivos distintos para mudar — dois mundos acoplados em um só lugar.
 *
 * <h2>Os Problemas</h2>
 * <ul>
 *   <li><b>Acoplamento rígido:</b> {@link ClienteFtp} é instanciado com {@code new}
 *       dentro do método, tornando impossível substituí-lo por um mock em testes.</li>
 *   <li><b>Baixa coesão:</b> campos {@code host}, {@code porta}, {@code usuario}
 *       e {@code senha} não têm nada a ver com {@code repositorioDeVendas}.</li>
 *   <li><b>Dificuldade de reutilização:</b> a lógica de FTP está presa nesta
 *       classe e não pode ser usada por outro Worker.</li>
 * </ul>
 *
 * <h2>A Cura: Extrair Classe</h2>
 * <p>A técnica de refatoração <b>Extrair Classe</b> separa a lógica de FTP em
 * {@code GerenciadorFtp} (pacote pai) e renomeia esta classe para
 * {@code SalvarRegistroDeVendasWorker}, que passa a receber suas
 * dependências por injeção via construtor.
 *
 * @see dev.weriton.patterns.ch02.extractclass.GerenciadorFtp classe extraída
 * @see dev.weriton.patterns.ch02.extractclass.SalvarRegistroDeVendasWorker versão refatorada
 */
public class BaixarRegistrosDeVendaFtpNoBancoWorker {

    // ⚠️ SMELL: campos de FTP misturados com campo de persistência
    private final String host;
    private final String porta;
    private final String usuario;
    private final String senha;
    private final RepositorioDeVendas repositorioDeVendas;

    public BaixarRegistrosDeVendaFtpNoBancoWorker(
            String host,
            String porta,
            String usuario,
            String senha,
            RepositorioDeVendas repositorioDeVendas) {
        this.host = host;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.repositorioDeVendas = repositorioDeVendas;
    }

    /**
     * Baixa um arquivo FTP <b>E</b> salva no banco — responsabilidades misturadas.
     *
     * <p>⚠️ O {@code new ClienteFtp(host, porta)} dentro do método cria acoplamento
     * direto com a implementação concreta. Não há como injetar um colaborador alternativo
     * (como um mock de testes), porque a dependência é criada internamente.
     *
     * @param caminhoArquivo caminho do arquivo no servidor FTP
     */
    public void requisitarFtp(String caminhoArquivo) {
        // ⚠️ Instanciação direta: acoplamento rígido, sem injeção de dependência
        ClienteFtp cliente = new ClienteFtp(host, porta);
        cliente.login(usuario, senha);
        ArquivoFtp arquivo = cliente.buscarArquivo(caminhoArquivo);
        repositorioDeVendas.salvarDeArquivo(arquivo);
    }
}
