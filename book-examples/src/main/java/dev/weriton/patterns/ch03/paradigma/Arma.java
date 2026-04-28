package dev.weriton.patterns.ch03.paradigma;

/**
 * Brizeno, Cap. 3 — Paradigma Orientado a Objetos
 *
 * <p>Demonstra o conceito de <b>classe abstrata</b> como contrato parcial:
 * define o comportamento comum ({@link #danoComArmaQuebrada()}) e delega
 * os detalhes específicos de cada arma ({@link #getDano()}, {@link #getBonusVelocidade()})
 * para as subclasses via métodos abstratos.
 *
 * <p>O método {@link #danoComArmaQuebrada()} é um exemplo de <b>Template Method</b>
 * embutido: usa {@link #getDano()} internamente, que será resolvido em tempo de
 * execução pela implementação concreta — polimorfismo em ação.
 */
public abstract class Arma {
    public abstract int getDano();
    public abstract int getBonusVelocidade();
    public int danoComArmaQuebrada() {
        return getDano() / 2;
    }
}
