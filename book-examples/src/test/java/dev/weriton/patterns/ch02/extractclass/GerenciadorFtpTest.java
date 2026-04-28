package dev.weriton.patterns.ch02.extractclass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Testes do {@link GerenciadorFtp} — Brizeno, Cap. 2, seção 2.5
 *
 * <p>Verifica que o gerenciador orquestra corretamente o ciclo de vida
 * da conexão FTP: conectar, buscar e desconectar (via {@code try/finally}).
 * O {@link ClienteFtp} é mockado para isolar o teste de infraestrutura real.
 */
@DisplayName("Testes do Gerenciador FTP")
class GerenciadorFtpTest {

    @Test
    @DisplayName("Deve realizar login e buscar o arquivo na rede corretamente")
    void deveRequisitarFtpComSucesso() {

        ClienteFtp clienteMock = mock(ClienteFtp.class);
        GerenciadorFtp gerenciador = new GerenciadorFtp(clienteMock);

        ArquivoFtp arquivoEsperado = new ArquivoFtp("dados.csv", new byte[]{1, 2, 3});
        when(clienteMock.buscarArquivo("/caminho/dados.csv")).thenReturn(arquivoEsperado);

        ArquivoFtp resultado = gerenciador.requisitarFtp("/caminho/dados.csv");

        verify(clienteMock, times(1)).conectar();
        verify(clienteMock, times(1)).buscarArquivo("/caminho/dados.csv");
        assertEquals(arquivoEsperado, resultado);
    }
}