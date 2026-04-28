package dev.weriton.patterns.ch02.movemethod.livro_original;

import dev.weriton.patterns.ch02.movemethod.Usuario;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Mau Cheiro: <b>Feature Envy (Inveja de Dados)</b>
 * <p>Brizeno, Cap. 2, seção 2.3 — Mover Método
 *
 * <p>Esta classe sofre de <b>Feature Envy</b>: ela está mais interessada nos dados
 * de {@link RepositorioUsuarios} do que nos seus próprios. Instancia o repositório
 * internamente com {@code new}, itera a lista e decide quem desativar —
 * tudo trabalho que pertence ao repositório.
 *
 * <p>O acoplamento estático com {@code NotificadorViaEmail.usuariosDesativados()}
 * impede qualquer substituição do notificador sem alterar esta classe.
 *
 * <p>Como as dependências são criadas internamente, é impossível injetar mocks
 * nos testes — a classe não é testável isoladamente.
 *
 * @see dev.weriton.patterns.ch02.movemethod.DesativarUsuariosWorker versão refatorada
 */
@RequiredArgsConstructor
public class DesativarUsuariosWorker {

    public void desativarUsuarios() {
        RepositorioUsuarios repositorio = new RepositorioUsuarios();
        List<Usuario> usuarios = repositorio.all().stream()
                .filter(usuario ->
                        usuario.semLoginRecente() && usuario.isAtivo())
                .collect(Collectors.toList());

        usuarios.forEach(usuario -> repositorio.desativar(usuario));
        NotificadorViaEmail.usuariosDesativados(usuarios);
    }
}
