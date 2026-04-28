package dev.weriton.patterns.ch02.movefield.livro_original;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Mau Cheiro: <b>Intimidade Inapropriada (Inappropriate Intimacy)</b>
 * <p>Brizeno, Cap. 2, seção 2.4 — Mover Campo
 *
 * <p>No design original, {@code Taxi} guarda as constantes {@code BANDEIRA_UM}
 * e {@code BANDEIRA_DOIS}, mas quem as <b>usa de verdade</b> é o
 * {@link CalculadorDePreco}. O Taxi conhece detalhes internos do calculador,
 * criando um vínculo íntimo e indevido entre as duas classes.
 *
 * <h2>O Problema</h2>
 * <pre>
 * Taxi                              CalculadorDePreco
 * ┌────────────────────────────┐    ┌───────────────────────────────┐
 * │ BANDEIRA_UM  = 1.2f  ◀─┐  │    │ calcularCorrida(km, bandeira)  │
 * │ BANDEIRA_DOIS = 1.8f ◀─┘  │    │   bandeira * km * 0.48        │
 * │                            │    │                               │
 * │ calcularPreco(km) {        │    │ ⚠️ recebe float sem semântica │
 * │   calculador               │───▶│                               │
 * │     .calcularCorrida(km,   │    └───────────────────────────────┘
 * │      BANDEIRA_UM)          │
 * └────────────────────────────┘
 * Os valores pertencem ao Calculador, mas vivem no Taxi.
 * </pre>
 *
 * <h2>A Cura: Mover Campo</h2>
 * <p>As constantes são <b>movidas para</b> {@code CalculadorDePreco},
 * e o Taxi passa a usar um {@code enum Bandeira} — semântico, seguro e
 * protegido contra valores inválidos.
 *
 * @see dev.weriton.patterns.ch02.movefield.Taxi versão refatorada
 * @see dev.weriton.patterns.ch02.movefield.CalculadorDePreco versão refatorada
 */
public class Taxi {

    // ⚠️ SMELL: estas constantes são usadas pelo CalculadorDePreco, não pelo Taxi
    private static final float BANDEIRA_UM = 1.2f;
    private static final float BANDEIRA_DOIS = 1.8f;

    private final CalculadorDePreco calculador;
    private final boolean ehFinalDeSemana;

    public Taxi(CalculadorDePreco calculador, boolean ehFinalDeSemana) {
        this.calculador = calculador;
        this.ehFinalDeSemana = ehFinalDeSemana;
    }

    /**
     * Calcula o preço da corrida, escolhendo a bandeira conforme o dia da semana.
     *
     * <p>⚠️ O Taxi decide qual bandeira usar passando o {@code float} correspondente.
     * Se o {@code CalculadorDePreco} mudar os valores internos de bandeira,
     * o {@code Taxi} também precisará mudar — acoplamento desnecessário.
     *
     * @param kmRodados quilometragem percorrida
     * @return preço final da corrida
     */
    public float calcularPreco(float kmRodados) {
        // ⚠️ O Taxi "sabe demais" — passa valores mágicos ao calculador
        if (ehFinalDeSemana) {
            return calculador.calcularCorrida(kmRodados, BANDEIRA_DOIS);
        } else {
            return calculador.calcularCorrida(kmRodados, BANDEIRA_UM);
        }
    }
}
