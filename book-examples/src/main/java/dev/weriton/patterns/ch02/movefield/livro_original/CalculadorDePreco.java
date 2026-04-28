package dev.weriton.patterns.ch02.movefield.livro_original;

/**
 * ⚠️ CÓDIGO ORIGINAL DO LIVRO — Brizeno, Cap. 2, seção 2.4 — Mover Campo
 *
 * <p>No design original, {@code CalculadorDePreco} recebe a {@code bandeira}
 * como um {@code float} simples. Isso introduz dois problemas:
 *
 * <ol>
 *   <li><b>Número mágico:</b> o chamador precisa saber que {@code 1.2f} significa
 *       "Bandeira 1" e {@code 1.8f} significa "Bandeira 2" — sem nenhuma semântica.</li>
 *   <li><b>Acoplamento invertido:</b> os valores das bandeiras estão definidos no
 *       {@link Taxi} (que as usa para calcular) em vez de estar aqui, onde elas
 *       são realmente consumidas.</li>
 * </ol>
 *
 * <p>Após a refatoração com <b>Mover Campo</b>, as constantes migram para cá,
 * e o método passa a receber um {@code enum Bandeira} com semântica clara.
 *
 * @see dev.weriton.patterns.ch02.movefield.CalculadorDePreco versão refatorada
 */
public class CalculadorDePreco {

    private static final float VALOR_POR_KM = 0.48f;

    /**
     * Calcula o preço da corrida com base na quilometragem e no multiplicador da bandeira.
     *
     * <p>⚠️ Mau Cheiro: o parâmetro {@code bandeira} é um {@code float} sem semântica.
     * O chamador (Taxi) precisa conhecer os valores mágicos {@code 1.2f} e {@code 1.8f},
     * criando acoplamento com detalhes de implementação deste calculador.
     *
     * @param kmRodados  quilometragem percorrida na corrida
     * @param bandeira   multiplicador da bandeira (ex: 1.2 para Bandeira 1, 1.8 para Bandeira 2)
     * @return           preço total da corrida
     */
    public float calcularCorrida(float kmRodados, float bandeira) {
        // ⚠️ bandeira é um número mágico vindo de fora — frágil e sem proteção
        return bandeira * (kmRodados * VALOR_POR_KM);
    }
}
