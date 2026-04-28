package dev.weriton.patterns.ch02.movemethod.livro_original;

import dev.weriton.patterns.ch02.movemethod.Usuario;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Brizeno, Cap. 2, seção 2.3 — Mover Método
 *
 * <p>No original, o método é {@code static} — o {@link DesativarUsuariosWorker}
 * o chama diretamente via {@code NotificadorViaEmail.usuariosDesativados()},
 * criando acoplamento rígido. Não há como substituir o notificador sem
 * alterar o worker.
 *
 * <p>Após a refatoração, {@code NotificadorViaEmail} implementa a interface
 * {@link dev.weriton.patterns.ch02.movemethod.Notificador} e é injetado via construtor.
 *
 * @see dev.weriton.patterns.ch02.movemethod.NotificadorViaEmail versão refatorada
 */
@Slf4j
public class NotificadorViaEmail {

    public static void usuariosDesativados(List<Usuario> usuarios) {
        log.info("Enviando e-mails para usuários desativados: {}", usuarios);
    }
}
