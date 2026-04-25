package dev.weriton.patterns.ch02.movefield;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Taxi {

    private final CalculadorDePreco calculador;
    private final Bandeira bandeira;

    public double calcularPreco(double quilometragem) {
        return calculador.calcular(bandeira, quilometragem);
    }
}
