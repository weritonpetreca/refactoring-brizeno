package dev.weriton.patterns.ch02.extractclass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do record {@link ArquivoFtp} — Brizeno, Cap. 2, seção 2.5
 *
 * <p>O {@code record} do Java 21 não gera {@code equals}/{@code hashCode}
 * corretos para {@code byte[]} por padrão (usa referência). Estes testes
 * garantem que os overrides manuais comparam o <b>conteúdo</b> do array
 * corretamente via {@link java.util.Arrays#equals}.
 */
@DisplayName("Testes da Entidade ArquivoFtp (Cobertura de Overrides)")
class ArquivoFtpTest {

    @Test
    @DisplayName("Deve garantir a igualdade e hashcode baseados no conteúdo do array")
    @SuppressWarnings("java:S5785")
    void deveCompararConteudoCorretamente() {

        ArquivoFtp arquivoOriginal = new ArquivoFtp("relatorio.csv", new byte[]{1, 0, 1});
        ArquivoFtp arquivoClone = new ArquivoFtp("relatorio.csv", new byte[]{1, 0, 1});
        ArquivoFtp arquivoConteudoDiferente = new ArquivoFtp("relatorio.csv", new byte[]{0, 0, 0});
        ArquivoFtp arquivoNomeDiferente = new ArquivoFtp("outro.csv", new byte[]{1, 0, 1});

        assertSame(arquivoOriginal, arquivoOriginal);
        assertEquals(arquivoOriginal, arquivoClone);

        assertFalse(arquivoOriginal.equals(null));
        assertFalse(arquivoOriginal.equals(new Object()));
        assertNotEquals(arquivoOriginal, arquivoConteudoDiferente);
        assertNotEquals(arquivoOriginal, arquivoNomeDiferente);

        assertEquals(arquivoOriginal.hashCode(), arquivoClone.hashCode());
        assertNotEquals(arquivoOriginal.hashCode(), arquivoConteudoDiferente.hashCode());

        String toStringResult = arquivoOriginal.toString();
        assertTrue(toStringResult.contains("relatorio.csv"));
        assertTrue(toStringResult.contains("[1, 0, 1]"));
    }
}