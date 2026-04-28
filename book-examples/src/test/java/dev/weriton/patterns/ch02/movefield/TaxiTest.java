package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Testes do {@link Taxi} refatorado — Brizeno, Cap. 2, seção 2.4
 *
 * <p>Verifica que o {@code Taxi} delega o cálculo ao {@link CalculadorDePreco}
 * sem conhecer os valores das bandeiras — prova que o Mover Campo funcionou.
 * O calculador é mockado para isolar a responsabilidade do Taxi.
 */
@DisplayName("Testes da Entidade Taxi")
class TaxiTest {

    @Test
    @DisplayName("Deve delegar o cálculo final de preço para a classe CalculadorDePreco")
    void deveDelegarCalculoDePrecoParaOCalculador() {

        CalculadorDePreco calculadorMock = mock(CalculadorDePreco.class);
        when(calculadorMock.calcularCorrida(10f, false)).thenReturn(5.76f);

        Taxi taxi = new Taxi(calculadorMock, false);

        float precoFinal = taxi.calcularCorrida(10.0f);

        assertEquals(5.76, precoFinal, 0.001);
        verify(calculadorMock, times(1)).calcularCorrida(10.0f, false);
    }
}
