package dev.weriton.patterns.ch02.extractclass.livro_original;

import dev.weriton.patterns.ch02.extractclass.ArquivoFtp;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Brizeno, Cap. 2 (Extrair Classe)
 *
 * <p>No original, o contrato do repositório é {@code salvarDeArquivo},
 * um nome que vaza o detalhe do transporte (FTP) para dentro da camada de persistência.
 * O repositório "sabe" que os dados vieram de um arquivo FTP.
 *
 * <p>Após a refatoração, o contrato é simplificado para {@code salvar(ArquivoFtp)},
 * e o repositório apenas recebe o dado, sem conhecer a origem.
 *
 * @see dev.weriton.patterns.ch02.extractclass.RepositorioDeVendas versão refatorada
 */
public interface RepositorioDeVendas {

    /**
     * Persiste o arquivo de vendas recebido do servidor FTP.
     *
     * <p>⚠️ O nome {@code salvarDeArquivo} é um "mau cheiro" sutil: revela que
     * o repositório conhece o mecanismo de entrega dos dados (arquivo FTP),
     * violando o encapsulamento da camada de persistência.
     *
     * @param arquivo o arquivo recebido via protocolo FTP
     */
    void salvarDeArquivo(ArquivoFtp arquivo);
}
