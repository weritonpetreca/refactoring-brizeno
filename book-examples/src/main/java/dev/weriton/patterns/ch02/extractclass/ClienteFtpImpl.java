package dev.weriton.patterns.ch02.extractclass;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Implementação real de {@link ClienteFtp} usando Apache Commons Net.
 *
 * <p>As credenciais ({@code host}, {@code port}, {@code usuario}, {@code senha})
 * ficam encapsuladas aqui — o {@link GerenciadorFtp} não precisa conhecê-las.
 *
 * <p>{@code buscarArquivo} usa {@link ByteArrayOutputStream} como buffer em memória:
 * o servidor FTP despeja os bytes do arquivo dentro do buffer via
 * {@code retrieveFile}, e ao final {@code toByteArray()} retorna o conteúdo
 * completo. Usa {@code byte[]} ao invés de {@code String} para suportar
 * qualquer tipo de arquivo (CSV, PDF, binário) sem risco de corrupção por encoding.
 */
@RequiredArgsConstructor
public class ClienteFtpImpl implements ClienteFtp {

    private final String host;
    private final int port;
    private final String usuario;
    private final String senha;
    private final FTPClient ftpClient;

    public ClienteFtpImpl(String host, int port, String usuario, String senha) {
        this(host, port, usuario, senha, new FTPClient());
        }

    @Override
    public void conectar() {
        try {
            ftpClient.connect(host, port);

            boolean loginOk = ftpClient.login(usuario, senha);

            if (!loginOk) {
                ftpClient.disconnect();
                throw new RuntimeException("Credenciais inválidas para o servidor FTP: " + host
                );
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao conectar no servidor FTP: " + host, e);
        }
    }

    @Override
    public ArquivoFtp buscarArquivo(String caminhoArquivo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean encontrado;
        try {
            encontrado = ftpClient.retrieveFile(caminhoArquivo, outputStream);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Falha ao buscar arquivo: " + caminhoArquivo, e);
        }
        if (!encontrado) {
            throw new RuntimeException("Arquivo não encontrado no servidor FTP: " + caminhoArquivo);
        }
        return new ArquivoFtp(caminhoArquivo, outputStream.toByteArray());
    }

    @Override
    public void desconectar() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao desconectar do servidor FTP", e);
        }
    }
}
