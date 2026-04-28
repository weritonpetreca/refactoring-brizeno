package dev.weriton.patterns.ch02.movemethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes da entidade {@link Usuario} — Brizeno, Cap. 2, seção 2.3
 *
 * <p>Valida as decisões de design da entidade: nasce ativo e sem login,
 * {@code semLoginRecente()} trata {@code null} corretamente, e o login
 * é um evento explícito separado da criação.
 */
@DisplayName("Testes da Entidade Usuario")
class UsuarioTest {

    @Test
    @DisplayName("Deve desativar um usuário que estava ativo")
    void deveDesativarUsuarioAtivo() {

        Usuario usuario = new Usuario("Geralt de Rivia");

        usuario.desativar();

        assertFalse(usuario.isAtivo());
        assertEquals("Geralt de Rivia", usuario.getNome());
    }

    @Test
    @DisplayName("Usuário é criado ativo e sem login")
    void usuarioCriadoAtivo() {
        Usuario usuario = new Usuario("Geralt de Rivia");

        assertEquals("Geralt de Rivia", usuario.getNome());
        assertTrue(usuario.isAtivo());
        assertNull(usuario.getUltimoLogin());
        assertTrue(usuario.semLoginRecente());
    }

    @Test
    @DisplayName("Verifica se o usuário possui login recente")
    void deveVerificarSeUsuarioPossuiLoginRecente() {
        Usuario usuario = new Usuario("Geralt de Rivia");
        usuario.login();

        assertFalse(usuario.semLoginRecente());
    }
}
