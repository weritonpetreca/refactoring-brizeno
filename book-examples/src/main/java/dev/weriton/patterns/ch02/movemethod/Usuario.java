package dev.weriton.patterns.ch02.movemethod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Usuario {

    private final String nome;
    private boolean ativo;

    public void desativar() {
        this.ativo = false;
    }
}
