package dev.weriton.patterns.ch02.movemethod;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
 * Implementação concreta de {@link Notificador} via e-mail.
 *
 * <p>No livro original, esta classe tinha o método {@code usuariosDesativados}
 * como {@code static}, criando acoplamento direto no worker. Após a refatoração,
 * implementa a interface {@link Notificador} e é injetada via construtor.
 *
 * <p>A guard clause {@code if (usuarios.isEmpty()) return} evita logar
 * mensagens vazias desnecessárias.
 */
@Slf4j
public class NotificadorViaEmail implements Notificador {

    public void usuariosDesativados(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) return;
        log.info("Enviando e-mails para usuários desativados: {}", usuarios.toString());
    }
}
