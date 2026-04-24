package dev.weriton.patterns.ch02.extractclass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Testes do Worker de Salvamento de Registros de Venda")
class SalvarRegistroDeVendasWorkerTest {

    @Nested
    @DisplayName("Dado que o serviço de rede (FTP/SFTP) está operante")
    class ServicoOperante {

        @Test
        @DisplayName("Deve requisitar o arquivo remoto e enviá-lo para persistência")
        void deveOrquestrarBaixaESalvamentoComSucesso() {

            String caminhoArquivo = "/vendas/2026-04-24.csv";
            ArquivoFtp arquivoSimulado = new ArquivoFtp("vendas.csv", new byte[]{});

            GerenciadorFtp gerenciadorMock = mock(GerenciadorFtp.class);
            RepositorioDeVendas repositorioMock = mock(RepositorioDeVendas.class);

            when(gerenciadorMock.requisitarFtp(caminhoArquivo)).thenReturn(arquivoSimulado);

            SalvarRegistroDeVendasWorker worker = new SalvarRegistroDeVendasWorker(gerenciadorMock, repositorioMock);

            worker.executar(caminhoArquivo);

            verify(gerenciadorMock, times(1)).requisitarFtp(caminhoArquivo);
            verify(repositorioMock, times(1)).salvar(arquivoSimulado);
        }
    }

    @Nested
    @DisplayName("Dado que parâmetros de entrada são maliciosos ou inválidos (Shift-Left)")
    class ParametrosInvalidos {

        @Test
        @DisplayName("Deve rejeitar a execução se o caminho do arquivo for nulo")
        void deveRejeitarCaminhoNulo() {

            GerenciadorFtp gerenciadorMock = mock(GerenciadorFtp.class);
            RepositorioDeVendas repositorioMock = mock(RepositorioDeVendas.class);

            SalvarRegistroDeVendasWorker worker = new SalvarRegistroDeVendasWorker(gerenciadorMock, repositorioMock);

            IllegalArgumentException excecao = assertThrows(
                    IllegalArgumentException.class,
                    () -> worker.executar(null)
            );

            assertEquals("O caminho do arquivo não pode ser nulo ou vazio (Possível falha de segurança)", excecao.getMessage());

            verify(gerenciadorMock, never()).requisitarFtp(any());
        }
    }
}
