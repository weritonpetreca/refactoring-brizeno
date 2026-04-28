package dev.weriton.patterns.ch02.extractclass;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Testes de integração do {@link ClienteFtpImpl} — Brizeno, Cap. 2, seção 2.5
 *
 * <p>Usa Testcontainers para subir um servidor FTP real em Docker,
 * garantindo que a implementação concreta funciona com infraestrutura real
 * sem depender de ambiente externo fixo.
 *
 * <p><b>Imagem:</b> {@code delfer/alpine-ftp-server} — servidor FTP leve baseado em vsftpd/Alpine.
 * O servidor faz {@code chroot} do usuário para {@code /ftp/<usuario>/},
 * portanto caminhos de arquivo devem ser relativos (ex: {@code "test.txt"}, não {@code "/test.txt"}).
 *
 * <p><b>Networking — por que conectamos ao IP do bridge e não a {@code localhost}:</b><br>
 * O protocolo FTP usa dois canais TCP separados: controle (porta 21) e dados (porta PASV).
 * No modo passivo, o servidor anuncia a porta de dados e o cliente abre uma segunda conexão.
 * As regras DNAT do Docker não interceptam tráfego originado em {@code 127.0.0.1} (loopback),
 * fazendo a conexão de dados falhar com {@code Connection refused}.
 * A solução é conectar diretamente ao IP do container na rede bridge ({@code 172.17.x.x}),
 * obtido via {@link #getContainerBridgeIp()}, onde o tráfego flui sem passar pelo NAT.
 */
@Testcontainers
@DisplayName("Testes de Integração do ClienteFtpImpl")
class ClienteFtpImplTest {

    private static final String USUARIO = "user";
    private static final String SENHA = "password";
    private static final int FTP_PORT = 21;

    @Container
    static GenericContainer<?> ftpServer = new GenericContainer<>(DockerImageName.parse("delfer/alpine-ftp-server"))
            .withEnv("USERS", USUARIO + "|" + SENHA)
            .withEnv("PASV_MIN_PORT", "21000")
            .withEnv("PASV_MAX_PORT", "21005")
            .withExposedPorts(FTP_PORT)
            .waitingFor(Wait.forListeningPort());

    @Test
    @DisplayName("Deve conectar e desconectar do servidor FTP com sucesso")
    void deveConectarEDesconectar() {
        ClienteFtpImpl cliente = new ClienteFtpImpl(
                getContainerBridgeIp(),
                FTP_PORT,
                USUARIO,
                SENHA
        );

        assertDoesNotThrow(() -> {
            cliente.conectar();
            cliente.desconectar();
        });
    }

    @Test
    @DisplayName("Deve buscar um arquivo existente no servidor FTP")
    void deveBuscarArquivo() throws IOException, InterruptedException {
        ftpServer.execInContainer("sh", "-c", "echo 'conteudo de teste' > /ftp/user/test.txt");

        ClienteFtpImpl cliente = new ClienteFtpImpl(
                getContainerBridgeIp(),
                FTP_PORT,
                USUARIO,
                SENHA
        );

        cliente.conectar();
        ArquivoFtp arquivo = cliente.buscarArquivo("test.txt");
        cliente.desconectar();

        assertNotNull(arquivo);
        assertEquals("test.txt", arquivo.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao conectar com credenciais inválidas")
    void deveLancarExcecaoComCredenciaisInvalidas() {
        ClienteFtpImpl cliente = new ClienteFtpImpl(
                getContainerBridgeIp(),
                FTP_PORT,
                "usuario_invalido",
                "senha_invalida"
        );

        RuntimeException ex = assertThrows(RuntimeException.class, cliente::conectar);
        assertThat(ex.getMessage()).contains("Credenciais inválidas");
    }

    @Test
    @DisplayName("Deve lançar exceção ao conectar em host inalcançável")
    void deveLancarExcecaoAoConectarEmHostInalcancavel() {
        ClienteFtpImpl cliente = new ClienteFtpImpl(
                "host-que-nao-existe.local",
                FTP_PORT,
                USUARIO,
                SENHA
        );

        RuntimeException ex = assertThrows(RuntimeException.class, cliente::conectar);
        assertThat(ex.getMessage()).contains("Falha ao conectar no servidor FTP");
        assertThat(ex.getCause()).isInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar arquivo inexistente")
    void deveLancarExcecaoAoBuscarArquivoInexistente() {
        ClienteFtpImpl cliente = new ClienteFtpImpl(
                getContainerBridgeIp(),
                FTP_PORT,
                USUARIO,
                SENHA
        );

        cliente.conectar();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cliente.buscarArquivo("arquivo-que-nao-existe.txt"));
        cliente.desconectar();

        assertThat(ex.getMessage()).contains("Arquivo não encontrado no servidor FTP");
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar arquivo sem estar conectado")
    void deveLancarExcecaoAoBuscarSemConectar() {
        ClienteFtpImpl cliente = new ClienteFtpImpl(
                getContainerBridgeIp(),
                FTP_PORT,
                USUARIO,
                SENHA
        );

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cliente.buscarArquivo("test.txt"));

        assertThat(ex.getMessage()).contains("Falha ao buscar arquivo");
        assertThat(ex.getCause()).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção ao falhar ao desconectar")
    void deveLancarExcecaoAoFalharAoDesconectar() throws IOException {
        FTPClient ftpClientMock = mock(FTPClient.class);
        doThrow(new IOException("socket fechado inesperadamente"))
                .when(ftpClientMock).logout();

        ClienteFtpImpl cliente = new ClienteFtpImpl(
                "localhost", FTP_PORT, USUARIO, SENHA, ftpClientMock
        );

        RuntimeException ex = assertThrows(RuntimeException.class, cliente::desconectar);
        assertThat(ex.getMessage()).contains("Falha ao desconectar do servidor FTP");
        assertThat(ex.getCause()).isInstanceOf(IOException.class);
    }

    private static String getContainerBridgeIp() {
        return ftpServer.getContainerInfo()
                .getNetworkSettings()
                .getNetworks()
                .get("bridge")
                .getIpAddress(); // ex: "172.17.0.2"
    }
}
