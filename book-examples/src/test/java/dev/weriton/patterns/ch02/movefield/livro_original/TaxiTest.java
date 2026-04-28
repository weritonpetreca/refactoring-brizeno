package dev.weriton.patterns.ch02.movefield.livro_original;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes do Código Original — Taxi + CalculadorDePreco (Intimidade Inapropriada)
 *
 * <p>⚠️ Estes testes documentam o comportamento funcional antes da refatoração
 * com <b>Mover Campo</b>. O comportamento externo deve ser <b>idêntico</b>
 * na versão refatorada — apenas o design interno muda.
 *
 * <p>Referência: Brizeno, Cap. 2, seção 2.4 — Mover Campo
 * <p>Repositório original: <a href="https://github.com/MarcosX/rppj">github.com/MarcosX/rppj</a>
 */
@DisplayName("[Original] Testes do Taxi + CalculadorDePreco (Mover Campo)")
class TaxiTest {

    @Nested
    @DisplayName("Comportamento funcional — preservado após a refatoração")
    class ComportamentoFuncional {

        @Test
        @DisplayName("Deve calcular o preço correto em dia de semana (Bandeira 1 = 1.2)")
        void deveCalcularCorridaDiaDeSemana() {
            // Arrange
            CalculadorDePreco calculador = new CalculadorDePreco();
            Taxi taxi = new Taxi(calculador, false); // não é final de semana

            // Act
            float preco = taxi.calcularPreco(10.0f);

            // Assert — 1.2 * (10 * 0.48) = 1.2 * 4.8 = 5.76
            assertEquals(5.76, preco, 0.001,
                    "10km na Bandeira 1 (1.2) com R$0,48/km deve custar R$5,76");
        }

        @Test
        @DisplayName("Deve calcular o preço correto no final de semana (Bandeira 2 = 1.8)")
        void deveCalcularCorridaFinalDeSemana() {
            // Arrange
            CalculadorDePreco calculador = new CalculadorDePreco();
            Taxi taxi = new Taxi(calculador, true); // é final de semana

            // Act
            float preco = taxi.calcularPreco(10.0f);

            // Assert — 1.8 * (10 * 0.48) = 1.8 * 4.8 = 8.64
            assertEquals(8.64f, preco, 0.001,
                    "10km na Bandeira 2 (1.8) com R$0,48/km deve custar R$8,64");
        }

        @Test
        @DisplayName("⚠️ Demonstra a Intimidade Inapropriada: Taxi conhece os valores de bandeira do Calculador")
        void demonstraQueOTaxiConheceDetalhesDoCalculador() {
            // Este teste não falha — o código funciona.
            // O problema é de DESIGN: BANDEIRA_UM (1.2f) e BANDEIRA_DOIS (1.8f)
            // estão definidas no Taxi, mas pertencem semanticamente ao CalculadorDePreco.
            //
            // Se o negócio mudar BANDEIRA_UM de 1.2 para 1.3, é preciso alterar o Taxi.
            // Por que o Taxi deveria ser alterado por uma mudança no cálculo de preço?
            // Essa dependência cruzada é o Mau Cheiro "Intimidade Inapropriada".
            //
            // A solução (Mover Campo) move as constantes para o CalculadorDePreco,
            // onde elas realmente pertencem.

            CalculadorDePreco calculador = new CalculadorDePreco();
            Taxi taxiDiaSemana = new Taxi(calculador, false);
            Taxi taxiFimDeSemana = new Taxi(calculador, true);

            // O comportamento externo é correto, mas o design interno está errado.
            assertEquals(5.76f, taxiDiaSemana.calcularPreco(10.0f), 0.001);
            assertEquals(8.64f, taxiFimDeSemana.calcularPreco(10.0f), 0.001);
        }
    }
}
