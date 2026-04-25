package dev.weriton.patterns.ch03.paradigma;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Estudante {

    private String serie;
    private String nome;

    @Override
    public String toString() {
        return "Nome: " + nome + "\nSerie: " + serie;
    }
}
