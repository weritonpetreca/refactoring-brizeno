package dev.weriton.patterns.ch03.paradigma;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes da Sobrescrita em Estudante")
public class EstudanteTest {

    @Test
    @DisplayName("Deve retornar a string formatada corretamente ao sobrescrever o toString")
    void deveRetornarStringFormatada() {
        Estudante estudante = new Estudante("3° Ano", "Ciri");
        String resultadoEsperado = "Nome: Ciri\nSerie: 3° Ano";

        assertEquals(resultadoEsperado, estudante.toString());
    }
}
