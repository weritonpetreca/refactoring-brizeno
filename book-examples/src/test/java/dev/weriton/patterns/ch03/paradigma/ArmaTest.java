package dev.weriton.patterns.ch03.paradigma;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes de Contrato de Armas")
class ArmaTest {

    @Test
    @DisplayName("Deve garantir que a Adaga respeita o contrato de Arma e retorna valores corretos")
    void adagaDeveRespeitarContrato() {

        Arma arma = new Adaga();

        assertEquals(10, arma.getDano());
        assertEquals(3, arma.getBonusVelocidade());
    }
}
