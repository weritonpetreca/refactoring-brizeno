package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Testes da Entidade Taxi")
class TaxiTest {

    @Test
    @DisplayName("Deve delegar o cálculo final de preço para a classe CalculadorDePreco")
    void deveDelegarCalculoDePrecoParaOCalculador() {

        CalculadorDePreco calculadorMock = mock(CalculadorDePreco.class);
        when(calculadorMock.calcular(Bandeira.BANDEIRA_1, 15.0)).thenReturn(30.0);

        Taxi taxi = new Taxi(calculadorMock, Bandeira.BANDEIRA_1);

        double precoFinal = taxi.calcularPreco(15.0);

        assertEquals(30.0, precoFinal);
        verify(calculadorMock, times(1)).calcular(Bandeira.BANDEIRA_1, 15.0);
    }
}
