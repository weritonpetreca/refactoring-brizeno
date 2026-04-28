package dev.weriton.patterns.ch02.extractclass.livro_original;

import dev.weriton.patterns.ch02.extractclass.ArquivoFtp;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Brizeno, Cap. 2 (Extrair Classe)
 *
 * <p>No código original, {@code ClienteFtp} é uma <b>classe concreta</b>, não uma interface.
 * O {@link BaixarRegistrosDeVendaFtpNoBancoWorker} a instancia diretamente com {@code new},
 * criando um acoplamento rígido que impede substituição e testes isolados.
 *
 * <p>Após a refatoração, {@code ClienteFtp} se torna uma <b>interface</b> no pacote pai,
 * permitindo injeção de dependência e mocks em testes.
 *
 * @see dev.weriton.patterns.ch02.extractclass.ClienteFtp versão refatorada (interface)
 */
public class ClienteFtp {

    private final String host;
    private final String porta;

    public ClienteFtp(String host, String porta) {
        this.host = host;
        this.porta = porta;
    }

    /**
     * Autentica no servidor FTP.
     * No original, esta lógica é escondida dentro do Worker — sem contrato visível.
     */
    public void login(String usuario, String senha) {
        // Implementação de protocolo omitida — o foco está no design, não no protocolo.
    }

    /**
     * Busca um arquivo remoto pelo caminho informado.
     *
     * @param caminhoArquivo caminho absoluto no servidor FTP
     * @return o arquivo recuperado
     */
    public ArquivoFtp buscarArquivo(String caminhoArquivo) {
        // Implementação de protocolo omitida.
        return null;
    }
}
