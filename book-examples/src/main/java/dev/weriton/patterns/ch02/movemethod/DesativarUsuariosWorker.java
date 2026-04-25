package dev.weriton.patterns.ch02.movemethod;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DesativarUsuariosWorker {

    private final RepositorioUsuarios repositorio;

    public void executar(List<Usuario> usuarios) {
        repositorio.desativar(usuarios);
    }
}
