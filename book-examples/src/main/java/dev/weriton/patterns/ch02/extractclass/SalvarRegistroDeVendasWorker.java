package dev.weriton.patterns.ch02.extractclass;

import lombok.RequiredArgsConstructor;

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
