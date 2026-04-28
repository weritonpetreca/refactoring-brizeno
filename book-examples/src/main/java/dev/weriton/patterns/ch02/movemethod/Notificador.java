package dev.weriton.patterns.ch02.movemethod;

import java.util.List;

/**
 * Contrato de notificação de usuários desativados.
 *
 * <p>Decisão além do livro: o livro usa {@code NotificadorViaEmail} diretamente
 * como classe concreta estática. Aqui extraimos uma interface para desacoplar
 * o {@link DesativarUsuariosWorker} da implementação concreta, permitindo
 * trocar o canal de notificação (email, SMS, push) sem alterar o worker.
 */
public interface Notificador {
    void usuariosDesativados(List<Usuario> usuarios);
}
