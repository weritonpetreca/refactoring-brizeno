package dev.weriton.patterns.ch02.extractclass.livro_original;

import dev.weriton.patterns.ch02.extractclass.ArquivoFtp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Testes do Código Original — BaixarRegistrosDeVendaFtpNoBancoWorker
 *
 * <p>⚠️ Estes testes existem para <b>documentar os limites</b> do design original,
 * não para validar seu comportamento completo. Eles provam, na prática, por que
 * a refatoração <b>Extrair Classe</b> é necessária.
 *
 * <p>Referência: Brizeno, Cap. 2, seção 2.5 — Extrair Classe
 * <p>Repositório original: <a href="https://github.com/MarcosX/rppj">github.com/MarcosX/rppj</a>
 */
@DisplayName("[Original] Testes do BaixarRegistrosDeVendaFtpNoBancoWorker")
class BaixarRegistrosDeVendaFtpNoBancoWorkerTest {

    @Nested
    @DisplayName("Demonstração do Mau Cheiro — Acoplamento Rígido")
    class DemonstraMauCheiro {

        @Test
        @DisplayName("⚠️ O Worker pode ser instanciado, mas requisitarFtp() é intestável sem FTP real")
        void demonstraQueRequisitarFtpNaoPodesSerTestadoIsoladamente() {
            // Arrange
            // Podemos mockar o RepositorioDeVendas, pois ele é injetado via construtor.
            RepositorioDeVendas repositorioMock = mock(RepositorioDeVendas.class);

            // Act
            BaixarRegistrosDeVendaFtpNoBancoWorker worker =
                    new BaixarRegistrosDeVendaFtpNoBancoWorker(
                            "ftp.example.com", "21", "user", "senha", repositorioMock);

            // Assert
            // ⚠️ O Worker foi criado, mas NÃO podemos chamar worker.requisitarFtp()
            // porque ele cria `new ClienteFtp(host, porta)` internamente.
            // Sem acesso a um servidor FTP real, o método falharia.
            //
            // Esta é a prova prática do Mau Cheiro:
            // a dependência não é injetável — ela está hardcoded dentro do método.
            //
            // A solução (Extrair Classe) torna ClienteFtp uma interface injetável,
            // visível em dev.weriton.patterns.ch02.extractclass.GerenciadorFtp.
            assertNotNull(worker, "Worker foi criado, mas o comportamento de FTP não pode ser testado isoladamente.");

            // Verificamos que o repositório NÃO foi chamado (pois não invocamos requisitarFtp)
            verifyNoInteractions(repositorioMock);
        }

        @Test
        @DisplayName("⚠️ Demonstra que a responsabilidade de FTP está misturada com a de persistência")
        void demonstraQueUmaClasseTrataDoisDominios() {
            // Arrange
            RepositorioDeVendas repositorioMock = mock(RepositorioDeVendas.class);
            // salvarDeArquivo é void — Mockito não precisa de stubbing (faz nada por padrão)

            // A God Class conhece: host, porta, usuário, senha (domínio FTP)
            //                  E: repositorioDeVendas          (domínio de persistência)
            // Dois mundos completamente distintos em uma única classe.
            BaixarRegistrosDeVendaFtpNoBancoWorker worker =
                    new BaixarRegistrosDeVendaFtpNoBancoWorker(
                            "ftp.loja.com.br", "21", "admin", "s3cr3t", repositorioMock);

            // Este teste documenta o design — a classe existe e compila.
            // O mau cheiro é estrutural, não funcional: está no design, não no resultado.
            assertNotNull(worker);
        }
    }
}