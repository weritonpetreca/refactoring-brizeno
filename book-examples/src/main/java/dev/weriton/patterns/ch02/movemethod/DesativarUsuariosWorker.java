package dev.weriton.patterns.ch02.movemethod;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Versão refatorada — Brizeno, Cap. 2, seção 2.3 — Mover Método
 *
 * <p>Após a refatoração, o worker não instancia mais suas dependências internamente.
 * Elas são injetadas via construtor ({@code @RequiredArgsConstructor}), tornando
 * a classe testável e desacoplada de implementações concretas.
 *
 * <p>O {@link Notificador} é uma interface — permite trocar a implementação
 * (email, SMS, push) sem alterar o worker. O {@link RepositorioUsuarios} também
 * é injetado, permitindo mocks nos testes.
 *
 * <p>A guard clause {@code if (usuarios.isEmpty()) return} evita notificações
 * desnecessárias quando não há usuários para desativar.
 *
 * @see dev.weriton.patterns.ch02.movemethod.livro_original.DesativarUsuariosWorker versão original
 */
@RequiredArgsConstructor
public class DesativarUsuariosWorker {

    private final RepositorioUsuarios repositorio;
    private final Notificador notificador;

    public void desativarUsuarios() {
        List<Usuario> usuarios = usuariosParaDesativar();
        if (usuarios.isEmpty()) return;
        usuarios.forEach(repositorio::desativar);
        notificador.usuariosDesativados(usuarios);
    }

    private List<Usuario> usuariosParaDesativar() {
        return repositorio.all().stream()
                .filter(usuario -> usuario.semLoginRecente() && usuario.isAtivo())
                .toList();
    }
}
