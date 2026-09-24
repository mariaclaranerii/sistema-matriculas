package br.pucminas.sistemamatriculas.acesso;

import java.io.Serial;

/**
 * Aluno que realiza e cancela matrículas.
 */
public class Aluno extends Usuario {
    @Serial
    private static final long serialVersionUID = 1L;

    public Aluno(String login, String senha) {
        super(login, senha);
    }
}
