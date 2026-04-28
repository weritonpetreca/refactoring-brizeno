package dev.weriton.patterns.ch03.paradigma;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Brizeno, Cap. 3 — Paradigma Orientado a Objetos
 *
 * <p>Demonstra a <b>sobrescrita de método</b> ({@code @Override}) com {@code toString()}.
 * O {@code super.toString()} retorna a identidade do objeto no formato
 * {@code NomeCompleto@enderecoMemoria} — útil para depuração e para
 * entender como a JVM identifica instâncias.
 *
 * <p>O id do objeto é dinâmico (endereço de memória), portanto os testes
 * verificam o formato via {@code contains} ao invés de {@code assertEquals}.
 */
@Getter
@AllArgsConstructor
public class Estudante {

    private String serie;
    private String nome;

    @Override
    public String toString() {
        String idDoObjeto = super.toString();
        return "Nome: " + nome + "\nSerie: " + serie + "\nId do Objeto: " + idDoObjeto;
    }
}
