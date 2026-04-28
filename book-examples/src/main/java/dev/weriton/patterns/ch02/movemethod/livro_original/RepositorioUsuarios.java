package dev.weriton.patterns.ch02.movemethod.livro_original;

import dev.weriton.patterns.ch02.movemethod.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Brizeno, Cap. 2, seção 2.3 — Mover Método
 *
 * <p>Repositório em memória usado pelo código original. O {@link DesativarUsuariosWorker}
 * o instancia diretamente com {@code new}, tornando impossível substituir
 * ou mockar nos testes — esse é o problema central demonstrado pelo capítulo.
 */
public class RepositorioUsuarios {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void adicionar(Usuario... usuarios) {
        this.usuarios.addAll(List.of(usuarios));
    }

    public List<Usuario> all() {
        return usuarios.stream().toList();
    }

    public boolean semLoginRecente(Usuario usuario) {
        return usuario.semLoginRecente();
    }

    public void desativar(Usuario usuario) {
        usuario.desativar();
    }
}