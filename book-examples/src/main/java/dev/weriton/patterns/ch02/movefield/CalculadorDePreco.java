package dev.weriton.patterns.ch02.movefield;

/**
 * Versão refatorada — Brizeno, Cap. 2, seção 2.4 — Mover Campo
 *
 * <p>As constantes das bandeiras foram movidas para cá (vindas do {@code Taxi})
 * e substituídas pelo {@link Bandeira} enum — os valores agora têm nome e
 * significado, eliminando os números mágicos do código original.
 *
 * <p>A lógica de decidir qual bandeira usar permanece aqui, fiel ao livro:
 * o {@code Taxi} apenas informa o contexto ({@code ehFinalDeSemana}).
 *
 * @see dev.weriton.patterns.ch02.movefield.livro_original.CalculadorDePreco versão original
 */
public class CalculadorDePreco {
    private static final float VALOR_POR_KM = 0.48f;

    public float calcularCorrida(float kmRodados, boolean bandeiraDois) {
        return (!bandeiraDois ? Bandeira.BANDEIRA_1.getValor() : Bandeira.BANDEIRA_2.getValor()) * kmRodados * VALOR_POR_KM;
    }
}
