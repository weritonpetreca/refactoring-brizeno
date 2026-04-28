package dev.weriton.patterns.ch02.extractclass;

import java.util.Arrays;

/**
 * Representa um arquivo recebido via protocolo FTP.
 *
 * <p>Modelado como {@code record} do Java 21 — imutável por natureza,
 * ideal para objetos de transporte de dados (DTO) sem comportamento.
 *
 * <p>{@code conteudo} é {@code byte[]} ao invés de {@code String} para suportar
 * qualquer tipo de arquivo (CSV, PDF, binário) sem risco de corrupção por encoding.
 *
 * <p>{@code equals}, {@code hashCode} e {@code toString} são sobrescritos manualmente
 * pois o comportamento padrão do {@code record} para arrays usa referência ({@code ==})
 * ao invés de conteúdo ({@link java.util.Arrays#equals}).
 */
public record ArquivoFtp(String nome, byte[] conteudo) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArquivoFtp that = (ArquivoFtp) o;
        return nome.equals(that.nome) && Arrays.equals(conteudo, that.conteudo);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + Arrays.hashCode(conteudo);
        return result;
    }

    @Override
    public String toString() {
        return "ArquivoFtp{" +
                "nome='" + nome + '\'' +
                ", conteudo=" + Arrays.toString(conteudo) +
                '}';
    }
}
