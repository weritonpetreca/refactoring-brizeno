package dev.weriton.patterns.ch02.extractclass;

/**
 * Contrato de persistência de vendas.
 *
 * <p>Decisão além do livro: o nome do método foi simplificado de
 * {@code salvarDeArquivo} para {@code salvar} — o repositório não precisa
 * saber que os dados vieram de um arquivo FTP. Isso remove o vazamento
 * do detalhe de transporte para dentro da camada de persistência.
 */
public interface RepositorioDeVendas {
    void salvar(ArquivoFtp arquivo);
}
