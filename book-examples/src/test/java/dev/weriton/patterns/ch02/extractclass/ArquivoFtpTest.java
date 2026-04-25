package dev.weriton.patterns.ch02.extractclass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Entidade ArquivoFtp (Cobertura de Overrides)")
class ArquivoFtpTest {

    @Test
    @DisplayName("Deve garantir a igualdade e hashcode baseados no conteúdo do array")
    void deveCompararConteudoCorretamente() {

        ArquivoFtp arquivoOriginal = new ArquivoFtp("relatorio.csv", new byte[]{1, 0, 1});
        ArquivoFtp arquivoClone = new ArquivoFtp("relatorio.csv", new byte[]{1, 0, 1});
        ArquivoFtp arquivoConteudoDiferente = new ArquivoFtp("relatorio.csv", new byte[]{0, 0, 0});
        ArquivoFtp arquivoNomeDiferente = new ArquivoFtp("outro.csv", new byte[]{1, 0, 1});

        assertSame(arquivoOriginal, arquivoOriginal);// Caminho: (this == o) return true
        assertEquals(arquivoOriginal, arquivoClone); // Caminho: igualdade real de conteúdo

        assertNotEquals(null, arquivoOriginal); // Caminho: (o == null) return false
        assertNotEquals(arquivoOriginal, new Object()); // Caminho: getClass() != o.getClass()
        assertNotEquals(arquivoOriginal, arquivoConteudoDiferente); // Caminho: arrays diferentes
        assertNotEquals(arquivoOriginal, arquivoNomeDiferente); // Caminho: nomes diferentes

        assertEquals(arquivoOriginal.hashCode(), arquivoClone.hashCode());
        assertNotEquals(arquivoOriginal.hashCode(), arquivoConteudoDiferente.hashCode());

        String toStringResult = arquivoOriginal.toString();
        assertTrue(toStringResult.contains("relatorio.csv"));
        assertTrue(toStringResult.contains("[1, 0, 1]"));
    }
}