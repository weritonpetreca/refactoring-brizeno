package dev.weriton.patterns.ch02.movemethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Testes da Entidade Usuario")
class UsuarioTest {

    @Test
    @DisplayName("Deve desativar um usuário que estava ativo")
    void deveDesativarUsuarioAtivo() {

        Usuario usuario = new Usuario("Geralt de Rivia", true);

        usuario.desativar();

        assertFalse(usuario.isAtivo());
        assertEquals("Geralt de Rivia", usuario.getNome());
    }
}
