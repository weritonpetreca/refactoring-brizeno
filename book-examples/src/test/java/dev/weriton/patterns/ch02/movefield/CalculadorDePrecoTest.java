package dev.weriton.patterns.ch02.movefield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes do {@link CalculadorDePreco} refatorado — Brizeno, Cap. 2, seção 2.4
 *
 * <p>Valida os valores calculados para cada bandeira após o Mover Campo.
 * Fórmula: {@code bandeira.getValor() * kmRodados * VALOR_POR_KM}
 * (ex: {@code 1.2 * 10 * 0.48 = 5.76} para Bandeira 1).
 */
@DisplayName("Testes do Calculador de Preço")
class CalculadorDePrecoTest {

    @Test
    @DisplayName("Deve aplicar a taxa correta para a Bandeira 1")
    void deveCalcularPrecoComBandeira1() {

        CalculadorDePreco calculador = new CalculadorDePreco();
        float preco = calculador.calcularCorrida(10.0f, false);
        assertEquals(5.76, preco, 0.001, "10km na Bandeira 1 (R$2) deve custar R$20");
    }

    @Test
    @DisplayName("Deve aplicar a taxa correta para a Bandeira 2")
    void deveCalcularPrecoComBandeira2() {
        CalculadorDePreco calculador = new CalculadorDePreco();
        float preco = calculador.calcularCorrida(10.0f, true);
        assertEquals(8.64, preco, 0.001, "10km na Bandeira 2 (R$3) deve custar R$30");
    }
}
