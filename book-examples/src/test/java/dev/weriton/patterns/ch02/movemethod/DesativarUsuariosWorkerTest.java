package dev.weriton.patterns.ch02.movemethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Testes do Worker de Desativação de Usuários")
class DesativarUsuariosWorkerTest {

    @Test
    @DisplayName("Deve delegar a desativação da lista para o repositório")
    void deveDelegarDesativacaoParaORepositorio() {

        RepositorioUsuarios repositorioMock = mock(RepositorioUsuarios.class);
        DesativarUsuariosWorker worker = new DesativarUsuariosWorker(repositorioMock);

        List<Usuario> usuarios = List.of(new Usuario("Vesemir", true));

        worker.executar(usuarios);

        verify(repositorioMock, times(1)).desativar(usuarios);
    }
}
