package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes do Calculador de Preço")
class CalculadorDePrecoTest {

    @Test
    @DisplayName("Deve aplicar a taxa correta para a Bandeira 1")
    void deveCalcularPrecoComBandeira1() {

        CalculadorDePreco calculador = new CalculadorDePreco();
        double preco = calculador.calcular(Bandeira.BANDEIRA_1, 10.0);
        assertEquals(20.0, preco, "10km na Bandeira 1 (R$2) deve custar R$20");
    }

    @Test
    @DisplayName("Deve aplicar a taxa correta para a Bandeira 2")
    void deveCalcularPrecoComBandeira2() {
        CalculadorDePreco calculador = new CalculadorDePreco();
        double preco = calculador.calcular(Bandeira.BANDEIRA_2, 10.0);
        assertEquals(30.0, preco, "10km na Bandeira 2 (R$3) deve custar R$30");
    }
}
