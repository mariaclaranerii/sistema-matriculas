package br.pucminas.sistemamatriculas.acesso;

import java.io.Serial;

/**
 * Papel responsável pelas operações acadêmicas previstas no enunciado.
 */
public class Secretaria extends Usuario {
    @Serial
    private static final long serialVersionUID = 1L;

    public Secretaria(String login, String senha) {
        super(login, senha);
    }
}
