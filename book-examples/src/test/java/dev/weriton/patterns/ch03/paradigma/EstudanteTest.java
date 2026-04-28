package dev.weriton.patterns.ch03.paradigma;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes da sobrescrita de {@code toString()} em {@link Estudante} — Brizeno, Cap. 3
 *
 * <p>O id do objeto ({@code super.toString()}) é dinâmico (endereço de memória),
 * portanto a verificação usa {@code contains} ao invés de {@code assertEquals},
 * garantindo o formato sem depender do valor exato.
 */
@DisplayName("Testes da Sobrescrita em Estudante")
class EstudanteTest {

    @Test
    @DisplayName("Deve incluir o id do objeto no toString")
    void deveIncluirIdDoObjeto() {
        Estudante estudante = new Estudante("3° Ano", "Ciri");
        String resultado = estudante.toString();

        assertTrue(resultado.contains("Nome: Ciri"));
        assertTrue(resultado.contains("Serie: 3° Ano"));
        assertTrue(resultado.contains("Id do Objeto: " + estudante.getClass().getName() + "@"));
    }
}
