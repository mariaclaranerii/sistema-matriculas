package br.pucminas.sistemamatriculas.acesso;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Usuário humano do Sistema de Matrículas.
 */
public abstract class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String login;
    private final String senha;

    protected Usuario(String login, String senha) {
        this.login = normalizarLogin(login);
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        this.senha = senha;
    }

    public boolean validarSenha(String senhaInformada) {
        return Objects.equals(senha, senhaInformada);
    }

    public String getLogin() {
        return login;
    }

    public String getPapel() {
        return getClass().getSimpleName();
    }

    public static String normalizarLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("O login é obrigatório.");
        }
        return login.trim().toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Usuario usuario)) {
            return false;
        }
        return login.equals(usuario.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return login + " (" + getPapel() + ")";
    }
}
