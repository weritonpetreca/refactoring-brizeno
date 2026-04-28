package dev.weriton.patterns.ch03.paradigma;

/**
 * Implementação concreta de {@link Arma} — Brizeno, Cap. 3
 *
 * <p>Demonstra como uma subclasse fornece os detalhes específicos do contrato
 * definido pela classe abstrata. O {@link #danoComArmaQuebrada()} herdado
 * automaticamente retorna {@code 5} (metade de {@code 10}) sem nenhuma
 * implementação adicional — reuso via herança.
 */
public class Adaga extends Arma {

    @Override
    public int getDano() {
        return 10;
    }

    @Override
    public int getBonusVelocidade() {
        return 3;
    }
}
