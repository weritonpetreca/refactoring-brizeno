package dev.weriton.patterns.ch02.extractclass;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GerenciadorFtp {

    private final String host;
    private final int port;
    private final String usuario;
    private final String senha;
    private final ClienteFtp clienteFtp;

    public ArquivoFtp requisitarFtp(String caminhoArquivo) {
        clienteFtp.login(usuario, senha);
        return clienteFtp.buscarArquivo(caminhoArquivo);
    }
}
