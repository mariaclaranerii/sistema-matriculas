package br.pucminas.sistemamatriculas.acesso;

import java.io.Serial;

/**
 * Professor que consulta alunos matriculados por disciplina.
 */
public class Professor extends Usuario {
    @Serial
    private static final long serialVersionUID = 1L;

    public Professor(String login, String senha) {
        super(login, senha);
    }
}
