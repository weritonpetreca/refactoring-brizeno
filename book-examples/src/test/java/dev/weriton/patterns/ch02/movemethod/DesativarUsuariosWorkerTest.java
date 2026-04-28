package dev.weriton.patterns.ch02.movemethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes do {@link DesativarUsuariosWorker} refatorado — Brizeno, Cap. 2, seção 2.3
 *
 * <p>Valida o comportamento do worker após a refatoração com <b>Mover Método</b>.
 * Usa mocks para verificar colaboração (quem chamou quem) e repositório real
 * para verificar estado (o usuário realmente ficou inativo).
 */
@DisplayName("Testes do Worker de Desativação de Usuários")
class DesativarUsuariosWorkerTest {

    @Test
    @DisplayName("Deve delegar a desativação da lista para o repositório")
    void deveDelegarDesativacaoParaORepositorio() {

        RepositorioUsuarios repositorioMock = mock(RepositorioUsuarios.class);
        NotificadorViaEmail notificadorMock = mock(NotificadorViaEmail.class);
        DesativarUsuariosWorker worker = new DesativarUsuariosWorker(repositorioMock, notificadorMock);

        Usuario usuario = new Usuario("Vesemir"); // sem login -> semLoginRecente() = true

        when(repositorioMock.all()).thenReturn(List.of(usuario));

        worker.desativarUsuarios();

        verify(repositorioMock, times(1)).desativar(usuario);
    }

    @Test
    @DisplayName("Deve desativar somente usuários sem login recente")
    void deveDesativarSomenteUsuariosSemLoginRecente() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        Usuario usuario1 = new Usuario("Vesemir");
        Usuario usuario2 = new Usuario("Yennefer");
        usuario2.login();
        repositorio.adicionar(usuario1, usuario2);

        new DesativarUsuariosWorker(repositorio, mock(NotificadorViaEmail.class)).desativarUsuarios();

        assertFalse(usuario1.isAtivo());
        assertTrue(usuario2.isAtivo());
    }

    @Test
    @DisplayName("Não deve chamar desativar nem notificar com repositório vazio")
    void naoDeveInteragirComRepositorioVazio() {
        RepositorioUsuarios repositorioMock = mock(RepositorioUsuarios.class);
        NotificadorViaEmail notificadorMock = mock(NotificadorViaEmail.class);
        when(repositorioMock.all()).thenReturn(List.of());

        new DesativarUsuariosWorker(repositorioMock, notificadorMock).desativarUsuarios();

        verify(repositorioMock, never()).desativar(any());
        verify(notificadorMock, never()).usuariosDesativados(any());
    }

    @Test
    @DisplayName("Não deve desativar usuário sem login recente que já está inativo")
    void naoDeveDesativarUsuarioJaInativo() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        Usuario usuario = new Usuario("Vesemir");
        usuario.desativar();
        repositorio.adicionar(usuario);

        new DesativarUsuariosWorker(repositorio, mock(NotificadorViaEmail.class)).desativarUsuarios();

        assertFalse(usuario.isAtivo());
    }

    @Test
    @DisplayName("Notificador deve ser chamado com a lista de desativados")
    void deveNotificarUsuariosDesativados() {
        RepositorioUsuarios repositorioMock = mock(RepositorioUsuarios.class);
        NotificadorViaEmail notificadorMock = mock(NotificadorViaEmail.class);
        DesativarUsuariosWorker worker = new DesativarUsuariosWorker(repositorioMock, notificadorMock);

        Usuario usuario = new Usuario("Vesemir");
        when(repositorioMock.all()).thenReturn(List.of(usuario));

        worker.desativarUsuarios();

        verify(notificadorMock, times(1)).usuariosDesativados(List.of(usuario));
    }
}
