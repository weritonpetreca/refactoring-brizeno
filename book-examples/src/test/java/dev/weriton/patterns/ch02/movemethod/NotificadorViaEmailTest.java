package dev.weriton.patterns.ch02.movemethod;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes do {@link NotificadorViaEmail} — Brizeno, Cap. 2, seção 2.3
 *
 * <p>Usa {@code slf4j-test} para capturar os eventos de log em memória
 * e verificar que a mensagem contém os nomes dos usuários desativados,
 * sem depender de saída real de e-mail ou console.
 */
@DisplayName("Testes do Notificador via E-mail")
class NotificadorViaEmailTest {

    private final TestLogger logger = TestLoggerFactory.getTestLogger(NotificadorViaEmail.class);

    @Test
    @DisplayName("Deve notificar os nomes dos usuários desativados via e-mail")
    void deveNotificarNomesUsuariosDesativados() {
        List<Usuario> usuarios = List.of(new Usuario("Geralt"), new Usuario("Yennefer"));

        new NotificadorViaEmail().usuariosDesativados(usuarios);

        assertTrue(logger.getLoggingEvents().stream()
                .anyMatch(e -> e.getFormattedMessage().contains("Geralt")));
    }

    @Test
    @DisplayName("Não deve notificar se a lista de usuários estiver vazia")
    void naoDeveNotificarSeListaUsuariosVazia() {
        List<Usuario> usuarios = List.of();

        new NotificadorViaEmail().usuariosDesativados(usuarios);

        assertTrue(logger.getLoggingEvents().isEmpty());
    }
}
