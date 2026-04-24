package dev.weriton.patterns.ch02.extractclass;

import java.util.Arrays;

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
