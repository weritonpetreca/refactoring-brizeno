package dev.weriton.patterns.ch02.movefield;

public class CalculadorDePreco {
    private final static double VALOR_BANDEIRA_1 = 2.0;
    private final static double VALOR_BANDEIRA_2 = 3.0;

    public double calcular(Bandeira bandeira, double quilometragem) {
        return (bandeira == Bandeira.BANDEIRA_1 ? VALOR_BANDEIRA_1 : VALOR_BANDEIRA_2) * quilometragem;
    }
}
