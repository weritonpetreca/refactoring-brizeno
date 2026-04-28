package dev.weriton.patterns.ch02.movemethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do {@link RepositorioUsuarios} — Brizeno, Cap. 2, seção 2.3
 *
 * <p>Valida as operações do repositório em memória, garantindo que a delegação
 * para a entidade {@link Usuario} funciona corretamente.
 */
@DisplayName("Testes do Repositório de Usuários")
class RepositorioUsuariosTest {

    @Test
    @DisplayName("Deve adicionar e retornar usuários corretamente")
    void deveAdicionarERetornarUsuarios() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        Usuario usuario1 = new Usuario("Geralt");
        Usuario usuario2 = new Usuario("Ciri");

        repositorio.adicionar(usuario1, usuario2);

        assertEquals(2, repositorio.all().size());
    }

    @Test
    @DisplayName("Deve desativar o usuário corretamente")
    void deveDesativarUsuario() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        Usuario usuario = new Usuario("Geralt");
        repositorio.adicionar(usuario);

        repositorio.desativar(usuario);

        assertFalse(usuario.isAtivo());
    }

    @Test
    @DisplayName("Deve delegar semLoginRecente para o usuário")
    void deveDelegarSemLoginRecenteParaOUsuario() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        Usuario semLogin = new Usuario("Geralt");
        Usuario comLogin = new Usuario("Ciri");
        comLogin.login();

        assertTrue(repositorio.semLoginRecente(semLogin));
        assertFalse(repositorio.semLoginRecente(comLogin));
    }
}
