package dev.weriton.patterns.ch02.extractclass;

/**
 * Contrato de um cliente FTP.
 *
 * <p>Decisão além do livro: no original, {@code ClienteFtp} era uma classe concreta
 * instanciada com {@code new} dentro do worker. Aqui é uma interface, permitindo
 * injetar mocks nos testes sem depender de servidor FTP real.
 *
 * <p>O ciclo de vida é explícito: {@link #conectar()} antes de operar,
 * {@link #desconectar()} ao finalizar — gerenciado pelo {@link GerenciadorFtp}
 * via {@code try/finally}.
 *
 * @see ClienteFtpImpl implementação real usando Apache Commons Net
 */
public interface ClienteFtp {
    void conectar();
    ArquivoFtp buscarArquivo(String caminhoArquivo);
    void desconectar();
}
