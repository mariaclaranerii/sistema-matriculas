package br.pucminas.sistemamatriculas.acesso;

/**
 * Usuário humano do Sistema de Matrículas.
 */
public abstract class Usuario {
    private String login;
    private String senha;

    public boolean validarSenha(String senhaInformada) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

