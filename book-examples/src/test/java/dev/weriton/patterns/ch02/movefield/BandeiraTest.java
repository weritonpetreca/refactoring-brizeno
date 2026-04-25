package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
