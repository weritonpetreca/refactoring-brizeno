package dev.weriton.patterns.ch02.movefield;

import lombok.AllArgsConstructor;

/**
 * Versão refatorada — Brizeno, Cap. 2, seção 2.4 — Mover Campo
 *
 * <p>Após a refatoração, o {@code Taxi} não guarda mais as constantes das bandeiras.
 * Ele apenas conhece o contexto ({@code ehFinalDeSemana}) e delega o cálculo
 * ao {@link CalculadorDePreco}, que é quem decide qual bandeira aplicar.
 *
 * @see dev.weriton.patterns.ch02.movefield.livro_original.Taxi versão original
 */
@AllArgsConstructor
public class Taxi {

    private final CalculadorDePreco calculador;
    private boolean ehFinalDeSemana;

    public float calcularCorrida(float kmRodados) {
        return calculador.calcularCorrida(kmRodados, ehFinalDeSemana);
    }
}
