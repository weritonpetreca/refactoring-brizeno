package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes do enum {@link Bandeira} — Brizeno, Cap. 2, seção 2.4
 *
 * <p>Garante a integridade dos valores do enum para cobertura do JaCoCo.
 * Os métodos {@code values()} e {@code valueOf()} são gerados pelo compilador
 * e precisam ser exercitados para atingir a meta de 80% de cobertura.
 */
@DisplayName("Testes do Enum Bandeira")
class BandeiraTest {

    @Test
    @DisplayName("Deve garantir a integridade dos valores do enum para o JaCoCo")
    void deveCobrirMetodosGeradosDoEnum() {

        Bandeira[] valores = Bandeira.values();
        assertEquals(2, valores.length);
        assertEquals(Bandeira.BANDEIRA_1, Bandeira.valueOf("BANDEIRA_1"));
    }
}
