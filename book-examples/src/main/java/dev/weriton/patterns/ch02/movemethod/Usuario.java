package dev.weriton.patterns.ch02.movemethod;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entidade central do domínio de usuários.
 *
 * <p>Decisões de design:
 * <ul>
 *   <li>{@code ultimoLogin} não é inicializado no construtor — o login é um
 *       evento explícito via {@link #login()}. Usuário recém criado sem login
 *       é considerado inativo por {@link #semLoginRecente()}.</li>
 *   <li>{@link #semLoginRecente()} trata {@code null} — se nunca houve login,
 *       retorna {@code true} por definição.</li>
 *   <li>{@code toString()} retorna o nome para facilitar a leitura dos logs
 *       sem expor detalhes internos do objeto.</li>
 * </ul>
 */
@Getter
public class Usuario {

    private final String nome;
    private boolean ativo;
    private final LocalDateTime dataCadastro;
    private LocalDateTime ultimoLogin;

    public Usuario(String nome) {
        this.nome = nome;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    public void login() {
        this.ultimoLogin = LocalDateTime.now();
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean semLoginRecente() {
        return ultimoLogin == null || ultimoLogin.isBefore(LocalDateTime.now().minusMonths(1));
    }

    @Override
    public String toString() {
        return nome;
    }
}
