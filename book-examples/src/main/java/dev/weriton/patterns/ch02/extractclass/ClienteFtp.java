package dev.weriton.patterns.ch02.extractclass;

public interface ClienteFtp {
    void login(String usuario, String senha);
    ArquivoFtp buscarArquivo(String caminhoArquivo);
}
