package dev.weriton.patterns.ch02.extractclass;

import lombok.RequiredArgsConstructor;

/**
 * Classe extraída — Brizeno, Cap. 2, seção 2.5 — Extrair Classe
 *
 * <p>Encapsula o ciclo completo de uma operação FTP: conectar, buscar e desconectar.
 * Recebe um {@link ClienteFtp} via construtor — a implementação concreta
 * ({@link ClienteFtpImpl}) conhece as credenciais; o gerenciador apenas orquestra.
 *
 * <p>O bloco {@code try/finally} garante que {@code desconectar()} sempre seja
 * chamado, mesmo em caso de exceção, evitando vazamento de conexões.
 */
@RequiredArgsConstructor
public class GerenciadorFtp {

    private final ClienteFtp clienteFtp;

    public ArquivoFtp requisitarFtp(String caminhoArquivo) {
        clienteFtp.conectar();
        try {
            return clienteFtp.buscarArquivo(caminhoArquivo);
        } finally {
            clienteFtp.desconectar();
        }
    }
}
