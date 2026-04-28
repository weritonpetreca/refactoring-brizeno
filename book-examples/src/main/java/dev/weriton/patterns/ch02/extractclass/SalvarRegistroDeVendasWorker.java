package dev.weriton.patterns.ch02.extractclass;

import lombok.RequiredArgsConstructor;

/**
 * Versão refatorada — Brizeno, Cap. 2, seção 2.5 — Extrair Classe
 *
 * <p>Após a refatoração, este worker tem uma única responsabilidade:
 * orquestrar o fluxo de busca e persistência. A lógica de FTP foi
 * extraída para {@link GerenciadorFtp}.
 *
 * <p>A validação do {@code caminhoArquivo} aplica <b>Shift-Left Security</b>:
 * dados inválidos são rejeitados na entrada antes de atingir o domínio.
 *
 * @see dev.weriton.patterns.ch02.extractclass.livro_original.BaixarRegistrosDeVendaFtpNoBancoWorker versão original
 */
@RequiredArgsConstructor
public class SalvarRegistroDeVendasWorker {

    private final GerenciadorFtp gerenciadorFtp;
    private final RepositorioDeVendas repositorioDeVendas;

    public void requisitarFtp(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo ou vazio (Possível falha de segurança)");
        }
        ArquivoFtp arquivo = gerenciadorFtp.requisitarFtp(caminhoArquivo);
        repositorioDeVendas.salvar(arquivo);
    }
}
